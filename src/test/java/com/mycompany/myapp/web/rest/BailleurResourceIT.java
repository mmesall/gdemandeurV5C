package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Bailleur;
import com.mycompany.myapp.repository.BailleurRepository;
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
 * Integration tests for the {@link BailleurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BailleurResourceIT {

    private static final String DEFAULT_NOM_PROJET = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PROJET = "BBBBBBBBBB";

    private static final Double DEFAULT_BUDGET_PREVU = 1D;
    private static final Double UPDATED_BUDGET_PREVU = 2D;

    private static final Double DEFAULT_BUDGET_DEPENSE = 1D;
    private static final Double UPDATED_BUDGET_DEPENSE = 2D;

    private static final Double DEFAULT_BUDGET_RESTANT = 1D;
    private static final Double UPDATED_BUDGET_RESTANT = 2D;

    private static final Long DEFAULT_NBRE_PC = 1L;
    private static final Long UPDATED_NBRE_PC = 2L;

    private static final String ENTITY_API_URL = "/api/bailleurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BailleurRepository bailleurRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBailleurMockMvc;

    private Bailleur bailleur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bailleur createEntity(EntityManager em) {
        Bailleur bailleur = new Bailleur()
            .nomProjet(DEFAULT_NOM_PROJET)
            .budgetPrevu(DEFAULT_BUDGET_PREVU)
            .budgetDepense(DEFAULT_BUDGET_DEPENSE)
            .budgetRestant(DEFAULT_BUDGET_RESTANT)
            .nbrePC(DEFAULT_NBRE_PC);
        return bailleur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bailleur createUpdatedEntity(EntityManager em) {
        Bailleur bailleur = new Bailleur()
            .nomProjet(UPDATED_NOM_PROJET)
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT)
            .nbrePC(UPDATED_NBRE_PC);
        return bailleur;
    }

    @BeforeEach
    public void initTest() {
        bailleur = createEntity(em);
    }

    @Test
    @Transactional
    void createBailleur() throws Exception {
        int databaseSizeBeforeCreate = bailleurRepository.findAll().size();
        // Create the Bailleur
        restBailleurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isCreated());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeCreate + 1);
        Bailleur testBailleur = bailleurList.get(bailleurList.size() - 1);
        assertThat(testBailleur.getNomProjet()).isEqualTo(DEFAULT_NOM_PROJET);
        assertThat(testBailleur.getBudgetPrevu()).isEqualTo(DEFAULT_BUDGET_PREVU);
        assertThat(testBailleur.getBudgetDepense()).isEqualTo(DEFAULT_BUDGET_DEPENSE);
        assertThat(testBailleur.getBudgetRestant()).isEqualTo(DEFAULT_BUDGET_RESTANT);
        assertThat(testBailleur.getNbrePC()).isEqualTo(DEFAULT_NBRE_PC);
    }

    @Test
    @Transactional
    void createBailleurWithExistingId() throws Exception {
        // Create the Bailleur with an existing ID
        bailleur.setId(1L);

        int databaseSizeBeforeCreate = bailleurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBailleurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBailleurs() throws Exception {
        // Initialize the database
        bailleurRepository.saveAndFlush(bailleur);

        // Get all the bailleurList
        restBailleurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bailleur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomProjet").value(hasItem(DEFAULT_NOM_PROJET)))
            .andExpect(jsonPath("$.[*].budgetPrevu").value(hasItem(DEFAULT_BUDGET_PREVU.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetDepense").value(hasItem(DEFAULT_BUDGET_DEPENSE.doubleValue())))
            .andExpect(jsonPath("$.[*].budgetRestant").value(hasItem(DEFAULT_BUDGET_RESTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].nbrePC").value(hasItem(DEFAULT_NBRE_PC.intValue())));
    }

    @Test
    @Transactional
    void getBailleur() throws Exception {
        // Initialize the database
        bailleurRepository.saveAndFlush(bailleur);

        // Get the bailleur
        restBailleurMockMvc
            .perform(get(ENTITY_API_URL_ID, bailleur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bailleur.getId().intValue()))
            .andExpect(jsonPath("$.nomProjet").value(DEFAULT_NOM_PROJET))
            .andExpect(jsonPath("$.budgetPrevu").value(DEFAULT_BUDGET_PREVU.doubleValue()))
            .andExpect(jsonPath("$.budgetDepense").value(DEFAULT_BUDGET_DEPENSE.doubleValue()))
            .andExpect(jsonPath("$.budgetRestant").value(DEFAULT_BUDGET_RESTANT.doubleValue()))
            .andExpect(jsonPath("$.nbrePC").value(DEFAULT_NBRE_PC.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBailleur() throws Exception {
        // Get the bailleur
        restBailleurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBailleur() throws Exception {
        // Initialize the database
        bailleurRepository.saveAndFlush(bailleur);

        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();

        // Update the bailleur
        Bailleur updatedBailleur = bailleurRepository.findById(bailleur.getId()).get();
        // Disconnect from session so that the updates on updatedBailleur are not directly saved in db
        em.detach(updatedBailleur);
        updatedBailleur
            .nomProjet(UPDATED_NOM_PROJET)
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT)
            .nbrePC(UPDATED_NBRE_PC);

        restBailleurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBailleur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBailleur))
            )
            .andExpect(status().isOk());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
        Bailleur testBailleur = bailleurList.get(bailleurList.size() - 1);
        assertThat(testBailleur.getNomProjet()).isEqualTo(UPDATED_NOM_PROJET);
        assertThat(testBailleur.getBudgetPrevu()).isEqualTo(UPDATED_BUDGET_PREVU);
        assertThat(testBailleur.getBudgetDepense()).isEqualTo(UPDATED_BUDGET_DEPENSE);
        assertThat(testBailleur.getBudgetRestant()).isEqualTo(UPDATED_BUDGET_RESTANT);
        assertThat(testBailleur.getNbrePC()).isEqualTo(UPDATED_NBRE_PC);
    }

    @Test
    @Transactional
    void putNonExistingBailleur() throws Exception {
        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();
        bailleur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bailleur.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBailleur() throws Exception {
        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();
        bailleur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBailleur() throws Exception {
        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();
        bailleur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBailleurWithPatch() throws Exception {
        // Initialize the database
        bailleurRepository.saveAndFlush(bailleur);

        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();

        // Update the bailleur using partial update
        Bailleur partialUpdatedBailleur = new Bailleur();
        partialUpdatedBailleur.setId(bailleur.getId());

        partialUpdatedBailleur
            .nomProjet(UPDATED_NOM_PROJET)
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT);

        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBailleur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBailleur))
            )
            .andExpect(status().isOk());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
        Bailleur testBailleur = bailleurList.get(bailleurList.size() - 1);
        assertThat(testBailleur.getNomProjet()).isEqualTo(UPDATED_NOM_PROJET);
        assertThat(testBailleur.getBudgetPrevu()).isEqualTo(UPDATED_BUDGET_PREVU);
        assertThat(testBailleur.getBudgetDepense()).isEqualTo(UPDATED_BUDGET_DEPENSE);
        assertThat(testBailleur.getBudgetRestant()).isEqualTo(UPDATED_BUDGET_RESTANT);
        assertThat(testBailleur.getNbrePC()).isEqualTo(DEFAULT_NBRE_PC);
    }

    @Test
    @Transactional
    void fullUpdateBailleurWithPatch() throws Exception {
        // Initialize the database
        bailleurRepository.saveAndFlush(bailleur);

        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();

        // Update the bailleur using partial update
        Bailleur partialUpdatedBailleur = new Bailleur();
        partialUpdatedBailleur.setId(bailleur.getId());

        partialUpdatedBailleur
            .nomProjet(UPDATED_NOM_PROJET)
            .budgetPrevu(UPDATED_BUDGET_PREVU)
            .budgetDepense(UPDATED_BUDGET_DEPENSE)
            .budgetRestant(UPDATED_BUDGET_RESTANT)
            .nbrePC(UPDATED_NBRE_PC);

        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBailleur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBailleur))
            )
            .andExpect(status().isOk());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
        Bailleur testBailleur = bailleurList.get(bailleurList.size() - 1);
        assertThat(testBailleur.getNomProjet()).isEqualTo(UPDATED_NOM_PROJET);
        assertThat(testBailleur.getBudgetPrevu()).isEqualTo(UPDATED_BUDGET_PREVU);
        assertThat(testBailleur.getBudgetDepense()).isEqualTo(UPDATED_BUDGET_DEPENSE);
        assertThat(testBailleur.getBudgetRestant()).isEqualTo(UPDATED_BUDGET_RESTANT);
        assertThat(testBailleur.getNbrePC()).isEqualTo(UPDATED_NBRE_PC);
    }

    @Test
    @Transactional
    void patchNonExistingBailleur() throws Exception {
        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();
        bailleur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bailleur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBailleur() throws Exception {
        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();
        bailleur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBailleur() throws Exception {
        int databaseSizeBeforeUpdate = bailleurRepository.findAll().size();
        bailleur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBailleurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bailleur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bailleur in the database
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBailleur() throws Exception {
        // Initialize the database
        bailleurRepository.saveAndFlush(bailleur);

        int databaseSizeBeforeDelete = bailleurRepository.findAll().size();

        // Delete the bailleur
        restBailleurMockMvc
            .perform(delete(ENTITY_API_URL_ID, bailleur.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bailleur> bailleurList = bailleurRepository.findAll();
        assertThat(bailleurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
