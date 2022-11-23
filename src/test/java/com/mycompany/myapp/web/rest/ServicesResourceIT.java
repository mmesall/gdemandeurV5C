package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Services;
import com.mycompany.myapp.repository.ServicesRepository;
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
 * Integration tests for the {@link ServicesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServicesResourceIT {

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOM_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_CHEF_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_CHEF_SERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PILOTAGE = false;
    private static final Boolean UPDATED_IS_PILOTAGE = true;

    private static final String ENTITY_API_URL = "/api/services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicesMockMvc;

    private Services services;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Services createEntity(EntityManager em) {
        Services services = new Services()
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .nomService(DEFAULT_NOM_SERVICE)
            .chefService(DEFAULT_CHEF_SERVICE)
            .description(DEFAULT_DESCRIPTION)
            .isPilotage(DEFAULT_IS_PILOTAGE);
        return services;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Services createUpdatedEntity(EntityManager em) {
        Services services = new Services()
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .nomService(UPDATED_NOM_SERVICE)
            .chefService(UPDATED_CHEF_SERVICE)
            .description(UPDATED_DESCRIPTION)
            .isPilotage(UPDATED_IS_PILOTAGE);
        return services;
    }

    @BeforeEach
    public void initTest() {
        services = createEntity(em);
    }

    @Test
    @Transactional
    void createServices() throws Exception {
        int databaseSizeBeforeCreate = servicesRepository.findAll().size();
        // Create the Services
        restServicesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isCreated());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeCreate + 1);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testServices.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testServices.getNomService()).isEqualTo(DEFAULT_NOM_SERVICE);
        assertThat(testServices.getChefService()).isEqualTo(DEFAULT_CHEF_SERVICE);
        assertThat(testServices.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServices.getIsPilotage()).isEqualTo(DEFAULT_IS_PILOTAGE);
    }

    @Test
    @Transactional
    void createServicesWithExistingId() throws Exception {
        // Create the Services with an existing ID
        services.setId(1L);

        int databaseSizeBeforeCreate = servicesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get all the servicesList
        restServicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(services.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].nomService").value(hasItem(DEFAULT_NOM_SERVICE)))
            .andExpect(jsonPath("$.[*].chefService").value(hasItem(DEFAULT_CHEF_SERVICE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isPilotage").value(hasItem(DEFAULT_IS_PILOTAGE.booleanValue())));
    }

    @Test
    @Transactional
    void getServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        // Get the services
        restServicesMockMvc
            .perform(get(ENTITY_API_URL_ID, services.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(services.getId().intValue()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.nomService").value(DEFAULT_NOM_SERVICE))
            .andExpect(jsonPath("$.chefService").value(DEFAULT_CHEF_SERVICE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isPilotage").value(DEFAULT_IS_PILOTAGE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingServices() throws Exception {
        // Get the services
        restServicesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services
        Services updatedServices = servicesRepository.findById(services.getId()).get();
        // Disconnect from session so that the updates on updatedServices are not directly saved in db
        em.detach(updatedServices);
        updatedServices
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .nomService(UPDATED_NOM_SERVICE)
            .chefService(UPDATED_CHEF_SERVICE)
            .description(UPDATED_DESCRIPTION)
            .isPilotage(UPDATED_IS_PILOTAGE);

        restServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServices.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedServices))
            )
            .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testServices.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testServices.getNomService()).isEqualTo(UPDATED_NOM_SERVICE);
        assertThat(testServices.getChefService()).isEqualTo(UPDATED_CHEF_SERVICE);
        assertThat(testServices.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServices.getIsPilotage()).isEqualTo(UPDATED_IS_PILOTAGE);
    }

    @Test
    @Transactional
    void putNonExistingServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, services.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicesWithPatch() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services using partial update
        Services partialUpdatedServices = new Services();
        partialUpdatedServices.setId(services.getId());

        partialUpdatedServices.description(UPDATED_DESCRIPTION).isPilotage(UPDATED_IS_PILOTAGE);

        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServices.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServices))
            )
            .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testServices.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testServices.getNomService()).isEqualTo(DEFAULT_NOM_SERVICE);
        assertThat(testServices.getChefService()).isEqualTo(DEFAULT_CHEF_SERVICE);
        assertThat(testServices.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServices.getIsPilotage()).isEqualTo(UPDATED_IS_PILOTAGE);
    }

    @Test
    @Transactional
    void fullUpdateServicesWithPatch() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();

        // Update the services using partial update
        Services partialUpdatedServices = new Services();
        partialUpdatedServices.setId(services.getId());

        partialUpdatedServices
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .nomService(UPDATED_NOM_SERVICE)
            .chefService(UPDATED_CHEF_SERVICE)
            .description(UPDATED_DESCRIPTION)
            .isPilotage(UPDATED_IS_PILOTAGE);

        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServices.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServices))
            )
            .andExpect(status().isOk());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
        Services testServices = servicesList.get(servicesList.size() - 1);
        assertThat(testServices.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testServices.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testServices.getNomService()).isEqualTo(UPDATED_NOM_SERVICE);
        assertThat(testServices.getChefService()).isEqualTo(UPDATED_CHEF_SERVICE);
        assertThat(testServices.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServices.getIsPilotage()).isEqualTo(UPDATED_IS_PILOTAGE);
    }

    @Test
    @Transactional
    void patchNonExistingServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, services.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isBadRequest());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServices() throws Exception {
        int databaseSizeBeforeUpdate = servicesRepository.findAll().size();
        services.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(services))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Services in the database
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServices() throws Exception {
        // Initialize the database
        servicesRepository.saveAndFlush(services);

        int databaseSizeBeforeDelete = servicesRepository.findAll().size();

        // Delete the services
        restServicesMockMvc
            .perform(delete(ENTITY_API_URL_ID, services.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Services> servicesList = servicesRepository.findAll();
        assertThat(servicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
