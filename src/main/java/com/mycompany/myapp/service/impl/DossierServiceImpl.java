package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.repository.DossierRepository;
import com.mycompany.myapp.service.DossierService;
import java.util.Calendar;
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
 * Service Implementation for managing {@link Dossier}.
 */
@Service
@Transactional
public class DossierServiceImpl implements DossierService {

    private final Logger log = LoggerFactory.getLogger(DossierServiceImpl.class);

    private final DossierRepository dossierRepository;

    public DossierServiceImpl(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    // @Override
    // public Dossier save(Dossier dossier) {
    //     log.debug("Request to save Dossier : {}", dossier);
    //     return dossierRepository.save(dossier);
    // }

    @Override
    public Dossier save(Dossier dossier) {
        // log.debug("Request to save Dossier : {}", dossier);
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String date = String.valueOf(System.currentTimeMillis());

        // String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Random rnd = new Random();
        // char letter = alphabet.charAt(rnd.nextInt(alphabet.length()));

        String region = "DK";
        if (dossier.getRegion().equals(NomRegion.THIES)) {
            region = "TH";
        } else if (dossier.getRegion().equals(NomRegion.DIOURBEL)) {
            region = "DB";
        } else if (dossier.getRegion().equals(NomRegion.FATICK)) {
            region = "FA";
        } else if (dossier.getRegion().equals(NomRegion.KAOLACK)) {
            region = "KL";
        } else if (dossier.getRegion().equals(NomRegion.KAFFRINE)) {
            region = "KF";
        } else if (dossier.getRegion().equals(NomRegion.KEDOUGOU)) {
            region = "KD";
        } else if (dossier.getRegion().equals(NomRegion.KOLDA)) {
            region = "KA";
        } else if (dossier.getRegion().equals(NomRegion.LOUGA)) {
            region = "LG";
        } else if (dossier.getRegion().equals(NomRegion.MATAM)) {
            region = "MA";
        } else if (dossier.getRegion().equals(NomRegion.SAINT_LOUIS)) {
            region = "SL";
        } else if (dossier.getRegion().equals(NomRegion.SEDHIOU)) {
            region = "SD";
        } else if (dossier.getRegion().equals(NomRegion.TAMBACOUNDA)) {
            region = "TA";
        } else if (dossier.getRegion().equals(NomRegion.ZIGINCHOR)) {
            region = "ZG";
        } else {
            region = "DK";
        }
        String numDoss = year.substring(year.length() - 2).concat(date.substring(date.length() - 4)).concat(region);

        // .concat(String.valueOf(letter));

        dossier.setNumDossier(numDoss);
        return dossierRepository.save(dossier);
    }

    @Override
    public Optional<Dossier> partialUpdate(Dossier dossier) {
        log.debug("Request to partially update Dossier : {}", dossier);

        return dossierRepository
            .findById(dossier.getId())
            .map(existingDossier -> {
                if (dossier.getNumDossier() != null) {
                    existingDossier.setNumDossier(dossier.getNumDossier());
                }
                if (dossier.getTypeDemandeur() != null) {
                    existingDossier.setTypeDemandeur(dossier.getTypeDemandeur());
                }
                if (dossier.getNom() != null) {
                    existingDossier.setNom(dossier.getNom());
                }
                if (dossier.getPrenom() != null) {
                    existingDossier.setPrenom(dossier.getPrenom());
                }
                if (dossier.getAdresse() != null) {
                    existingDossier.setAdresse(dossier.getAdresse());
                }
                if (dossier.getRegion() != null) {
                    existingDossier.setRegion(dossier.getRegion());
                }
                if (dossier.getTelephone() != null) {
                    existingDossier.setTelephone(dossier.getTelephone());
                }
                if (dossier.getEmail() != null) {
                    existingDossier.setEmail(dossier.getEmail());
                }
                if (dossier.getPhoto() != null) {
                    existingDossier.setPhoto(dossier.getPhoto());
                }
                if (dossier.getPhotoContentType() != null) {
                    existingDossier.setPhotoContentType(dossier.getPhotoContentType());
                }
                if (dossier.getCv() != null) {
                    existingDossier.setCv(dossier.getCv());
                }
                if (dossier.getCvContentType() != null) {
                    existingDossier.setCvContentType(dossier.getCvContentType());
                }
                if (dossier.getLettreMotivation() != null) {
                    existingDossier.setLettreMotivation(dossier.getLettreMotivation());
                }
                if (dossier.getDiplomeRequis() != null) {
                    existingDossier.setDiplomeRequis(dossier.getDiplomeRequis());
                }
                if (dossier.getNiveauEtude() != null) {
                    existingDossier.setNiveauEtude(dossier.getNiveauEtude());
                }
                if (dossier.getProfession() != null) {
                    existingDossier.setProfession(dossier.getProfession());
                }

                return existingDossier;
            })
            .map(dossierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dossier> findAll(Pageable pageable) {
        log.debug("Request to get all Dossiers");
        return dossierRepository.findAll(pageable);
    }

    public Page<Dossier> findAllWithEagerRelationships(Pageable pageable) {
        return dossierRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the dossiers where Eleve is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Dossier> findAllWhereEleveIsNull() {
        log.debug("Request to get all dossiers where Eleve is null");
        return StreamSupport
            .stream(dossierRepository.findAll().spliterator(), false)
            .filter(dossier -> dossier.getEleve() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the dossiers where Etudiant is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Dossier> findAllWhereEtudiantIsNull() {
        log.debug("Request to get all dossiers where Etudiant is null");
        return StreamSupport
            .stream(dossierRepository.findAll().spliterator(), false)
            .filter(dossier -> dossier.getEtudiant() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the dossiers where Professionnel is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Dossier> findAllWhereProfessionnelIsNull() {
        log.debug("Request to get all dossiers where Professionnel is null");
        return StreamSupport
            .stream(dossierRepository.findAll().spliterator(), false)
            .filter(dossier -> dossier.getProfessionnel() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the dossiers where Demandeur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Dossier> findAllWhereDemandeurIsNull() {
        log.debug("Request to get all dossiers where Demandeur is null");
        return StreamSupport
            .stream(dossierRepository.findAll().spliterator(), false)
            .filter(dossier -> dossier.getDemandeur() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Dossier> findOne(Long id) {
        log.debug("Request to get Dossier : {}", id);
        return dossierRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dossier : {}", id);
        dossierRepository.deleteById(id);
    }
}
