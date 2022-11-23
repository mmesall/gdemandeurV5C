package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Bailleur;
import com.mycompany.myapp.repository.BailleurRepository;
import com.mycompany.myapp.service.BailleurService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bailleur}.
 */
@Service
@Transactional
public class BailleurServiceImpl implements BailleurService {

    private final Logger log = LoggerFactory.getLogger(BailleurServiceImpl.class);

    private final BailleurRepository bailleurRepository;

    public BailleurServiceImpl(BailleurRepository bailleurRepository) {
        this.bailleurRepository = bailleurRepository;
    }

    @Override
    public Bailleur save(Bailleur bailleur) {
        log.debug("Request to save Bailleur : {}", bailleur);
        return bailleurRepository.save(bailleur);
    }

    @Override
    public Optional<Bailleur> partialUpdate(Bailleur bailleur) {
        log.debug("Request to partially update Bailleur : {}", bailleur);

        return bailleurRepository
            .findById(bailleur.getId())
            .map(existingBailleur -> {
                if (bailleur.getNomProjet() != null) {
                    existingBailleur.setNomProjet(bailleur.getNomProjet());
                }
                if (bailleur.getBudgetPrevu() != null) {
                    existingBailleur.setBudgetPrevu(bailleur.getBudgetPrevu());
                }
                if (bailleur.getBudgetDepense() != null) {
                    existingBailleur.setBudgetDepense(bailleur.getBudgetDepense());
                }
                if (bailleur.getBudgetRestant() != null) {
                    existingBailleur.setBudgetRestant(bailleur.getBudgetRestant());
                }
                if (bailleur.getNbrePC() != null) {
                    existingBailleur.setNbrePC(bailleur.getNbrePC());
                }

                return existingBailleur;
            })
            .map(bailleurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bailleur> findAll(Pageable pageable) {
        log.debug("Request to get all Bailleurs");
        return bailleurRepository.findAll(pageable);
    }

    public Page<Bailleur> findAllWithEagerRelationships(Pageable pageable) {
        return bailleurRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bailleur> findOne(Long id) {
        log.debug("Request to get Bailleur : {}", id);
        return bailleurRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bailleur : {}", id);
        bailleurRepository.deleteById(id);
    }
}
