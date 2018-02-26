package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Randonnee;
import io.github.jhipster.application.repository.RandonneeRepository;
import io.github.jhipster.application.service.RandonneeService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.sameInstant;
import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RandonneeResource REST controller.
 *
 * @see RandonneeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class RandonneeResourceIntTest {

    private static final String DEFAULT_LIEU_DE_RDV = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_DE_RDV = "BBBBBBBBBB";

    private static final Integer DEFAULT_DENIVELE_POSITIF = 1;
    private static final Integer UPDATED_DENIVELE_POSITIF = 2;

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RandonneeRepository randonneeRepository;

    @Autowired
    private RandonneeService randonneeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRandonneeMockMvc;

    private Randonnee randonnee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RandonneeResource randonneeResource = new RandonneeResource(randonneeService);
        this.restRandonneeMockMvc = MockMvcBuilders.standaloneSetup(randonneeResource)
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
    public static Randonnee createEntity(EntityManager em) {
        Randonnee randonnee = new Randonnee()
            .lieuDeRDV(DEFAULT_LIEU_DE_RDV)
            .denivelePositif(DEFAULT_DENIVELE_POSITIF)
            .duree(DEFAULT_DUREE)
            .date(DEFAULT_DATE);
        return randonnee;
    }

    @Before
    public void initTest() {
        randonnee = createEntity(em);
    }

    @Test
    @Transactional
    public void createRandonnee() throws Exception {
        int databaseSizeBeforeCreate = randonneeRepository.findAll().size();

        // Create the Randonnee
        restRandonneeMockMvc.perform(post("/api/randonnees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonnee)))
            .andExpect(status().isCreated());

        // Validate the Randonnee in the database
        List<Randonnee> randonneeList = randonneeRepository.findAll();
        assertThat(randonneeList).hasSize(databaseSizeBeforeCreate + 1);
        Randonnee testRandonnee = randonneeList.get(randonneeList.size() - 1);
        assertThat(testRandonnee.getLieuDeRDV()).isEqualTo(DEFAULT_LIEU_DE_RDV);
        assertThat(testRandonnee.getDenivelePositif()).isEqualTo(DEFAULT_DENIVELE_POSITIF);
        assertThat(testRandonnee.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testRandonnee.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createRandonneeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = randonneeRepository.findAll().size();

        // Create the Randonnee with an existing ID
        randonnee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRandonneeMockMvc.perform(post("/api/randonnees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonnee)))
            .andExpect(status().isBadRequest());

        // Validate the Randonnee in the database
        List<Randonnee> randonneeList = randonneeRepository.findAll();
        assertThat(randonneeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRandonnees() throws Exception {
        // Initialize the database
        randonneeRepository.saveAndFlush(randonnee);

        // Get all the randonneeList
        restRandonneeMockMvc.perform(get("/api/randonnees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(randonnee.getId().intValue())))
            .andExpect(jsonPath("$.[*].lieuDeRDV").value(hasItem(DEFAULT_LIEU_DE_RDV.toString())))
            .andExpect(jsonPath("$.[*].denivelePositif").value(hasItem(DEFAULT_DENIVELE_POSITIF)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    public void getRandonnee() throws Exception {
        // Initialize the database
        randonneeRepository.saveAndFlush(randonnee);

        // Get the randonnee
        restRandonneeMockMvc.perform(get("/api/randonnees/{id}", randonnee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(randonnee.getId().intValue()))
            .andExpect(jsonPath("$.lieuDeRDV").value(DEFAULT_LIEU_DE_RDV.toString()))
            .andExpect(jsonPath("$.denivelePositif").value(DEFAULT_DENIVELE_POSITIF))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingRandonnee() throws Exception {
        // Get the randonnee
        restRandonneeMockMvc.perform(get("/api/randonnees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRandonnee() throws Exception {
        // Initialize the database
        randonneeService.save(randonnee);

        int databaseSizeBeforeUpdate = randonneeRepository.findAll().size();

        // Update the randonnee
        Randonnee updatedRandonnee = randonneeRepository.findOne(randonnee.getId());
        // Disconnect from session so that the updates on updatedRandonnee are not directly saved in db
        em.detach(updatedRandonnee);
        updatedRandonnee
            .lieuDeRDV(UPDATED_LIEU_DE_RDV)
            .denivelePositif(UPDATED_DENIVELE_POSITIF)
            .duree(UPDATED_DUREE)
            .date(UPDATED_DATE);

        restRandonneeMockMvc.perform(put("/api/randonnees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRandonnee)))
            .andExpect(status().isOk());

        // Validate the Randonnee in the database
        List<Randonnee> randonneeList = randonneeRepository.findAll();
        assertThat(randonneeList).hasSize(databaseSizeBeforeUpdate);
        Randonnee testRandonnee = randonneeList.get(randonneeList.size() - 1);
        assertThat(testRandonnee.getLieuDeRDV()).isEqualTo(UPDATED_LIEU_DE_RDV);
        assertThat(testRandonnee.getDenivelePositif()).isEqualTo(UPDATED_DENIVELE_POSITIF);
        assertThat(testRandonnee.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testRandonnee.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRandonnee() throws Exception {
        int databaseSizeBeforeUpdate = randonneeRepository.findAll().size();

        // Create the Randonnee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRandonneeMockMvc.perform(put("/api/randonnees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonnee)))
            .andExpect(status().isCreated());

        // Validate the Randonnee in the database
        List<Randonnee> randonneeList = randonneeRepository.findAll();
        assertThat(randonneeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRandonnee() throws Exception {
        // Initialize the database
        randonneeService.save(randonnee);

        int databaseSizeBeforeDelete = randonneeRepository.findAll().size();

        // Get the randonnee
        restRandonneeMockMvc.perform(delete("/api/randonnees/{id}", randonnee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Randonnee> randonneeList = randonneeRepository.findAll();
        assertThat(randonneeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Randonnee.class);
        Randonnee randonnee1 = new Randonnee();
        randonnee1.setId(1L);
        Randonnee randonnee2 = new Randonnee();
        randonnee2.setId(randonnee1.getId());
        assertThat(randonnee1).isEqualTo(randonnee2);
        randonnee2.setId(2L);
        assertThat(randonnee1).isNotEqualTo(randonnee2);
        randonnee1.setId(null);
        assertThat(randonnee1).isNotEqualTo(randonnee2);
    }
}
