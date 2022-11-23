package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Commission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Commission}.
 */
public interface CommissionService {
    /**
     * Save a commission.
     *
     * @param commission the entity to save.
     * @return the persisted entity.
     */
    Commission save(Commission commission);

    /**
     * Partially updates a commission.
     *
     * @param commission the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Commission> partialUpdate(Commission commission);

    /**
     * Get all the commissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Commission> findAll(Pageable pageable);
    /**
     * Get all the Commission where Services is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Commission> findAllWhereServicesIsNull();

    /**
     * Get the "id" commission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Commission> findOne(Long id);

    /**
     * Delete the "id" commission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
