package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.SessionForm;
import com.mycompany.myapp.repository.SessionFormRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SessionFormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SessionFormResourceIT {

    private static final String DEFAULT_NOM_SESSION = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SESSION = "BBBBBBBBBB";

    private static final String DEFAULT_ANNEE = "AAAAAAAAAA";
    private static final String UPDATED_ANNEE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT_SESS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT_SESS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_SESS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_SESS = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/session-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SessionFormRepository sessionFormRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionFormMockMvc;

    private SessionForm sessionForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionForm createEntity(EntityManager em) {
        SessionForm sessionForm = new SessionForm()
            .nomSession(DEFAULT_NOM_SESSION)
            .annee(DEFAULT_ANNEE)
            .dateDebutSess(DEFAULT_DATE_DEBUT_SESS)
            .dateFinSess(DEFAULT_DATE_FIN_SESS);
        return sessionForm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionForm createUpdatedEntity(EntityManager em) {
        SessionForm sessionForm = new SessionForm()
            .nomSession(UPDATED_NOM_SESSION)
            .annee(UPDATED_ANNEE)
            .dateDebutSess(UPDATED_DATE_DEBUT_SESS)
            .dateFinSess(UPDATED_DATE_FIN_SESS);
        return sessionForm;
    }

    @BeforeEach
    public void initTest() {
        sessionForm = createEntity(em);
    }

    @Test
    @Transactional
    void createSessionForm() throws Exception {
        int databaseSizeBeforeCreate = sessionFormRepository.findAll().size();
        // Create the SessionForm
        restSessionFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isCreated());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeCreate + 1);
        SessionForm testSessionForm = sessionFormList.get(sessionFormList.size() - 1);
        assertThat(testSessionForm.getNomSession()).isEqualTo(DEFAULT_NOM_SESSION);
        assertThat(testSessionForm.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSessionForm.getDateDebutSess()).isEqualTo(DEFAULT_DATE_DEBUT_SESS);
        assertThat(testSessionForm.getDateFinSess()).isEqualTo(DEFAULT_DATE_FIN_SESS);
    }

    @Test
    @Transactional
    void createSessionFormWithExistingId() throws Exception {
        // Create the SessionForm with an existing ID
        sessionForm.setId(1L);

        int databaseSizeBeforeCreate = sessionFormRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSessionForms() throws Exception {
        // Initialize the database
        sessionFormRepository.saveAndFlush(sessionForm);

        // Get all the sessionFormList
        restSessionFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomSession").value(hasItem(DEFAULT_NOM_SESSION)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].dateDebutSess").value(hasItem(DEFAULT_DATE_DEBUT_SESS.toString())))
            .andExpect(jsonPath("$.[*].dateFinSess").value(hasItem(DEFAULT_DATE_FIN_SESS.toString())));
    }

    @Test
    @Transactional
    void getSessionForm() throws Exception {
        // Initialize the database
        sessionFormRepository.saveAndFlush(sessionForm);

        // Get the sessionForm
        restSessionFormMockMvc
            .perform(get(ENTITY_API_URL_ID, sessionForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sessionForm.getId().intValue()))
            .andExpect(jsonPath("$.nomSession").value(DEFAULT_NOM_SESSION))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.dateDebutSess").value(DEFAULT_DATE_DEBUT_SESS.toString()))
            .andExpect(jsonPath("$.dateFinSess").value(DEFAULT_DATE_FIN_SESS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSessionForm() throws Exception {
        // Get the sessionForm
        restSessionFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSessionForm() throws Exception {
        // Initialize the database
        sessionFormRepository.saveAndFlush(sessionForm);

        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();

        // Update the sessionForm
        SessionForm updatedSessionForm = sessionFormRepository.findById(sessionForm.getId()).get();
        // Disconnect from session so that the updates on updatedSessionForm are not directly saved in db
        em.detach(updatedSessionForm);
        updatedSessionForm
            .nomSession(UPDATED_NOM_SESSION)
            .annee(UPDATED_ANNEE)
            .dateDebutSess(UPDATED_DATE_DEBUT_SESS)
            .dateFinSess(UPDATED_DATE_FIN_SESS);

        restSessionFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSessionForm.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSessionForm))
            )
            .andExpect(status().isOk());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
        SessionForm testSessionForm = sessionFormList.get(sessionFormList.size() - 1);
        assertThat(testSessionForm.getNomSession()).isEqualTo(UPDATED_NOM_SESSION);
        assertThat(testSessionForm.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSessionForm.getDateDebutSess()).isEqualTo(UPDATED_DATE_DEBUT_SESS);
        assertThat(testSessionForm.getDateFinSess()).isEqualTo(UPDATED_DATE_FIN_SESS);
    }

    @Test
    @Transactional
    void putNonExistingSessionForm() throws Exception {
        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();
        sessionForm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionForm.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSessionForm() throws Exception {
        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();
        sessionForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSessionForm() throws Exception {
        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();
        sessionForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionFormMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSessionFormWithPatch() throws Exception {
        // Initialize the database
        sessionFormRepository.saveAndFlush(sessionForm);

        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();

        // Update the sessionForm using partial update
        SessionForm partialUpdatedSessionForm = new SessionForm();
        partialUpdatedSessionForm.setId(sessionForm.getId());

        partialUpdatedSessionForm.nomSession(UPDATED_NOM_SESSION).annee(UPDATED_ANNEE);

        restSessionFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionForm.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSessionForm))
            )
            .andExpect(status().isOk());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
        SessionForm testSessionForm = sessionFormList.get(sessionFormList.size() - 1);
        assertThat(testSessionForm.getNomSession()).isEqualTo(UPDATED_NOM_SESSION);
        assertThat(testSessionForm.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSessionForm.getDateDebutSess()).isEqualTo(DEFAULT_DATE_DEBUT_SESS);
        assertThat(testSessionForm.getDateFinSess()).isEqualTo(DEFAULT_DATE_FIN_SESS);
    }

    @Test
    @Transactional
    void fullUpdateSessionFormWithPatch() throws Exception {
        // Initialize the database
        sessionFormRepository.saveAndFlush(sessionForm);

        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();

        // Update the sessionForm using partial update
        SessionForm partialUpdatedSessionForm = new SessionForm();
        partialUpdatedSessionForm.setId(sessionForm.getId());

        partialUpdatedSessionForm
            .nomSession(UPDATED_NOM_SESSION)
            .annee(UPDATED_ANNEE)
            .dateDebutSess(UPDATED_DATE_DEBUT_SESS)
            .dateFinSess(UPDATED_DATE_FIN_SESS);

        restSessionFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionForm.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSessionForm))
            )
            .andExpect(status().isOk());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
        SessionForm testSessionForm = sessionFormList.get(sessionFormList.size() - 1);
        assertThat(testSessionForm.getNomSession()).isEqualTo(UPDATED_NOM_SESSION);
        assertThat(testSessionForm.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSessionForm.getDateDebutSess()).isEqualTo(UPDATED_DATE_DEBUT_SESS);
        assertThat(testSessionForm.getDateFinSess()).isEqualTo(UPDATED_DATE_FIN_SESS);
    }

    @Test
    @Transactional
    void patchNonExistingSessionForm() throws Exception {
        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();
        sessionForm.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sessionForm.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSessionForm() throws Exception {
        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();
        sessionForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSessionForm() throws Exception {
        int databaseSizeBeforeUpdate = sessionFormRepository.findAll().size();
        sessionForm.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionFormMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sessionForm))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionForm in the database
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSessionForm() throws Exception {
        // Initialize the database
        sessionFormRepository.saveAndFlush(sessionForm);

        int databaseSizeBeforeDelete = sessionFormRepository.findAll().size();

        // Delete the sessionForm
        restSessionFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, sessionForm.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SessionForm> sessionFormList = sessionFormRepository.findAll();
        assertThat(sessionFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
