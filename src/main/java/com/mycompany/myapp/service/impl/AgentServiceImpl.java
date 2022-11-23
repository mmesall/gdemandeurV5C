package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Agent;
import com.mycompany.myapp.repository.AgentRepository;
import com.mycompany.myapp.service.AgentService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Agent}.
 */
@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    private final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent save(Agent agent) {
        log.debug("Request to save Agent : {}", agent);
        return agentRepository.save(agent);
    }

    @Override
    public Optional<Agent> partialUpdate(Agent agent) {
        log.debug("Request to partially update Agent : {}", agent);

        return agentRepository
            .findById(agent.getId())
            .map(existingAgent -> {
                if (agent.getNom() != null) {
                    existingAgent.setNom(agent.getNom());
                }
                if (agent.getPrenom() != null) {
                    existingAgent.setPrenom(agent.getPrenom());
                }
                if (agent.getDateNaiss() != null) {
                    existingAgent.setDateNaiss(agent.getDateNaiss());
                }
                if (agent.getLieuNaiss() != null) {
                    existingAgent.setLieuNaiss(agent.getLieuNaiss());
                }
                if (agent.getSexe() != null) {
                    existingAgent.setSexe(agent.getSexe());
                }
                if (agent.getTelephone() != null) {
                    existingAgent.setTelephone(agent.getTelephone());
                }
                if (agent.getAdressePhysique() != null) {
                    existingAgent.setAdressePhysique(agent.getAdressePhysique());
                }
                if (agent.getEmail() != null) {
                    existingAgent.setEmail(agent.getEmail());
                }
                if (agent.getCni() != null) {
                    existingAgent.setCni(agent.getCni());
                }
                if (agent.getMatricule() != null) {
                    existingAgent.setMatricule(agent.getMatricule());
                }

                return existingAgent;
            })
            .map(agentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Agent> findAll(Pageable pageable) {
        log.debug("Request to get all Agents");
        return agentRepository.findAll(pageable);
    }

    public Page<Agent> findAllWithEagerRelationships(Pageable pageable) {
        return agentRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the agents where Bailleur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Agent> findAllWhereBailleurIsNull() {
        log.debug("Request to get all agents where Bailleur is null");
        return StreamSupport
            .stream(agentRepository.findAll().spliterator(), false)
            .filter(agent -> agent.getBailleur() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the agents where Membre is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Agent> findAllWhereMembreIsNull() {
        log.debug("Request to get all agents where Membre is null");
        return StreamSupport
            .stream(agentRepository.findAll().spliterator(), false)
            .filter(agent -> agent.getMembre() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agent> findOne(Long id) {
        log.debug("Request to get Agent : {}", id);
        return agentRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agent : {}", id);
        agentRepository.deleteById(id);
    }
}
