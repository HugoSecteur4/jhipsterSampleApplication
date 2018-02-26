package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Randonneur;
import io.github.jhipster.application.repository.RandonneurRepository;
import io.github.jhipster.application.service.RandonneurService;
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

import io.github.jhipster.application.domain.enumeration.Sexe;
/**
 * Test class for the RandonneurResource REST controller.
 *
 * @see RandonneurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class RandonneurResourceIntTest {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.MASCULIN;
    private static final Sexe UPDATED_SEXE = Sexe.FEMININ;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final ZonedDateTime DEFAULT_DATE_DE_NAISSANCE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DE_NAISSANCE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RandonneurRepository randonneurRepository;

    @Autowired
    private RandonneurService randonneurService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRandonneurMockMvc;

    private Randonneur randonneur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RandonneurResource randonneurResource = new RandonneurResource(randonneurService);
        this.restRandonneurMockMvc = MockMvcBuilders.standaloneSetup(randonneurResource)
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
    public static Randonneur createEntity(EntityManager em) {
        Randonneur randonneur = new Randonneur()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .sexe(DEFAULT_SEXE)
            .age(DEFAULT_AGE)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE);
        return randonneur;
    }

    @Before
    public void initTest() {
        randonneur = createEntity(em);
    }

    @Test
    @Transactional
    public void createRandonneur() throws Exception {
        int databaseSizeBeforeCreate = randonneurRepository.findAll().size();

        // Create the Randonneur
        restRandonneurMockMvc.perform(post("/api/randonneurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonneur)))
            .andExpect(status().isCreated());

        // Validate the Randonneur in the database
        List<Randonneur> randonneurList = randonneurRepository.findAll();
        assertThat(randonneurList).hasSize(databaseSizeBeforeCreate + 1);
        Randonneur testRandonneur = randonneurList.get(randonneurList.size() - 1);
        assertThat(testRandonneur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRandonneur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRandonneur.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testRandonneur.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testRandonneur.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void createRandonneurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = randonneurRepository.findAll().size();

        // Create the Randonneur with an existing ID
        randonneur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRandonneurMockMvc.perform(post("/api/randonneurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonneur)))
            .andExpect(status().isBadRequest());

        // Validate the Randonneur in the database
        List<Randonneur> randonneurList = randonneurRepository.findAll();
        assertThat(randonneurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = randonneurRepository.findAll().size();
        // set the field null
        randonneur.setPrenom(null);

        // Create the Randonneur, which fails.

        restRandonneurMockMvc.perform(post("/api/randonneurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonneur)))
            .andExpect(status().isBadRequest());

        List<Randonneur> randonneurList = randonneurRepository.findAll();
        assertThat(randonneurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = randonneurRepository.findAll().size();
        // set the field null
        randonneur.setNom(null);

        // Create the Randonneur, which fails.

        restRandonneurMockMvc.perform(post("/api/randonneurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonneur)))
            .andExpect(status().isBadRequest());

        List<Randonneur> randonneurList = randonneurRepository.findAll();
        assertThat(randonneurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRandonneurs() throws Exception {
        // Initialize the database
        randonneurRepository.saveAndFlush(randonneur);

        // Get all the randonneurList
        restRandonneurMockMvc.perform(get("/api/randonneurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(randonneur.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(sameInstant(DEFAULT_DATE_DE_NAISSANCE))));
    }

    @Test
    @Transactional
    public void getRandonneur() throws Exception {
        // Initialize the database
        randonneurRepository.saveAndFlush(randonneur);

        // Get the randonneur
        restRandonneurMockMvc.perform(get("/api/randonneurs/{id}", randonneur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(randonneur.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.dateDeNaissance").value(sameInstant(DEFAULT_DATE_DE_NAISSANCE)));
    }

    @Test
    @Transactional
    public void getNonExistingRandonneur() throws Exception {
        // Get the randonneur
        restRandonneurMockMvc.perform(get("/api/randonneurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRandonneur() throws Exception {
        // Initialize the database
        randonneurService.save(randonneur);

        int databaseSizeBeforeUpdate = randonneurRepository.findAll().size();

        // Update the randonneur
        Randonneur updatedRandonneur = randonneurRepository.findOne(randonneur.getId());
        // Disconnect from session so that the updates on updatedRandonneur are not directly saved in db
        em.detach(updatedRandonneur);
        updatedRandonneur
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .sexe(UPDATED_SEXE)
            .age(UPDATED_AGE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE);

        restRandonneurMockMvc.perform(put("/api/randonneurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRandonneur)))
            .andExpect(status().isOk());

        // Validate the Randonneur in the database
        List<Randonneur> randonneurList = randonneurRepository.findAll();
        assertThat(randonneurList).hasSize(databaseSizeBeforeUpdate);
        Randonneur testRandonneur = randonneurList.get(randonneurList.size() - 1);
        assertThat(testRandonneur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRandonneur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRandonneur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testRandonneur.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testRandonneur.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingRandonneur() throws Exception {
        int databaseSizeBeforeUpdate = randonneurRepository.findAll().size();

        // Create the Randonneur

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRandonneurMockMvc.perform(put("/api/randonneurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(randonneur)))
            .andExpect(status().isCreated());

        // Validate the Randonneur in the database
        List<Randonneur> randonneurList = randonneurRepository.findAll();
        assertThat(randonneurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRandonneur() throws Exception {
        // Initialize the database
        randonneurService.save(randonneur);

        int databaseSizeBeforeDelete = randonneurRepository.findAll().size();

        // Get the randonneur
        restRandonneurMockMvc.perform(delete("/api/randonneurs/{id}", randonneur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Randonneur> randonneurList = randonneurRepository.findAll();
        assertThat(randonneurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Randonneur.class);
        Randonneur randonneur1 = new Randonneur();
        randonneur1.setId(1L);
        Randonneur randonneur2 = new Randonneur();
        randonneur2.setId(randonneur1.getId());
        assertThat(randonneur1).isEqualTo(randonneur2);
        randonneur2.setId(2L);
        assertThat(randonneur1).isNotEqualTo(randonneur2);
        randonneur1.setId(null);
        assertThat(randonneur1).isNotEqualTo(randonneur2);
    }
}
