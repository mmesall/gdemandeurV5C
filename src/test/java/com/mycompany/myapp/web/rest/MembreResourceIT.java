package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Membre;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.repository.MembreRepository;
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
 * Integration tests for the {@link MembreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MembreResourceIT {

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

    private static final String DEFAULT_ADRESSE_PHYSIQUE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_PHYSIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_CNI = 1L;
    private static final Long UPDATED_CNI = 2L;

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/membres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembreMockMvc;

    private Membre membre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membre createEntity(EntityManager em) {
        Membre membre = new Membre()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .lieuNaiss(DEFAULT_LIEU_NAISS)
            .sexe(DEFAULT_SEXE)
            .telephone(DEFAULT_TELEPHONE)
            .adressePhysique(DEFAULT_ADRESSE_PHYSIQUE)
            .email(DEFAULT_EMAIL)
            .cni(DEFAULT_CNI)
            .matricule(DEFAULT_MATRICULE);
        return membre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Membre createUpdatedEntity(EntityManager em) {
        Membre membre = new Membre()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .adressePhysique(UPDATED_ADRESSE_PHYSIQUE)
            .email(UPDATED_EMAIL)
            .cni(UPDATED_CNI)
            .matricule(UPDATED_MATRICULE);
        return membre;
    }

    @BeforeEach
    public void initTest() {
        membre = createEntity(em);
    }

    @Test
    @Transactional
    void createMembre() throws Exception {
        int databaseSizeBeforeCreate = membreRepository.findAll().size();
        // Create the Membre
        restMembreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isCreated());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeCreate + 1);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembre.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMembre.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testMembre.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testMembre.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testMembre.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testMembre.getAdressePhysique()).isEqualTo(DEFAULT_ADRESSE_PHYSIQUE);
        assertThat(testMembre.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMembre.getCni()).isEqualTo(DEFAULT_CNI);
        assertThat(testMembre.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    void createMembreWithExistingId() throws Exception {
        // Create the Membre with an existing ID
        membre.setId(1L);

        int databaseSizeBeforeCreate = membreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMembres() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        // Get all the membreList
        restMembreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].lieuNaiss").value(hasItem(DEFAULT_LIEU_NAISS)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())))
            .andExpect(jsonPath("$.[*].adressePhysique").value(hasItem(DEFAULT_ADRESSE_PHYSIQUE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].cni").value(hasItem(DEFAULT_CNI.intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)));
    }

    @Test
    @Transactional
    void getMembre() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        // Get the membre
        restMembreMockMvc
            .perform(get(ENTITY_API_URL_ID, membre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membre.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.lieuNaiss").value(DEFAULT_LIEU_NAISS))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()))
            .andExpect(jsonPath("$.adressePhysique").value(DEFAULT_ADRESSE_PHYSIQUE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.cni").value(DEFAULT_CNI.intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE));
    }

    @Test
    @Transactional
    void getNonExistingMembre() throws Exception {
        // Get the membre
        restMembreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMembre() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        int databaseSizeBeforeUpdate = membreRepository.findAll().size();

        // Update the membre
        Membre updatedMembre = membreRepository.findById(membre.getId()).get();
        // Disconnect from session so that the updates on updatedMembre are not directly saved in db
        em.detach(updatedMembre);
        updatedMembre
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .adressePhysique(UPDATED_ADRESSE_PHYSIQUE)
            .email(UPDATED_EMAIL)
            .cni(UPDATED_CNI)
            .matricule(UPDATED_MATRICULE);

        restMembreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMembre.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMembre))
            )
            .andExpect(status().isOk());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembre.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembre.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testMembre.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testMembre.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testMembre.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMembre.getAdressePhysique()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE);
        assertThat(testMembre.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMembre.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testMembre.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    void putNonExistingMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();
        membre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, membre.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();
        membre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();
        membre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMembreWithPatch() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        int databaseSizeBeforeUpdate = membreRepository.findAll().size();

        // Update the membre using partial update
        Membre partialUpdatedMembre = new Membre();
        partialUpdatedMembre.setId(membre.getId());

        partialUpdatedMembre.nom(UPDATED_NOM).email(UPDATED_EMAIL).cni(UPDATED_CNI);

        restMembreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembre))
            )
            .andExpect(status().isOk());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembre.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMembre.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testMembre.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testMembre.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testMembre.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testMembre.getAdressePhysique()).isEqualTo(DEFAULT_ADRESSE_PHYSIQUE);
        assertThat(testMembre.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMembre.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testMembre.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    void fullUpdateMembreWithPatch() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        int databaseSizeBeforeUpdate = membreRepository.findAll().size();

        // Update the membre using partial update
        Membre partialUpdatedMembre = new Membre();
        partialUpdatedMembre.setId(membre.getId());

        partialUpdatedMembre
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateNaiss(UPDATED_DATE_NAISS)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .sexe(UPDATED_SEXE)
            .telephone(UPDATED_TELEPHONE)
            .adressePhysique(UPDATED_ADRESSE_PHYSIQUE)
            .email(UPDATED_EMAIL)
            .cni(UPDATED_CNI)
            .matricule(UPDATED_MATRICULE);

        restMembreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMembre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMembre))
            )
            .andExpect(status().isOk());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
        Membre testMembre = membreList.get(membreList.size() - 1);
        assertThat(testMembre.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembre.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMembre.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testMembre.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testMembre.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testMembre.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testMembre.getAdressePhysique()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE);
        assertThat(testMembre.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMembre.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testMembre.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    void patchNonExistingMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();
        membre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, membre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();
        membre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMembre() throws Exception {
        int databaseSizeBeforeUpdate = membreRepository.findAll().size();
        membre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMembreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(membre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Membre in the database
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMembre() throws Exception {
        // Initialize the database
        membreRepository.saveAndFlush(membre);

        int databaseSizeBeforeDelete = membreRepository.findAll().size();

        // Delete the membre
        restMembreMockMvc
            .perform(delete(ENTITY_API_URL_ID, membre.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Membre> membreList = membreRepository.findAll();
        assertThat(membreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
