package ecom.mescourses.web.rest;

import ecom.mescourses.MesCoursesApp;

import ecom.mescourses.domain.UserDocuments;
import ecom.mescourses.repository.UserDocumentsRepository;
import ecom.mescourses.repository.search.UserDocumentsSearchRepository;
import ecom.mescourses.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static ecom.mescourses.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ecom.mescourses.domain.enumeration.Sexe;
/**
 * Test class for the UserDocumentsResource REST controller.
 *
 * @see UserDocumentsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesCoursesApp.class)
public class UserDocumentsResourceIntTest {

    private static final LocalDate DEFAULT_DATE_DE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Sexe DEFAULT_SEXE = Sexe.HOMME;
    private static final Sexe UPDATED_SEXE = Sexe.FEMME;

    private static final byte[] DEFAULT_CERTIFICAT_MEDICAL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICAT_MEDICAL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICAT_MEDICAL_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LICENSE_FEDERALE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LICENSE_FEDERALE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LICENSE_FEDERALE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LICENSE_FEDERALE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOCUMENT_COMPLEMENTAIRE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_COMPLEMENTAIRE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE = "image/png";

    @Autowired
    private UserDocumentsRepository userDocumentsRepository;

    /**
     * This repository is mocked in the ecom.mescourses.repository.search test package.
     *
     * @see ecom.mescourses.repository.search.UserDocumentsSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserDocumentsSearchRepository mockUserDocumentsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserDocumentsMockMvc;

    private UserDocuments userDocuments;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserDocumentsResource userDocumentsResource = new UserDocumentsResource(userDocumentsRepository, mockUserDocumentsSearchRepository);
        this.restUserDocumentsMockMvc = MockMvcBuilders.standaloneSetup(userDocumentsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDocuments createEntity(EntityManager em) {
        UserDocuments userDocuments = new UserDocuments()
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .sexe(DEFAULT_SEXE)
            .certificatMedical(DEFAULT_CERTIFICAT_MEDICAL)
            .certificatMedicalContentType(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE)
            .licenseFederale(DEFAULT_LICENSE_FEDERALE)
            .licenseFederaleContentType(DEFAULT_LICENSE_FEDERALE_CONTENT_TYPE)
            .documentComplementaire(DEFAULT_DOCUMENT_COMPLEMENTAIRE)
            .documentComplementaireContentType(DEFAULT_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE);
        return userDocuments;
    }

    @Before
    public void initTest() {
        userDocuments = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDocuments() throws Exception {
        int databaseSizeBeforeCreate = userDocumentsRepository.findAll().size();

        // Create the UserDocuments
        restUserDocumentsMockMvc.perform(post("/api/user-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDocuments)))
            .andExpect(status().isCreated());

        // Validate the UserDocuments in the database
        List<UserDocuments> userDocumentsList = userDocumentsRepository.findAll();
        assertThat(userDocumentsList).hasSize(databaseSizeBeforeCreate + 1);
        UserDocuments testUserDocuments = userDocumentsList.get(userDocumentsList.size() - 1);
        assertThat(testUserDocuments.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testUserDocuments.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testUserDocuments.getCertificatMedical()).isEqualTo(DEFAULT_CERTIFICAT_MEDICAL);
        assertThat(testUserDocuments.getCertificatMedicalContentType()).isEqualTo(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE);
        assertThat(testUserDocuments.getLicenseFederale()).isEqualTo(DEFAULT_LICENSE_FEDERALE);
        assertThat(testUserDocuments.getLicenseFederaleContentType()).isEqualTo(DEFAULT_LICENSE_FEDERALE_CONTENT_TYPE);
        assertThat(testUserDocuments.getDocumentComplementaire()).isEqualTo(DEFAULT_DOCUMENT_COMPLEMENTAIRE);
        assertThat(testUserDocuments.getDocumentComplementaireContentType()).isEqualTo(DEFAULT_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE);

        // Validate the UserDocuments in Elasticsearch
        verify(mockUserDocumentsSearchRepository, times(1)).save(testUserDocuments);
    }

    @Test
    @Transactional
    public void createUserDocumentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDocumentsRepository.findAll().size();

        // Create the UserDocuments with an existing ID
        userDocuments.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDocumentsMockMvc.perform(post("/api/user-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the UserDocuments in the database
        List<UserDocuments> userDocumentsList = userDocumentsRepository.findAll();
        assertThat(userDocumentsList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserDocuments in Elasticsearch
        verify(mockUserDocumentsSearchRepository, times(0)).save(userDocuments);
    }

    @Test
    @Transactional
    public void getAllUserDocuments() throws Exception {
        // Initialize the database
        userDocumentsRepository.saveAndFlush(userDocuments);

        // Get all the userDocumentsList
        restUserDocumentsMockMvc.perform(get("/api/user-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].certificatMedicalContentType").value(hasItem(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].certificatMedical").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICAT_MEDICAL))))
            .andExpect(jsonPath("$.[*].licenseFederaleContentType").value(hasItem(DEFAULT_LICENSE_FEDERALE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].licenseFederale").value(hasItem(Base64Utils.encodeToString(DEFAULT_LICENSE_FEDERALE))))
            .andExpect(jsonPath("$.[*].documentComplementaireContentType").value(hasItem(DEFAULT_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentComplementaire").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_COMPLEMENTAIRE))));
    }
    
    @Test
    @Transactional
    public void getUserDocuments() throws Exception {
        // Initialize the database
        userDocumentsRepository.saveAndFlush(userDocuments);

        // Get the userDocuments
        restUserDocumentsMockMvc.perform(get("/api/user-documents/{id}", userDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDocuments.getId().intValue()))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.certificatMedicalContentType").value(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE))
            .andExpect(jsonPath("$.certificatMedical").value(Base64Utils.encodeToString(DEFAULT_CERTIFICAT_MEDICAL)))
            .andExpect(jsonPath("$.licenseFederaleContentType").value(DEFAULT_LICENSE_FEDERALE_CONTENT_TYPE))
            .andExpect(jsonPath("$.licenseFederale").value(Base64Utils.encodeToString(DEFAULT_LICENSE_FEDERALE)))
            .andExpect(jsonPath("$.documentComplementaireContentType").value(DEFAULT_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentComplementaire").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_COMPLEMENTAIRE)));
    }

    @Test
    @Transactional
    public void getNonExistingUserDocuments() throws Exception {
        // Get the userDocuments
        restUserDocumentsMockMvc.perform(get("/api/user-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDocuments() throws Exception {
        // Initialize the database
        userDocumentsRepository.saveAndFlush(userDocuments);

        int databaseSizeBeforeUpdate = userDocumentsRepository.findAll().size();

        // Update the userDocuments
        UserDocuments updatedUserDocuments = userDocumentsRepository.findById(userDocuments.getId()).get();
        // Disconnect from session so that the updates on updatedUserDocuments are not directly saved in db
        em.detach(updatedUserDocuments);
        updatedUserDocuments
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .sexe(UPDATED_SEXE)
            .certificatMedical(UPDATED_CERTIFICAT_MEDICAL)
            .certificatMedicalContentType(UPDATED_CERTIFICAT_MEDICAL_CONTENT_TYPE)
            .licenseFederale(UPDATED_LICENSE_FEDERALE)
            .licenseFederaleContentType(UPDATED_LICENSE_FEDERALE_CONTENT_TYPE)
            .documentComplementaire(UPDATED_DOCUMENT_COMPLEMENTAIRE)
            .documentComplementaireContentType(UPDATED_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE);

        restUserDocumentsMockMvc.perform(put("/api/user-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserDocuments)))
            .andExpect(status().isOk());

        // Validate the UserDocuments in the database
        List<UserDocuments> userDocumentsList = userDocumentsRepository.findAll();
        assertThat(userDocumentsList).hasSize(databaseSizeBeforeUpdate);
        UserDocuments testUserDocuments = userDocumentsList.get(userDocumentsList.size() - 1);
        assertThat(testUserDocuments.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testUserDocuments.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testUserDocuments.getCertificatMedical()).isEqualTo(UPDATED_CERTIFICAT_MEDICAL);
        assertThat(testUserDocuments.getCertificatMedicalContentType()).isEqualTo(UPDATED_CERTIFICAT_MEDICAL_CONTENT_TYPE);
        assertThat(testUserDocuments.getLicenseFederale()).isEqualTo(UPDATED_LICENSE_FEDERALE);
        assertThat(testUserDocuments.getLicenseFederaleContentType()).isEqualTo(UPDATED_LICENSE_FEDERALE_CONTENT_TYPE);
        assertThat(testUserDocuments.getDocumentComplementaire()).isEqualTo(UPDATED_DOCUMENT_COMPLEMENTAIRE);
        assertThat(testUserDocuments.getDocumentComplementaireContentType()).isEqualTo(UPDATED_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE);

        // Validate the UserDocuments in Elasticsearch
        verify(mockUserDocumentsSearchRepository, times(1)).save(testUserDocuments);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDocuments() throws Exception {
        int databaseSizeBeforeUpdate = userDocumentsRepository.findAll().size();

        // Create the UserDocuments

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDocumentsMockMvc.perform(put("/api/user-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDocuments)))
            .andExpect(status().isBadRequest());

        // Validate the UserDocuments in the database
        List<UserDocuments> userDocumentsList = userDocumentsRepository.findAll();
        assertThat(userDocumentsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserDocuments in Elasticsearch
        verify(mockUserDocumentsSearchRepository, times(0)).save(userDocuments);
    }

    @Test
    @Transactional
    public void deleteUserDocuments() throws Exception {
        // Initialize the database
        userDocumentsRepository.saveAndFlush(userDocuments);

        int databaseSizeBeforeDelete = userDocumentsRepository.findAll().size();

        // Get the userDocuments
        restUserDocumentsMockMvc.perform(delete("/api/user-documents/{id}", userDocuments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserDocuments> userDocumentsList = userDocumentsRepository.findAll();
        assertThat(userDocumentsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserDocuments in Elasticsearch
        verify(mockUserDocumentsSearchRepository, times(1)).deleteById(userDocuments.getId());
    }

    @Test
    @Transactional
    public void searchUserDocuments() throws Exception {
        // Initialize the database
        userDocumentsRepository.saveAndFlush(userDocuments);
        when(mockUserDocumentsSearchRepository.search(queryStringQuery("id:" + userDocuments.getId())))
            .thenReturn(Collections.singletonList(userDocuments));
        // Search the userDocuments
        restUserDocumentsMockMvc.perform(get("/api/_search/user-documents?query=id:" + userDocuments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDocuments.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].certificatMedicalContentType").value(hasItem(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].certificatMedical").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICAT_MEDICAL))))
            .andExpect(jsonPath("$.[*].licenseFederaleContentType").value(hasItem(DEFAULT_LICENSE_FEDERALE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].licenseFederale").value(hasItem(Base64Utils.encodeToString(DEFAULT_LICENSE_FEDERALE))))
            .andExpect(jsonPath("$.[*].documentComplementaireContentType").value(hasItem(DEFAULT_DOCUMENT_COMPLEMENTAIRE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentComplementaire").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_COMPLEMENTAIRE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDocuments.class);
        UserDocuments userDocuments1 = new UserDocuments();
        userDocuments1.setId(1L);
        UserDocuments userDocuments2 = new UserDocuments();
        userDocuments2.setId(userDocuments1.getId());
        assertThat(userDocuments1).isEqualTo(userDocuments2);
        userDocuments2.setId(2L);
        assertThat(userDocuments1).isNotEqualTo(userDocuments2);
        userDocuments1.setId(null);
        assertThat(userDocuments1).isNotEqualTo(userDocuments2);
    }
}
