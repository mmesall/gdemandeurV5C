package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Formation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Formation}.
 */
public interface FormationService {
    /**
     * Save a formation.
     *
     * @param formation the entity to save.
     * @return the persisted entity.
     */
    Formation save(Formation formation);

    /**
     * Partially updates a formation.
     *
     * @param formation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Formation> partialUpdate(Formation formation);

    /**
     * Get all the formations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Formation> findAll(Pageable pageable);
    /**
     * Get all the Formation where Demande is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Formation> findAllWhereDemandeIsNull();

    /**
     * Get the "id" formation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Formation> findOne(Long id);

    /**
     * Delete the "id" formation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
