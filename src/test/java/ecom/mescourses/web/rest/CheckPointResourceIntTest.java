package ecom.mescourses.web.rest;

import ecom.mescourses.MesCoursesApp;

import ecom.mescourses.domain.CheckPoint;
import ecom.mescourses.repository.CheckPointRepository;
import ecom.mescourses.repository.search.CheckPointSearchRepository;
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

/**
 * Test class for the CheckPointResource REST controller.
 *
 * @see CheckPointResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesCoursesApp.class)
public class CheckPointResourceIntTest {

    private static final String DEFAULT_HEURE = "AAAAAAAAAA";
    private static final String UPDATED_HEURE = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER = 1;
    private static final Integer UPDATED_USER = 2;

    private static final Integer DEFAULT_COURSE = 1;
    private static final Integer UPDATED_COURSE = 2;

    @Autowired
    private CheckPointRepository checkPointRepository;

    /**
     * This repository is mocked in the ecom.mescourses.repository.search test package.
     *
     * @see ecom.mescourses.repository.search.CheckPointSearchRepositoryMockConfiguration
     */
    @Autowired
    private CheckPointSearchRepository mockCheckPointSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCheckPointMockMvc;

    private CheckPoint checkPoint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckPointResource checkPointResource = new CheckPointResource(checkPointRepository, mockCheckPointSearchRepository);
        this.restCheckPointMockMvc = MockMvcBuilders.standaloneSetup(checkPointResource)
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
    public static CheckPoint createEntity(EntityManager em) {
        CheckPoint checkPoint = new CheckPoint()
            .heure(DEFAULT_HEURE)
            .user(DEFAULT_USER)
            .course(DEFAULT_COURSE);
        return checkPoint;
    }

    @Before
    public void initTest() {
        checkPoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckPoint() throws Exception {
        int databaseSizeBeforeCreate = checkPointRepository.findAll().size();

        // Create the CheckPoint
        restCheckPointMockMvc.perform(post("/api/check-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkPoint)))
            .andExpect(status().isCreated());

        // Validate the CheckPoint in the database
        List<CheckPoint> checkPointList = checkPointRepository.findAll();
        assertThat(checkPointList).hasSize(databaseSizeBeforeCreate + 1);
        CheckPoint testCheckPoint = checkPointList.get(checkPointList.size() - 1);
        assertThat(testCheckPoint.getHeure()).isEqualTo(DEFAULT_HEURE);
        assertThat(testCheckPoint.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testCheckPoint.getCourse()).isEqualTo(DEFAULT_COURSE);

        // Validate the CheckPoint in Elasticsearch
        verify(mockCheckPointSearchRepository, times(1)).save(testCheckPoint);
    }

    @Test
    @Transactional
    public void createCheckPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkPointRepository.findAll().size();

        // Create the CheckPoint with an existing ID
        checkPoint.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckPointMockMvc.perform(post("/api/check-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkPoint)))
            .andExpect(status().isBadRequest());

        // Validate the CheckPoint in the database
        List<CheckPoint> checkPointList = checkPointRepository.findAll();
        assertThat(checkPointList).hasSize(databaseSizeBeforeCreate);

        // Validate the CheckPoint in Elasticsearch
        verify(mockCheckPointSearchRepository, times(0)).save(checkPoint);
    }

    @Test
    @Transactional
    public void getAllCheckPoints() throws Exception {
        // Initialize the database
        checkPointRepository.saveAndFlush(checkPoint);

        // Get all the checkPointList
        restCheckPointMockMvc.perform(get("/api/check-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE)));
    }
    
    @Test
    @Transactional
    public void getCheckPoint() throws Exception {
        // Initialize the database
        checkPointRepository.saveAndFlush(checkPoint);

        // Get the checkPoint
        restCheckPointMockMvc.perform(get("/api/check-points/{id}", checkPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkPoint.getId().intValue()))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE));
    }

    @Test
    @Transactional
    public void getNonExistingCheckPoint() throws Exception {
        // Get the checkPoint
        restCheckPointMockMvc.perform(get("/api/check-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckPoint() throws Exception {
        // Initialize the database
        checkPointRepository.saveAndFlush(checkPoint);

        int databaseSizeBeforeUpdate = checkPointRepository.findAll().size();

        // Update the checkPoint
        CheckPoint updatedCheckPoint = checkPointRepository.findById(checkPoint.getId()).get();
        // Disconnect from session so that the updates on updatedCheckPoint are not directly saved in db
        em.detach(updatedCheckPoint);
        updatedCheckPoint
            .heure(UPDATED_HEURE)
            .user(UPDATED_USER)
            .course(UPDATED_COURSE);

        restCheckPointMockMvc.perform(put("/api/check-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCheckPoint)))
            .andExpect(status().isOk());

        // Validate the CheckPoint in the database
        List<CheckPoint> checkPointList = checkPointRepository.findAll();
        assertThat(checkPointList).hasSize(databaseSizeBeforeUpdate);
        CheckPoint testCheckPoint = checkPointList.get(checkPointList.size() - 1);
        assertThat(testCheckPoint.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testCheckPoint.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testCheckPoint.getCourse()).isEqualTo(UPDATED_COURSE);

        // Validate the CheckPoint in Elasticsearch
        verify(mockCheckPointSearchRepository, times(1)).save(testCheckPoint);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckPoint() throws Exception {
        int databaseSizeBeforeUpdate = checkPointRepository.findAll().size();

        // Create the CheckPoint

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckPointMockMvc.perform(put("/api/check-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkPoint)))
            .andExpect(status().isBadRequest());

        // Validate the CheckPoint in the database
        List<CheckPoint> checkPointList = checkPointRepository.findAll();
        assertThat(checkPointList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckPoint in Elasticsearch
        verify(mockCheckPointSearchRepository, times(0)).save(checkPoint);
    }

    @Test
    @Transactional
    public void deleteCheckPoint() throws Exception {
        // Initialize the database
        checkPointRepository.saveAndFlush(checkPoint);

        int databaseSizeBeforeDelete = checkPointRepository.findAll().size();

        // Get the checkPoint
        restCheckPointMockMvc.perform(delete("/api/check-points/{id}", checkPoint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CheckPoint> checkPointList = checkPointRepository.findAll();
        assertThat(checkPointList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CheckPoint in Elasticsearch
        verify(mockCheckPointSearchRepository, times(1)).deleteById(checkPoint.getId());
    }

    @Test
    @Transactional
    public void searchCheckPoint() throws Exception {
        // Initialize the database
        checkPointRepository.saveAndFlush(checkPoint);
        when(mockCheckPointSearchRepository.search(queryStringQuery("id:" + checkPoint.getId())))
            .thenReturn(Collections.singletonList(checkPoint));
        // Search the checkPoint
        restCheckPointMockMvc.perform(get("/api/_search/check-points?query=id:" + checkPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckPoint.class);
        CheckPoint checkPoint1 = new CheckPoint();
        checkPoint1.setId(1L);
        CheckPoint checkPoint2 = new CheckPoint();
        checkPoint2.setId(checkPoint1.getId());
        assertThat(checkPoint1).isEqualTo(checkPoint2);
        checkPoint2.setId(2L);
        assertThat(checkPoint1).isNotEqualTo(checkPoint2);
        checkPoint1.setId(null);
        assertThat(checkPoint1).isNotEqualTo(checkPoint2);
    }
}
