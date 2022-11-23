package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Services;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Services}.
 */
public interface ServicesService {
    /**
     * Save a services.
     *
     * @param services the entity to save.
     * @return the persisted entity.
     */
    Services save(Services services);

    /**
     * Partially updates a services.
     *
     * @param services the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Services> partialUpdate(Services services);

    /**
     * Get all the services.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Services> findAll(Pageable pageable);

    /**
     * Get all the services with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Services> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" services.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Services> findOne(Long id);

    /**
     * Delete the "id" services.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
