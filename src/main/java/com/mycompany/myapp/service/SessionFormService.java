package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SessionForm;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SessionForm}.
 */
public interface SessionFormService {
    /**
     * Save a sessionForm.
     *
     * @param sessionForm the entity to save.
     * @return the persisted entity.
     */
    SessionForm save(SessionForm sessionForm);

    /**
     * Partially updates a sessionForm.
     *
     * @param sessionForm the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SessionForm> partialUpdate(SessionForm sessionForm);

    /**
     * Get all the sessionForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SessionForm> findAll(Pageable pageable);

    /**
     * Get all the sessionForms with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SessionForm> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" sessionForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SessionForm> findOne(Long id);

    /**
     * Delete the "id" sessionForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
