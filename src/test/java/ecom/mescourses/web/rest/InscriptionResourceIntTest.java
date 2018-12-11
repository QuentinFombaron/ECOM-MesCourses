package ecom.mescourses.web.rest;

import ecom.mescourses.MesCoursesApp;

import ecom.mescourses.domain.Inscription;
import ecom.mescourses.repository.InscriptionRepository;
import ecom.mescourses.repository.search.InscriptionSearchRepository;
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

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static ecom.mescourses.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ecom.mescourses.domain.enumeration.Metier;
/**
 * Test class for the InscriptionResource REST controller.
 *
 * @see InscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesCoursesApp.class)
public class InscriptionResourceIntTest {

    private static final Metier DEFAULT_ROLE = Metier.MEDECIN;
    private static final Metier UPDATED_ROLE = Metier.KINESITHERAPEUTE;

    private static final Integer DEFAULT_USER = 1;
    private static final Integer UPDATED_USER = 2;

    private static final Integer DEFAULT_COURSE = 1;
    private static final Integer UPDATED_COURSE = 2;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    /**
     * This repository is mocked in the ecom.mescourses.repository.search test package.
     *
     * @see ecom.mescourses.repository.search.InscriptionSearchRepositoryMockConfiguration
     */
    @Autowired
    private InscriptionSearchRepository mockInscriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInscriptionMockMvc;

    private Inscription inscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InscriptionResource inscriptionResource = new InscriptionResource(inscriptionRepository, mockInscriptionSearchRepository);
        this.restInscriptionMockMvc = MockMvcBuilders.standaloneSetup(inscriptionResource)
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
    public static Inscription createEntity(EntityManager em) {
        Inscription inscription = new Inscription()
            .role(DEFAULT_ROLE)
            .user(DEFAULT_USER)
            .course(DEFAULT_COURSE);
        return inscription;
    }

    @Before
    public void initTest() {
        inscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscription() throws Exception {
        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();

        // Create the Inscription
        restInscriptionMockMvc.perform(post("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscription)))
            .andExpect(status().isCreated());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testInscription.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testInscription.getCourse()).isEqualTo(DEFAULT_COURSE);

        // Validate the Inscription in Elasticsearch
        verify(mockInscriptionSearchRepository, times(1)).save(testInscription);
    }

    @Test
    @Transactional
    public void createInscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();

        // Create the Inscription with an existing ID
        inscription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscriptionMockMvc.perform(post("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscription)))
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Inscription in Elasticsearch
        verify(mockInscriptionSearchRepository, times(0)).save(inscription);
    }

    @Test
    @Transactional
    public void getAllInscriptions() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList
        restInscriptionMockMvc.perform(get("/api/inscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE)));
    }
    
    @Test
    @Transactional
    public void getInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get the inscription
        restInscriptionMockMvc.perform(get("/api/inscriptions/{id}", inscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscription.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE));
    }

    @Test
    @Transactional
    public void getNonExistingInscription() throws Exception {
        // Get the inscription
        restInscriptionMockMvc.perform(get("/api/inscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Update the inscription
        Inscription updatedInscription = inscriptionRepository.findById(inscription.getId()).get();
        // Disconnect from session so that the updates on updatedInscription are not directly saved in db
        em.detach(updatedInscription);
        updatedInscription
            .role(UPDATED_ROLE)
            .user(UPDATED_USER)
            .course(UPDATED_COURSE);

        restInscriptionMockMvc.perform(put("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInscription)))
            .andExpect(status().isOk());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testInscription.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testInscription.getCourse()).isEqualTo(UPDATED_COURSE);

        // Validate the Inscription in Elasticsearch
        verify(mockInscriptionSearchRepository, times(1)).save(testInscription);
    }

    @Test
    @Transactional
    public void updateNonExistingInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Create the Inscription

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscriptionMockMvc.perform(put("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscription)))
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Inscription in Elasticsearch
        verify(mockInscriptionSearchRepository, times(0)).save(inscription);
    }

    @Test
    @Transactional
    public void deleteInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeDelete = inscriptionRepository.findAll().size();

        // Get the inscription
        restInscriptionMockMvc.perform(delete("/api/inscriptions/{id}", inscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Inscription in Elasticsearch
        verify(mockInscriptionSearchRepository, times(1)).deleteById(inscription.getId());
    }

    @Test
    @Transactional
    public void searchInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);
        when(mockInscriptionSearchRepository.search(queryStringQuery("id:" + inscription.getId())))
            .thenReturn(Collections.singletonList(inscription));
        // Search the inscription
        restInscriptionMockMvc.perform(get("/api/_search/inscriptions?query=id:" + inscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inscription.class);
        Inscription inscription1 = new Inscription();
        inscription1.setId(1L);
        Inscription inscription2 = new Inscription();
        inscription2.setId(inscription1.getId());
        assertThat(inscription1).isEqualTo(inscription2);
        inscription2.setId(2L);
        assertThat(inscription1).isNotEqualTo(inscription2);
        inscription1.setId(null);
        assertThat(inscription1).isNotEqualTo(inscription2);
    }
}
