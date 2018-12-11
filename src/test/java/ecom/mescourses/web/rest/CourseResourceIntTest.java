package ecom.mescourses.web.rest;

import ecom.mescourses.MesCoursesApp;

import ecom.mescourses.domain.Course;
import ecom.mescourses.repository.CourseRepository;
import ecom.mescourses.repository.search.CourseSearchRepository;
import ecom.mescourses.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static ecom.mescourses.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ecom.mescourses.domain.enumeration.Sport;
/**
 * Test class for the CourseResource REST controller.
 *
 * @see CourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MesCoursesApp.class)
public class CourseResourceIntTest {

    private static final Integer DEFAULT_ORGANISATEUR = 1;
    private static final Integer UPDATED_ORGANISATEUR = 2;

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Sport DEFAULT_SPORT = Sport.COURSE_A_PIED;
    private static final Sport UPDATED_SPORT = Sport.MARATHON;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HEURE = "AAAAAAAAAA";
    private static final String UPDATED_HEURE = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final byte[] DEFAULT_IMAGE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_4_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_5 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_5 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_5_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_5_CONTENT_TYPE = "image/png";

    @Autowired
    private CourseRepository courseRepository;

    @Mock
    private CourseRepository courseRepositoryMock;

    /**
     * This repository is mocked in the ecom.mescourses.repository.search test package.
     *
     * @see ecom.mescourses.repository.search.CourseSearchRepositoryMockConfiguration
     */
    @Autowired
    private CourseSearchRepository mockCourseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCourseMockMvc;

    private Course course;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseResource courseResource = new CourseResource(courseRepository, mockCourseSearchRepository);
        this.restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
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
    public static Course createEntity(EntityManager em) {
        Course course = new Course()
            .organisateur(DEFAULT_ORGANISATEUR)
            .titre(DEFAULT_TITRE)
            .description(DEFAULT_DESCRIPTION)
            .sport(DEFAULT_SPORT)
            .date(DEFAULT_DATE)
            .heure(DEFAULT_HEURE)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .lieu(DEFAULT_LIEU)
            .prix(DEFAULT_PRIX)
            .image1(DEFAULT_IMAGE_1)
            .image1ContentType(DEFAULT_IMAGE_1_CONTENT_TYPE)
            .image2(DEFAULT_IMAGE_2)
            .image2ContentType(DEFAULT_IMAGE_2_CONTENT_TYPE)
            .image3(DEFAULT_IMAGE_3)
            .image3ContentType(DEFAULT_IMAGE_3_CONTENT_TYPE)
            .image4(DEFAULT_IMAGE_4)
            .image4ContentType(DEFAULT_IMAGE_4_CONTENT_TYPE)
            .image5(DEFAULT_IMAGE_5)
            .image5ContentType(DEFAULT_IMAGE_5_CONTENT_TYPE);
        return course;
    }

    @Before
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getOrganisateur()).isEqualTo(DEFAULT_ORGANISATEUR);
        assertThat(testCourse.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testCourse.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourse.getSport()).isEqualTo(DEFAULT_SPORT);
        assertThat(testCourse.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCourse.getHeure()).isEqualTo(DEFAULT_HEURE);
        assertThat(testCourse.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testCourse.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCourse.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testCourse.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testCourse.getImage1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testCourse.getImage1ContentType()).isEqualTo(DEFAULT_IMAGE_1_CONTENT_TYPE);
        assertThat(testCourse.getImage2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testCourse.getImage2ContentType()).isEqualTo(DEFAULT_IMAGE_2_CONTENT_TYPE);
        assertThat(testCourse.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testCourse.getImage3ContentType()).isEqualTo(DEFAULT_IMAGE_3_CONTENT_TYPE);
        assertThat(testCourse.getImage4()).isEqualTo(DEFAULT_IMAGE_4);
        assertThat(testCourse.getImage4ContentType()).isEqualTo(DEFAULT_IMAGE_4_CONTENT_TYPE);
        assertThat(testCourse.getImage5()).isEqualTo(DEFAULT_IMAGE_5);
        assertThat(testCourse.getImage5ContentType()).isEqualTo(DEFAULT_IMAGE_5_CONTENT_TYPE);

        // Validate the Course in Elasticsearch
        verify(mockCourseSearchRepository, times(1)).save(testCourse);
    }

    @Test
    @Transactional
    public void createCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course with an existing ID
        course.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Course in Elasticsearch
        verify(mockCourseSearchRepository, times(0)).save(course);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].organisateur").value(hasItem(DEFAULT_ORGANISATEUR)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sport").value(hasItem(DEFAULT_SPORT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].image1ContentType").value(hasItem(DEFAULT_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_1))))
            .andExpect(jsonPath("$.[*].image2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))))
            .andExpect(jsonPath("$.[*].image3ContentType").value(hasItem(DEFAULT_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3))))
            .andExpect(jsonPath("$.[*].image4ContentType").value(hasItem(DEFAULT_IMAGE_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_4))))
            .andExpect(jsonPath("$.[*].image5ContentType").value(hasItem(DEFAULT_IMAGE_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_5))));
    }
    
    public void getAllCoursesWithEagerRelationshipsIsEnabled() throws Exception {
        CourseResource courseResource = new CourseResource(courseRepositoryMock, mockCourseSearchRepository);
        when(courseRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCourseMockMvc.perform(get("/api/courses?eagerload=true"))
        .andExpect(status().isOk());

        verify(courseRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllCoursesWithEagerRelationshipsIsNotEnabled() throws Exception {
        CourseResource courseResource = new CourseResource(courseRepositoryMock, mockCourseSearchRepository);
            when(courseRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCourseMockMvc.perform(get("/api/courses?eagerload=true"))
        .andExpect(status().isOk());

            verify(courseRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.organisateur").value(DEFAULT_ORGANISATEUR))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sport").value(DEFAULT_SPORT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.heure").value(DEFAULT_HEURE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.image1ContentType").value(DEFAULT_IMAGE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.image1").value(Base64Utils.encodeToString(DEFAULT_IMAGE_1)))
            .andExpect(jsonPath("$.image2ContentType").value(DEFAULT_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.image2").value(Base64Utils.encodeToString(DEFAULT_IMAGE_2)))
            .andExpect(jsonPath("$.image3ContentType").value(DEFAULT_IMAGE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.image3").value(Base64Utils.encodeToString(DEFAULT_IMAGE_3)))
            .andExpect(jsonPath("$.image4ContentType").value(DEFAULT_IMAGE_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.image4").value(Base64Utils.encodeToString(DEFAULT_IMAGE_4)))
            .andExpect(jsonPath("$.image5ContentType").value(DEFAULT_IMAGE_5_CONTENT_TYPE))
            .andExpect(jsonPath("$.image5").value(Base64Utils.encodeToString(DEFAULT_IMAGE_5)));
    }

    @Test
    @Transactional
    public void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = courseRepository.findById(course.getId()).get();
        // Disconnect from session so that the updates on updatedCourse are not directly saved in db
        em.detach(updatedCourse);
        updatedCourse
            .organisateur(UPDATED_ORGANISATEUR)
            .titre(UPDATED_TITRE)
            .description(UPDATED_DESCRIPTION)
            .sport(UPDATED_SPORT)
            .date(UPDATED_DATE)
            .heure(UPDATED_HEURE)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .lieu(UPDATED_LIEU)
            .prix(UPDATED_PRIX)
            .image1(UPDATED_IMAGE_1)
            .image1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE)
            .image4(UPDATED_IMAGE_4)
            .image4ContentType(UPDATED_IMAGE_4_CONTENT_TYPE)
            .image5(UPDATED_IMAGE_5)
            .image5ContentType(UPDATED_IMAGE_5_CONTENT_TYPE);

        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourse)))
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getOrganisateur()).isEqualTo(UPDATED_ORGANISATEUR);
        assertThat(testCourse.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testCourse.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourse.getSport()).isEqualTo(UPDATED_SPORT);
        assertThat(testCourse.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCourse.getHeure()).isEqualTo(UPDATED_HEURE);
        assertThat(testCourse.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCourse.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCourse.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testCourse.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testCourse.getImage1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testCourse.getImage1ContentType()).isEqualTo(UPDATED_IMAGE_1_CONTENT_TYPE);
        assertThat(testCourse.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testCourse.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testCourse.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testCourse.getImage3ContentType()).isEqualTo(UPDATED_IMAGE_3_CONTENT_TYPE);
        assertThat(testCourse.getImage4()).isEqualTo(UPDATED_IMAGE_4);
        assertThat(testCourse.getImage4ContentType()).isEqualTo(UPDATED_IMAGE_4_CONTENT_TYPE);
        assertThat(testCourse.getImage5()).isEqualTo(UPDATED_IMAGE_5);
        assertThat(testCourse.getImage5ContentType()).isEqualTo(UPDATED_IMAGE_5_CONTENT_TYPE);

        // Validate the Course in Elasticsearch
        verify(mockCourseSearchRepository, times(1)).save(testCourse);
    }

    @Test
    @Transactional
    public void updateNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Create the Course

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Course in Elasticsearch
        verify(mockCourseSearchRepository, times(0)).save(course);
    }

    @Test
    @Transactional
    public void deleteCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Get the course
        restCourseMockMvc.perform(delete("/api/courses/{id}", course.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Course in Elasticsearch
        verify(mockCourseSearchRepository, times(1)).deleteById(course.getId());
    }

    @Test
    @Transactional
    public void searchCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);
        when(mockCourseSearchRepository.search(queryStringQuery("id:" + course.getId())))
            .thenReturn(Collections.singletonList(course));
        // Search the course
        restCourseMockMvc.perform(get("/api/_search/courses?query=id:" + course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].organisateur").value(hasItem(DEFAULT_ORGANISATEUR)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sport").value(hasItem(DEFAULT_SPORT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].heure").value(hasItem(DEFAULT_HEURE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].image1ContentType").value(hasItem(DEFAULT_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_1))))
            .andExpect(jsonPath("$.[*].image2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))))
            .andExpect(jsonPath("$.[*].image3ContentType").value(hasItem(DEFAULT_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3))))
            .andExpect(jsonPath("$.[*].image4ContentType").value(hasItem(DEFAULT_IMAGE_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_4))))
            .andExpect(jsonPath("$.[*].image5ContentType").value(hasItem(DEFAULT_IMAGE_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_5))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);
        course2.setId(2L);
        assertThat(course1).isNotEqualTo(course2);
        course1.setId(null);
        assertThat(course1).isNotEqualTo(course2);
    }
}
