package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Membre;
import com.mycompany.myapp.repository.MembreRepository;
import com.mycompany.myapp.service.MembreService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Membre}.
 */
@Service
@Transactional
public class MembreServiceImpl implements MembreService {

    private final Logger log = LoggerFactory.getLogger(MembreServiceImpl.class);

    private final MembreRepository membreRepository;

    public MembreServiceImpl(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    @Override
    public Membre save(Membre membre) {
        log.debug("Request to save Membre : {}", membre);
        return membreRepository.save(membre);
    }

    @Override
    public Optional<Membre> partialUpdate(Membre membre) {
        log.debug("Request to partially update Membre : {}", membre);

        return membreRepository
            .findById(membre.getId())
            .map(existingMembre -> {
                if (membre.getNom() != null) {
                    existingMembre.setNom(membre.getNom());
                }
                if (membre.getPrenom() != null) {
                    existingMembre.setPrenom(membre.getPrenom());
                }
                if (membre.getDateNaiss() != null) {
                    existingMembre.setDateNaiss(membre.getDateNaiss());
                }
                if (membre.getLieuNaiss() != null) {
                    existingMembre.setLieuNaiss(membre.getLieuNaiss());
                }
                if (membre.getSexe() != null) {
                    existingMembre.setSexe(membre.getSexe());
                }
                if (membre.getTelephone() != null) {
                    existingMembre.setTelephone(membre.getTelephone());
                }
                if (membre.getAdressePhysique() != null) {
                    existingMembre.setAdressePhysique(membre.getAdressePhysique());
                }
                if (membre.getEmail() != null) {
                    existingMembre.setEmail(membre.getEmail());
                }
                if (membre.getCni() != null) {
                    existingMembre.setCni(membre.getCni());
                }
                if (membre.getMatricule() != null) {
                    existingMembre.setMatricule(membre.getMatricule());
                }

                return existingMembre;
            })
            .map(membreRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Membre> findAll(Pageable pageable) {
        log.debug("Request to get all Membres");
        return membreRepository.findAll(pageable);
    }

    public Page<Membre> findAllWithEagerRelationships(Pageable pageable) {
        return membreRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Membre> findOne(Long id) {
        log.debug("Request to get Membre : {}", id);
        return membreRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Membre : {}", id);
        membreRepository.deleteById(id);
    }
}
