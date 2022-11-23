package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Formation;
import com.mycompany.myapp.repository.FormationRepository;
import com.mycompany.myapp.service.FormationService;
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
 * Service Implementation for managing {@link Formation}.
 */
@Service
@Transactional
public class FormationServiceImpl implements FormationService {

    private final Logger log = LoggerFactory.getLogger(FormationServiceImpl.class);

    private final FormationRepository formationRepository;

    public FormationServiceImpl(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    @Override
    public Formation save(Formation formation) {
        log.debug("Request to save Formation : {}", formation);
        return formationRepository.save(formation);
    }

    @Override
    public Optional<Formation> partialUpdate(Formation formation) {
        log.debug("Request to partially update Formation : {}", formation);

        return formationRepository
            .findById(formation.getId())
            .map(existingFormation -> {
                if (formation.getNomFormation() != null) {
                    existingFormation.setNomFormation(formation.getNomFormation());
                }
                if (formation.getTypeFormation() != null) {
                    existingFormation.setTypeFormation(formation.getTypeFormation());
                }
                if (formation.getDuree() != null) {
                    existingFormation.setDuree(formation.getDuree());
                }
                if (formation.getAdmission() != null) {
                    existingFormation.setAdmission(formation.getAdmission());
                }
                if (formation.getDiplomeRequis() != null) {
                    existingFormation.setDiplomeRequis(formation.getDiplomeRequis());
                }
                if (formation.getAutreDiplome() != null) {
                    existingFormation.setAutreDiplome(formation.getAutreDiplome());
                }
                if (formation.getSecteur() != null) {
                    existingFormation.setSecteur(formation.getSecteur());
                }
                if (formation.getAutreSecteur() != null) {
                    existingFormation.setAutreSecteur(formation.getAutreSecteur());
                }
                if (formation.getFicheFormation() != null) {
                    existingFormation.setFicheFormation(formation.getFicheFormation());
                }
                if (formation.getFicheFormationContentType() != null) {
                    existingFormation.setFicheFormationContentType(formation.getFicheFormationContentType());
                }
                if (formation.getProgrammes() != null) {
                    existingFormation.setProgrammes(formation.getProgrammes());
                }
                if (formation.getNomConcours() != null) {
                    existingFormation.setNomConcours(formation.getNomConcours());
                }
                if (formation.getDateOuverture() != null) {
                    existingFormation.setDateOuverture(formation.getDateOuverture());
                }
                if (formation.getDateCloture() != null) {
                    existingFormation.setDateCloture(formation.getDateCloture());
                }
                if (formation.getDateConcours() != null) {
                    existingFormation.setDateConcours(formation.getDateConcours());
                }
                if (formation.getLibellePC() != null) {
                    existingFormation.setLibellePC(formation.getLibellePC());
                }
                if (formation.getMontantPriseEnCharge() != null) {
                    existingFormation.setMontantPriseEnCharge(formation.getMontantPriseEnCharge());
                }
                if (formation.getDetailPC() != null) {
                    existingFormation.setDetailPC(formation.getDetailPC());
                }
                if (formation.getNomDiplome() != null) {
                    existingFormation.setNomDiplome(formation.getNomDiplome());
                }
                if (formation.getNomDebouche() != null) {
                    existingFormation.setNomDebouche(formation.getNomDebouche());
                }

                return existingFormation;
            })
            .map(formationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Formation> findAll(Pageable pageable) {
        log.debug("Request to get all Formations");
        return formationRepository.findAll(pageable);
    }

    /**
     *  Get all the formations where Demande is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Formation> findAllWhereDemandeIsNull() {
        log.debug("Request to get all formations where Demande is null");
        return StreamSupport
            .stream(formationRepository.findAll().spliterator(), false)
            .filter(formation -> formation.getDemande() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Formation> findOne(Long id) {
        log.debug("Request to get Formation : {}", id);
        return formationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formation : {}", id);
        formationRepository.deleteById(id);
    }
}
