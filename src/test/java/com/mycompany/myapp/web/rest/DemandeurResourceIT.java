package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Demandeur;
import com.mycompany.myapp.domain.enumeration.Profil;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.repository.DemandeurRepository;
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
 * Integration tests for the {@link DemandeurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISS = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.MASCULIN;
    private static final Sexe UPDATED_SEXE = Sexe.FEMININ;

    private static final Long DEFAULT_TELEPHONE = 1L;
    private static final Long UPDATED_TELEPHONE = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Profil DEFAULT_PROFIL = Profil.ELEVE;
    private static final Profil UPDATED_PROFIL = Profil.ETUDIANT;

    private static final String ENTITY_API_URL = "/api/demandeurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeurMockMvc;

    private Demandeur demandeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandeur createEntity(EntityManager em) {
        Demandeur demandeur = new Demandeur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .lieuNaiss(DEFAULT_LIEU_NAISS)
            .sexe(DEFAULT_SEXE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL)
            .profil(DEFAULT_PROFIL);
        return demandeur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandeur createUpdatedEntity(EntityManager em) {
        Demandeur demandeur = new Demandeur()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .profil(UPDATED_PROFIL);
        return demandeur;
    }

    @BeforeEach
    public void initTest() {
        demandeur = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeur() throws Exception {
        int databaseSizeBeforeCreate = demandeurRepository.findAll().size();
        // Create the Demandeur
        restDemandeurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isCreated());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeCreate + 1);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeur.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testDemandeur.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testDemandeur.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeur.getProfil()).isEqualTo(DEFAULT_PROFIL);
    }

    @Test
    @Transactional
    void createDemandeurWithExistingId() throws Exception {
        // Create the Demandeur with an existing ID
        demandeur.setId(1L);

        int databaseSizeBeforeCreate = demandeurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandeurs() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        // Get all the demandeurList
        restDemandeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].lieuNaiss").value(hasItem(DEFAULT_LIEU_NAISS)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].profil").value(hasItem(DEFAULT_PROFIL.toString())));
    }

    @Test
    @Transactional
    void getDemandeur() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        // Get the demandeur
        restDemandeurMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.lieuNaiss").value(DEFAULT_LIEU_NAISS))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.profil").value(DEFAULT_PROFIL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemandeur() throws Exception {
        // Get the demandeur
        restDemandeurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeur() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();

        // Update the demandeur
        Demandeur updatedDemandeur = demandeurRepository.findById(demandeur.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeur are not directly saved in db
        em.detach(updatedDemandeur);
        updatedDemandeur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .profil(UPDATED_PROFIL);

        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeur.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testDemandeur.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testDemandeur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeur.getProfil()).isEqualTo(UPDATED_PROFIL);
    }

    @Test
    @Transactional
    void putNonExistingDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeurWithPatch() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();

        // Update the demandeur using partial update
        Demandeur partialUpdatedDemandeur = new Demandeur();
        partialUpdatedDemandeur.setId(demandeur.getId());

        partialUpdatedDemandeur.nom(UPDATED_NOM).sexe(UPDATED_SEXE).telephone(UPDATED_TELEPHONE).profil(UPDATED_PROFIL);

        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDemandeur.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testDemandeur.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testDemandeur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDemandeur.getProfil()).isEqualTo(UPDATED_PROFIL);
    }

    @Test
    @Transactional
    void fullUpdateDemandeurWithPatch() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();

        // Update the demandeur using partial update
        Demandeur partialUpdatedDemandeur = new Demandeur();
        partialUpdatedDemandeur.setId(demandeur.getId());

        partialUpdatedDemandeur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL)
            .profil(UPDATED_PROFIL);

        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeur))
            )
            .andExpect(status().isOk());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
        Demandeur testDemandeur = demandeurList.get(demandeurList.size() - 1);
        assertThat(testDemandeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDemandeur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDemandeur.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testDemandeur.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testDemandeur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDemandeur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDemandeur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDemandeur.getProfil()).isEqualTo(UPDATED_PROFIL);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeur() throws Exception {
        int databaseSizeBeforeUpdate = demandeurRepository.findAll().size();
        demandeur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demandeur in the database
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeur() throws Exception {
        // Initialize the database
        demandeurRepository.saveAndFlush(demandeur);

        int databaseSizeBeforeDelete = demandeurRepository.findAll().size();

        // Delete the demandeur
        restDemandeurMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeur.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Demandeur> demandeurList = demandeurRepository.findAll();
        assertThat(demandeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
