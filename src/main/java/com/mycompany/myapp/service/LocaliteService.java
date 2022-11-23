package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Localite;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Localite}.
 */
public interface LocaliteService {
    /**
     * Save a localite.
     *
     * @param localite the entity to save.
     * @return the persisted entity.
     */
    Localite save(Localite localite);

    /**
     * Partially updates a localite.
     *
     * @param localite the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Localite> partialUpdate(Localite localite);

    /**
     * Get all the localites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Localite> findAll(Pageable pageable);

    /**
     * Get the "id" localite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Localite> findOne(Long id);

    /**
     * Delete the "id" localite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
