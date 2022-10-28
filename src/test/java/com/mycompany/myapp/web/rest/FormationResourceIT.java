package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Formation;
import com.mycompany.myapp.domain.enumeration.Admission;
import com.mycompany.myapp.domain.enumeration.DiplomeRequis;
import com.mycompany.myapp.domain.enumeration.NomDiplome;
import com.mycompany.myapp.domain.enumeration.Secteur;
import com.mycompany.myapp.domain.enumeration.TypeFormation;
import com.mycompany.myapp.repository.FormationRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FormationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormationResourceIT {

    private static final String DEFAULT_NOM_FORMATION = "AAAAAAAAAA";
    private static final String UPDATED_NOM_FORMATION = "BBBBBBBBBB";

    private static final TypeFormation DEFAULT_TYPE_FORMATION = TypeFormation.INITIALE;
    private static final TypeFormation UPDATED_TYPE_FORMATION = TypeFormation.CONTINUE;

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final Admission DEFAULT_ADMISSION = Admission.CONCOURS;
    private static final Admission UPDATED_ADMISSION = Admission.PC;

    private static final DiplomeRequis DEFAULT_DIPLOME_REQUIS = DiplomeRequis.ATTESTATION;
    private static final DiplomeRequis UPDATED_DIPLOME_REQUIS = DiplomeRequis.CAP;

    private static final String DEFAULT_AUTRE_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_DIPLOME = "BBBBBBBBBB";

    private static final Secteur DEFAULT_SECTEUR = Secteur.SANTE;
    private static final Secteur UPDATED_SECTEUR = Secteur.BATIMENT;

    private static final String DEFAULT_AUTRE_SECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUTRE_SECTEUR = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FICHE_FORMATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FICHE_FORMATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FICHE_FORMATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FICHE_FORMATION_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PROGRAMMES = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAMMES = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CONCOURS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CONCOURS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CLOTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CLOTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CONCOURS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CONCOURS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIBELLE_PC = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PC = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT_PRISE_EN_CHARGE = 1D;
    private static final Double UPDATED_MONTANT_PRISE_EN_CHARGE = 2D;

    private static final String DEFAULT_DETAIL_PC = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_PC = "BBBBBBBBBB";

    private static final NomDiplome DEFAULT_NOM_DIPLOME = NomDiplome.CPS;
    private static final NomDiplome UPDATED_NOM_DIPLOME = NomDiplome.CAP;

    private static final String DEFAULT_NOM_DEBOUCHE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DEBOUCHE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/formations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormationMockMvc;

    private Formation formation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createEntity(EntityManager em) {
        Formation formation = new Formation()
            .nomFormation(DEFAULT_NOM_FORMATION)
            .typeFormation(DEFAULT_TYPE_FORMATION)
            .duree(DEFAULT_DUREE)
            .admission(DEFAULT_ADMISSION)
            .diplomeRequis(DEFAULT_DIPLOME_REQUIS)
            .autreDiplome(DEFAULT_AUTRE_DIPLOME)
            .secteur(DEFAULT_SECTEUR)
            .autreSecteur(DEFAULT_AUTRE_SECTEUR)
            .ficheFormation(DEFAULT_FICHE_FORMATION)
            .ficheFormationContentType(DEFAULT_FICHE_FORMATION_CONTENT_TYPE)
            .programmes(DEFAULT_PROGRAMMES)
            .nomConcours(DEFAULT_NOM_CONCOURS)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateCloture(DEFAULT_DATE_CLOTURE)
            .dateConcours(DEFAULT_DATE_CONCOURS)
            .libellePC(DEFAULT_LIBELLE_PC)
            .montantPriseEnCharge(DEFAULT_MONTANT_PRISE_EN_CHARGE)
            .detailPC(DEFAULT_DETAIL_PC)
            .nomDiplome(DEFAULT_NOM_DIPLOME)
            .nomDebouche(DEFAULT_NOM_DEBOUCHE);
        return formation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formation createUpdatedEntity(EntityManager em) {
        Formation formation = new Formation()
            .nomFormation(UPDATED_NOM_FORMATION)
            .typeFormation(UPDATED_TYPE_FORMATION)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .autreDiplome(UPDATED_AUTRE_DIPLOME)
            .secteur(UPDATED_SECTEUR)
            .autreSecteur(UPDATED_AUTRE_SECTEUR)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .programmes(UPDATED_PROGRAMMES)
            .nomConcours(UPDATED_NOM_CONCOURS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .libellePC(UPDATED_LIBELLE_PC)
            .montantPriseEnCharge(UPDATED_MONTANT_PRISE_EN_CHARGE)
            .detailPC(UPDATED_DETAIL_PC)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);
        return formation;
    }

    @BeforeEach
    public void initTest() {
        formation = createEntity(em);
    }

    @Test
    @Transactional
    void createFormation() throws Exception {
        int databaseSizeBeforeCreate = formationRepository.findAll().size();
        // Create the Formation
        restFormationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isCreated());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate + 1);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getNomFormation()).isEqualTo(DEFAULT_NOM_FORMATION);
        assertThat(testFormation.getTypeFormation()).isEqualTo(DEFAULT_TYPE_FORMATION);
        assertThat(testFormation.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testFormation.getAdmission()).isEqualTo(DEFAULT_ADMISSION);
        assertThat(testFormation.getDiplomeRequis()).isEqualTo(DEFAULT_DIPLOME_REQUIS);
        assertThat(testFormation.getAutreDiplome()).isEqualTo(DEFAULT_AUTRE_DIPLOME);
        assertThat(testFormation.getSecteur()).isEqualTo(DEFAULT_SECTEUR);
        assertThat(testFormation.getAutreSecteur()).isEqualTo(DEFAULT_AUTRE_SECTEUR);
        assertThat(testFormation.getFicheFormation()).isEqualTo(DEFAULT_FICHE_FORMATION);
        assertThat(testFormation.getFicheFormationContentType()).isEqualTo(DEFAULT_FICHE_FORMATION_CONTENT_TYPE);
        assertThat(testFormation.getProgrammes()).isEqualTo(DEFAULT_PROGRAMMES);
        assertThat(testFormation.getNomConcours()).isEqualTo(DEFAULT_NOM_CONCOURS);
        assertThat(testFormation.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testFormation.getDateCloture()).isEqualTo(DEFAULT_DATE_CLOTURE);
        assertThat(testFormation.getDateConcours()).isEqualTo(DEFAULT_DATE_CONCOURS);
        assertThat(testFormation.getLibellePC()).isEqualTo(DEFAULT_LIBELLE_PC);
        assertThat(testFormation.getMontantPriseEnCharge()).isEqualTo(DEFAULT_MONTANT_PRISE_EN_CHARGE);
        assertThat(testFormation.getDetailPC()).isEqualTo(DEFAULT_DETAIL_PC);
        assertThat(testFormation.getNomDiplome()).isEqualTo(DEFAULT_NOM_DIPLOME);
        assertThat(testFormation.getNomDebouche()).isEqualTo(DEFAULT_NOM_DEBOUCHE);
    }

    @Test
    @Transactional
    void createFormationWithExistingId() throws Exception {
        // Create the Formation with an existing ID
        formation.setId(1L);

        int databaseSizeBeforeCreate = formationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormations() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get all the formationList
        restFormationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formation.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomFormation").value(hasItem(DEFAULT_NOM_FORMATION)))
            .andExpect(jsonPath("$.[*].typeFormation").value(hasItem(DEFAULT_TYPE_FORMATION.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].admission").value(hasItem(DEFAULT_ADMISSION.toString())))
            .andExpect(jsonPath("$.[*].diplomeRequis").value(hasItem(DEFAULT_DIPLOME_REQUIS.toString())))
            .andExpect(jsonPath("$.[*].autreDiplome").value(hasItem(DEFAULT_AUTRE_DIPLOME)))
            .andExpect(jsonPath("$.[*].secteur").value(hasItem(DEFAULT_SECTEUR.toString())))
            .andExpect(jsonPath("$.[*].autreSecteur").value(hasItem(DEFAULT_AUTRE_SECTEUR)))
            .andExpect(jsonPath("$.[*].ficheFormationContentType").value(hasItem(DEFAULT_FICHE_FORMATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].ficheFormation").value(hasItem(Base64Utils.encodeToString(DEFAULT_FICHE_FORMATION))))
            .andExpect(jsonPath("$.[*].programmes").value(hasItem(DEFAULT_PROGRAMMES.toString())))
            .andExpect(jsonPath("$.[*].nomConcours").value(hasItem(DEFAULT_NOM_CONCOURS)))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateCloture").value(hasItem(DEFAULT_DATE_CLOTURE.toString())))
            .andExpect(jsonPath("$.[*].dateConcours").value(hasItem(DEFAULT_DATE_CONCOURS.toString())))
            .andExpect(jsonPath("$.[*].libellePC").value(hasItem(DEFAULT_LIBELLE_PC)))
            .andExpect(jsonPath("$.[*].montantPriseEnCharge").value(hasItem(DEFAULT_MONTANT_PRISE_EN_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].detailPC").value(hasItem(DEFAULT_DETAIL_PC.toString())))
            .andExpect(jsonPath("$.[*].nomDiplome").value(hasItem(DEFAULT_NOM_DIPLOME.toString())))
            .andExpect(jsonPath("$.[*].nomDebouche").value(hasItem(DEFAULT_NOM_DEBOUCHE)));
    }

    @Test
    @Transactional
    void getFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        // Get the formation
        restFormationMockMvc
            .perform(get(ENTITY_API_URL_ID, formation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formation.getId().intValue()))
            .andExpect(jsonPath("$.nomFormation").value(DEFAULT_NOM_FORMATION))
            .andExpect(jsonPath("$.typeFormation").value(DEFAULT_TYPE_FORMATION.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.admission").value(DEFAULT_ADMISSION.toString()))
            .andExpect(jsonPath("$.diplomeRequis").value(DEFAULT_DIPLOME_REQUIS.toString()))
            .andExpect(jsonPath("$.autreDiplome").value(DEFAULT_AUTRE_DIPLOME))
            .andExpect(jsonPath("$.secteur").value(DEFAULT_SECTEUR.toString()))
            .andExpect(jsonPath("$.autreSecteur").value(DEFAULT_AUTRE_SECTEUR))
            .andExpect(jsonPath("$.ficheFormationContentType").value(DEFAULT_FICHE_FORMATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.ficheFormation").value(Base64Utils.encodeToString(DEFAULT_FICHE_FORMATION)))
            .andExpect(jsonPath("$.programmes").value(DEFAULT_PROGRAMMES.toString()))
            .andExpect(jsonPath("$.nomConcours").value(DEFAULT_NOM_CONCOURS))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateCloture").value(DEFAULT_DATE_CLOTURE.toString()))
            .andExpect(jsonPath("$.dateConcours").value(DEFAULT_DATE_CONCOURS.toString()))
            .andExpect(jsonPath("$.libellePC").value(DEFAULT_LIBELLE_PC))
            .andExpect(jsonPath("$.montantPriseEnCharge").value(DEFAULT_MONTANT_PRISE_EN_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.detailPC").value(DEFAULT_DETAIL_PC.toString()))
            .andExpect(jsonPath("$.nomDiplome").value(DEFAULT_NOM_DIPLOME.toString()))
            .andExpect(jsonPath("$.nomDebouche").value(DEFAULT_NOM_DEBOUCHE));
    }

    @Test
    @Transactional
    void getNonExistingFormation() throws Exception {
        // Get the formation
        restFormationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation
        Formation updatedFormation = formationRepository.findById(formation.getId()).get();
        // Disconnect from session so that the updates on updatedFormation are not directly saved in db
        em.detach(updatedFormation);
        updatedFormation
            .nomFormation(UPDATED_NOM_FORMATION)
            .typeFormation(UPDATED_TYPE_FORMATION)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .autreDiplome(UPDATED_AUTRE_DIPLOME)
            .secteur(UPDATED_SECTEUR)
            .autreSecteur(UPDATED_AUTRE_SECTEUR)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .programmes(UPDATED_PROGRAMMES)
            .nomConcours(UPDATED_NOM_CONCOURS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .libellePC(UPDATED_LIBELLE_PC)
            .montantPriseEnCharge(UPDATED_MONTANT_PRISE_EN_CHARGE)
            .detailPC(UPDATED_DETAIL_PC)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);

        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getNomFormation()).isEqualTo(UPDATED_NOM_FORMATION);
        assertThat(testFormation.getTypeFormation()).isEqualTo(UPDATED_TYPE_FORMATION);
        assertThat(testFormation.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testFormation.getAdmission()).isEqualTo(UPDATED_ADMISSION);
        assertThat(testFormation.getDiplomeRequis()).isEqualTo(UPDATED_DIPLOME_REQUIS);
        assertThat(testFormation.getAutreDiplome()).isEqualTo(UPDATED_AUTRE_DIPLOME);
        assertThat(testFormation.getSecteur()).isEqualTo(UPDATED_SECTEUR);
        assertThat(testFormation.getAutreSecteur()).isEqualTo(UPDATED_AUTRE_SECTEUR);
        assertThat(testFormation.getFicheFormation()).isEqualTo(UPDATED_FICHE_FORMATION);
        assertThat(testFormation.getFicheFormationContentType()).isEqualTo(UPDATED_FICHE_FORMATION_CONTENT_TYPE);
        assertThat(testFormation.getProgrammes()).isEqualTo(UPDATED_PROGRAMMES);
        assertThat(testFormation.getNomConcours()).isEqualTo(UPDATED_NOM_CONCOURS);
        assertThat(testFormation.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testFormation.getDateCloture()).isEqualTo(UPDATED_DATE_CLOTURE);
        assertThat(testFormation.getDateConcours()).isEqualTo(UPDATED_DATE_CONCOURS);
        assertThat(testFormation.getLibellePC()).isEqualTo(UPDATED_LIBELLE_PC);
        assertThat(testFormation.getMontantPriseEnCharge()).isEqualTo(UPDATED_MONTANT_PRISE_EN_CHARGE);
        assertThat(testFormation.getDetailPC()).isEqualTo(UPDATED_DETAIL_PC);
        assertThat(testFormation.getNomDiplome()).isEqualTo(UPDATED_NOM_DIPLOME);
        assertThat(testFormation.getNomDebouche()).isEqualTo(UPDATED_NOM_DEBOUCHE);
    }

    @Test
    @Transactional
    void putNonExistingFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormationWithPatch() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation using partial update
        Formation partialUpdatedFormation = new Formation();
        partialUpdatedFormation.setId(formation.getId());

        partialUpdatedFormation
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .programmes(UPDATED_PROGRAMMES)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .libellePC(UPDATED_LIBELLE_PC)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);

        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getNomFormation()).isEqualTo(DEFAULT_NOM_FORMATION);
        assertThat(testFormation.getTypeFormation()).isEqualTo(DEFAULT_TYPE_FORMATION);
        assertThat(testFormation.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testFormation.getAdmission()).isEqualTo(DEFAULT_ADMISSION);
        assertThat(testFormation.getDiplomeRequis()).isEqualTo(UPDATED_DIPLOME_REQUIS);
        assertThat(testFormation.getAutreDiplome()).isEqualTo(DEFAULT_AUTRE_DIPLOME);
        assertThat(testFormation.getSecteur()).isEqualTo(DEFAULT_SECTEUR);
        assertThat(testFormation.getAutreSecteur()).isEqualTo(DEFAULT_AUTRE_SECTEUR);
        assertThat(testFormation.getFicheFormation()).isEqualTo(UPDATED_FICHE_FORMATION);
        assertThat(testFormation.getFicheFormationContentType()).isEqualTo(UPDATED_FICHE_FORMATION_CONTENT_TYPE);
        assertThat(testFormation.getProgrammes()).isEqualTo(UPDATED_PROGRAMMES);
        assertThat(testFormation.getNomConcours()).isEqualTo(DEFAULT_NOM_CONCOURS);
        assertThat(testFormation.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testFormation.getDateCloture()).isEqualTo(UPDATED_DATE_CLOTURE);
        assertThat(testFormation.getDateConcours()).isEqualTo(DEFAULT_DATE_CONCOURS);
        assertThat(testFormation.getLibellePC()).isEqualTo(UPDATED_LIBELLE_PC);
        assertThat(testFormation.getMontantPriseEnCharge()).isEqualTo(DEFAULT_MONTANT_PRISE_EN_CHARGE);
        assertThat(testFormation.getDetailPC()).isEqualTo(DEFAULT_DETAIL_PC);
        assertThat(testFormation.getNomDiplome()).isEqualTo(UPDATED_NOM_DIPLOME);
        assertThat(testFormation.getNomDebouche()).isEqualTo(UPDATED_NOM_DEBOUCHE);
    }

    @Test
    @Transactional
    void fullUpdateFormationWithPatch() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeUpdate = formationRepository.findAll().size();

        // Update the formation using partial update
        Formation partialUpdatedFormation = new Formation();
        partialUpdatedFormation.setId(formation.getId());

        partialUpdatedFormation
            .nomFormation(UPDATED_NOM_FORMATION)
            .typeFormation(UPDATED_TYPE_FORMATION)
            .duree(UPDATED_DUREE)
            .admission(UPDATED_ADMISSION)
            .diplomeRequis(UPDATED_DIPLOME_REQUIS)
            .autreDiplome(UPDATED_AUTRE_DIPLOME)
            .secteur(UPDATED_SECTEUR)
            .autreSecteur(UPDATED_AUTRE_SECTEUR)
            .ficheFormation(UPDATED_FICHE_FORMATION)
            .ficheFormationContentType(UPDATED_FICHE_FORMATION_CONTENT_TYPE)
            .programmes(UPDATED_PROGRAMMES)
            .nomConcours(UPDATED_NOM_CONCOURS)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateCloture(UPDATED_DATE_CLOTURE)
            .dateConcours(UPDATED_DATE_CONCOURS)
            .libellePC(UPDATED_LIBELLE_PC)
            .montantPriseEnCharge(UPDATED_MONTANT_PRISE_EN_CHARGE)
            .detailPC(UPDATED_DETAIL_PC)
            .nomDiplome(UPDATED_NOM_DIPLOME)
            .nomDebouche(UPDATED_NOM_DEBOUCHE);

        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormation))
            )
            .andExpect(status().isOk());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
        Formation testFormation = formationList.get(formationList.size() - 1);
        assertThat(testFormation.getNomFormation()).isEqualTo(UPDATED_NOM_FORMATION);
        assertThat(testFormation.getTypeFormation()).isEqualTo(UPDATED_TYPE_FORMATION);
        assertThat(testFormation.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testFormation.getAdmission()).isEqualTo(UPDATED_ADMISSION);
        assertThat(testFormation.getDiplomeRequis()).isEqualTo(UPDATED_DIPLOME_REQUIS);
        assertThat(testFormation.getAutreDiplome()).isEqualTo(UPDATED_AUTRE_DIPLOME);
        assertThat(testFormation.getSecteur()).isEqualTo(UPDATED_SECTEUR);
        assertThat(testFormation.getAutreSecteur()).isEqualTo(UPDATED_AUTRE_SECTEUR);
        assertThat(testFormation.getFicheFormation()).isEqualTo(UPDATED_FICHE_FORMATION);
        assertThat(testFormation.getFicheFormationContentType()).isEqualTo(UPDATED_FICHE_FORMATION_CONTENT_TYPE);
        assertThat(testFormation.getProgrammes()).isEqualTo(UPDATED_PROGRAMMES);
        assertThat(testFormation.getNomConcours()).isEqualTo(UPDATED_NOM_CONCOURS);
        assertThat(testFormation.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testFormation.getDateCloture()).isEqualTo(UPDATED_DATE_CLOTURE);
        assertThat(testFormation.getDateConcours()).isEqualTo(UPDATED_DATE_CONCOURS);
        assertThat(testFormation.getLibellePC()).isEqualTo(UPDATED_LIBELLE_PC);
        assertThat(testFormation.getMontantPriseEnCharge()).isEqualTo(UPDATED_MONTANT_PRISE_EN_CHARGE);
        assertThat(testFormation.getDetailPC()).isEqualTo(UPDATED_DETAIL_PC);
        assertThat(testFormation.getNomDiplome()).isEqualTo(UPDATED_NOM_DIPLOME);
        assertThat(testFormation.getNomDebouche()).isEqualTo(UPDATED_NOM_DEBOUCHE);
    }

    @Test
    @Transactional
    void patchNonExistingFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormation() throws Exception {
        int databaseSizeBeforeUpdate = formationRepository.findAll().size();
        formation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Formation in the database
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormation() throws Exception {
        // Initialize the database
        formationRepository.saveAndFlush(formation);

        int databaseSizeBeforeDelete = formationRepository.findAll().size();

        // Delete the formation
        restFormationMockMvc
            .perform(delete(ENTITY_API_URL_ID, formation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Formation> formationList = formationRepository.findAll();
        assertThat(formationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
