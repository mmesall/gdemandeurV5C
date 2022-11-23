package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Agent;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.repository.AgentRepository;
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
 * Integration tests for the {@link AgentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgentResourceIT {

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

    private static final String ENTITY_API_URL = "/api/agents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgentMockMvc;

    private Agent agent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createEntity(EntityManager em) {
        Agent agent = new Agent()
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
        return agent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agent createUpdatedEntity(EntityManager em) {
        Agent agent = new Agent()
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
        return agent;
    }

    @BeforeEach
    public void initTest() {
        agent = createEntity(em);
    }

    @Test
    @Transactional
    void createAgent() throws Exception {
        int databaseSizeBeforeCreate = agentRepository.findAll().size();
        // Create the Agent
        restAgentMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isCreated());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate + 1);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAgent.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAgent.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testAgent.getLieuNaiss()).isEqualTo(DEFAULT_LIEU_NAISS);
        assertThat(testAgent.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testAgent.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAgent.getAdressePhysique()).isEqualTo(DEFAULT_ADRESSE_PHYSIQUE);
        assertThat(testAgent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAgent.getCni()).isEqualTo(DEFAULT_CNI);
        assertThat(testAgent.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    void createAgentWithExistingId() throws Exception {
        // Create the Agent with an existing ID
        agent.setId(1L);

        int databaseSizeBeforeCreate = agentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgents() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get all the agentList
        restAgentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agent.getId().intValue())))
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
    void getAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        // Get the agent
        restAgentMockMvc
            .perform(get(ENTITY_API_URL_ID, agent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agent.getId().intValue()))
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
    void getNonExistingAgent() throws Exception {
        // Get the agent
        restAgentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent
        Agent updatedAgent = agentRepository.findById(agent.getId()).get();
        // Disconnect from session so that the updates on updatedAgent are not directly saved in db
        em.detach(updatedAgent);
        updatedAgent
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

        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgent.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAgent.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAgent.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testAgent.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testAgent.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testAgent.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAgent.getAdressePhysique()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE);
        assertThat(testAgent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAgent.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testAgent.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    void putNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agent.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgentWithPatch() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent using partial update
        Agent partialUpdatedAgent = new Agent();
        partialUpdatedAgent.setId(agent.getId());

        partialUpdatedAgent
            .nom(UPDATED_NOM)
            .lieuNaiss(UPDATED_LIEU_NAISS)
            .telephone(UPDATED_TELEPHONE)
            .adressePhysique(UPDATED_ADRESSE_PHYSIQUE)
            .email(UPDATED_EMAIL)
            .cni(UPDATED_CNI)
            .matricule(UPDATED_MATRICULE);

        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAgent.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAgent.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testAgent.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testAgent.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testAgent.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAgent.getAdressePhysique()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE);
        assertThat(testAgent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAgent.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testAgent.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    void fullUpdateAgentWithPatch() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeUpdate = agentRepository.findAll().size();

        // Update the agent using partial update
        Agent partialUpdatedAgent = new Agent();
        partialUpdatedAgent.setId(agent.getId());

        partialUpdatedAgent
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

        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgent))
            )
            .andExpect(status().isOk());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
        Agent testAgent = agentList.get(agentList.size() - 1);
        assertThat(testAgent.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAgent.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAgent.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testAgent.getLieuNaiss()).isEqualTo(UPDATED_LIEU_NAISS);
        assertThat(testAgent.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testAgent.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAgent.getAdressePhysique()).isEqualTo(UPDATED_ADRESSE_PHYSIQUE);
        assertThat(testAgent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAgent.getCni()).isEqualTo(UPDATED_CNI);
        assertThat(testAgent.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    void patchNonExistingAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgent() throws Exception {
        int databaseSizeBeforeUpdate = agentRepository.findAll().size();
        agent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agent in the database
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgent() throws Exception {
        // Initialize the database
        agentRepository.saveAndFlush(agent);

        int databaseSizeBeforeDelete = agentRepository.findAll().size();

        // Delete the agent
        restAgentMockMvc
            .perform(delete(ENTITY_API_URL_ID, agent.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agent> agentList = agentRepository.findAll();
        assertThat(agentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
