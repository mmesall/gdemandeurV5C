package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Membre;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Membre}.
 */
public interface MembreService {
    /**
     * Save a membre.
     *
     * @param membre the entity to save.
     * @return the persisted entity.
     */
    Membre save(Membre membre);

    /**
     * Partially updates a membre.
     *
     * @param membre the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Membre> partialUpdate(Membre membre);

    /**
     * Get all the membres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Membre> findAll(Pageable pageable);

    /**
     * Get all the membres with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Membre> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" membre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Membre> findOne(Long id);

    /**
     * Delete the "id" membre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
