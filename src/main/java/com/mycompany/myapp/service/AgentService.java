package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Agent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Agent}.
 */
public interface AgentService {
    /**
     * Save a agent.
     *
     * @param agent the entity to save.
     * @return the persisted entity.
     */
    Agent save(Agent agent);

    /**
     * Partially updates a agent.
     *
     * @param agent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Agent> partialUpdate(Agent agent);

    /**
     * Get all the agents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Agent> findAll(Pageable pageable);
    /**
     * Get all the Agent where Bailleur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Agent> findAllWhereBailleurIsNull();
    /**
     * Get all the Agent where Membre is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<Agent> findAllWhereMembreIsNull();

    /**
     * Get all the agents with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Agent> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" agent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Agent> findOne(Long id);

    /**
     * Delete the "id" agent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
