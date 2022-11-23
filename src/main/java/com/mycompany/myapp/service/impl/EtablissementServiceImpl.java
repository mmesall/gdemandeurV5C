package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Etablissement;
import com.mycompany.myapp.repository.EtablissementRepository;
import com.mycompany.myapp.service.EtablissementService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Etablissement}.
 */
@Service
@Transactional
public class EtablissementServiceImpl implements EtablissementService {

    private final Logger log = LoggerFactory.getLogger(EtablissementServiceImpl.class);

    private final EtablissementRepository etablissementRepository;

    public EtablissementServiceImpl(EtablissementRepository etablissementRepository) {
        this.etablissementRepository = etablissementRepository;
    }

    @Override
    public Etablissement save(Etablissement etablissement) {
        log.debug("Request to save Etablissement : {}", etablissement);
        return etablissementRepository.save(etablissement);
    }

    @Override
    public Optional<Etablissement> partialUpdate(Etablissement etablissement) {
        log.debug("Request to partially update Etablissement : {}", etablissement);

        return etablissementRepository
            .findById(etablissement.getId())
            .map(existingEtablissement -> {
                if (etablissement.getNomEtablissement() != null) {
                    existingEtablissement.setNomEtablissement(etablissement.getNomEtablissement());
                }
                if (etablissement.getPhoto() != null) {
                    existingEtablissement.setPhoto(etablissement.getPhoto());
                }
                if (etablissement.getPhotoContentType() != null) {
                    existingEtablissement.setPhotoContentType(etablissement.getPhotoContentType());
                }
                if (etablissement.getAdresse() != null) {
                    existingEtablissement.setAdresse(etablissement.getAdresse());
                }
                if (etablissement.getEmail() != null) {
                    existingEtablissement.setEmail(etablissement.getEmail());
                }
                if (etablissement.getTelephone() != null) {
                    existingEtablissement.setTelephone(etablissement.getTelephone());
                }
                if (etablissement.getTypeEtablissement() != null) {
                    existingEtablissement.setTypeEtablissement(etablissement.getTypeEtablissement());
                }

                return existingEtablissement;
            })
            .map(etablissementRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etablissement> findAll(Pageable pageable) {
        log.debug("Request to get all Etablissements");
        return etablissementRepository.findAll(pageable);
    }

    public Page<Etablissement> findAllWithEagerRelationships(Pageable pageable) {
        return etablissementRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Etablissement> findOne(Long id) {
        log.debug("Request to get Etablissement : {}", id);
        return etablissementRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etablissement : {}", id);
        etablissementRepository.deleteById(id);
    }
}
