package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Localite;
import com.mycompany.myapp.repository.LocaliteRepository;
import com.mycompany.myapp.service.LocaliteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Localite}.
 */
@Service
@Transactional
public class LocaliteServiceImpl implements LocaliteService {

    private final Logger log = LoggerFactory.getLogger(LocaliteServiceImpl.class);

    private final LocaliteRepository localiteRepository;

    public LocaliteServiceImpl(LocaliteRepository localiteRepository) {
        this.localiteRepository = localiteRepository;
    }

    @Override
    public Localite save(Localite localite) {
        log.debug("Request to save Localite : {}", localite);
        return localiteRepository.save(localite);
    }

    @Override
    public Optional<Localite> partialUpdate(Localite localite) {
        log.debug("Request to partially update Localite : {}", localite);

        return localiteRepository
            .findById(localite.getId())
            .map(existingLocalite -> {
                if (localite.getRegion() != null) {
                    existingLocalite.setRegion(localite.getRegion());
                }
                if (localite.getAutreRegion() != null) {
                    existingLocalite.setAutreRegion(localite.getAutreRegion());
                }
                if (localite.getDepartementDakar() != null) {
                    existingLocalite.setDepartementDakar(localite.getDepartementDakar());
                }
                if (localite.getDepartementDiourbel() != null) {
                    existingLocalite.setDepartementDiourbel(localite.getDepartementDiourbel());
                }
                if (localite.getDepartementFatick() != null) {
                    existingLocalite.setDepartementFatick(localite.getDepartementFatick());
                }
                if (localite.getDepartementKaffrine() != null) {
                    existingLocalite.setDepartementKaffrine(localite.getDepartementKaffrine());
                }
                if (localite.getDepartementKaolack() != null) {
                    existingLocalite.setDepartementKaolack(localite.getDepartementKaolack());
                }
                if (localite.getDepartementKedougou() != null) {
                    existingLocalite.setDepartementKedougou(localite.getDepartementKedougou());
                }
                if (localite.getDepartementKolda() != null) {
                    existingLocalite.setDepartementKolda(localite.getDepartementKolda());
                }
                if (localite.getDepartementLouga() != null) {
                    existingLocalite.setDepartementLouga(localite.getDepartementLouga());
                }
                if (localite.getDepartementMatam() != null) {
                    existingLocalite.setDepartementMatam(localite.getDepartementMatam());
                }
                if (localite.getDepartementSaint() != null) {
                    existingLocalite.setDepartementSaint(localite.getDepartementSaint());
                }
                if (localite.getDepartementSedhiou() != null) {
                    existingLocalite.setDepartementSedhiou(localite.getDepartementSedhiou());
                }
                if (localite.getDepartementTambacounda() != null) {
                    existingLocalite.setDepartementTambacounda(localite.getDepartementTambacounda());
                }
                if (localite.getDepartementThis() != null) {
                    existingLocalite.setDepartementThis(localite.getDepartementThis());
                }
                if (localite.getDepartementZiguinchor() != null) {
                    existingLocalite.setDepartementZiguinchor(localite.getDepartementZiguinchor());
                }
                if (localite.getAutredepartementDakar() != null) {
                    existingLocalite.setAutredepartementDakar(localite.getAutredepartementDakar());
                }
                if (localite.getAutredepartementDiourbel() != null) {
                    existingLocalite.setAutredepartementDiourbel(localite.getAutredepartementDiourbel());
                }
                if (localite.getAutredepartementFatick() != null) {
                    existingLocalite.setAutredepartementFatick(localite.getAutredepartementFatick());
                }
                if (localite.getAutredepartementKaffrine() != null) {
                    existingLocalite.setAutredepartementKaffrine(localite.getAutredepartementKaffrine());
                }
                if (localite.getAutredepartementKaolack() != null) {
                    existingLocalite.setAutredepartementKaolack(localite.getAutredepartementKaolack());
                }
                if (localite.getAutredepartementKedougou() != null) {
                    existingLocalite.setAutredepartementKedougou(localite.getAutredepartementKedougou());
                }
                if (localite.getAutredepartementKolda() != null) {
                    existingLocalite.setAutredepartementKolda(localite.getAutredepartementKolda());
                }
                if (localite.getAutredepartementLouga() != null) {
                    existingLocalite.setAutredepartementLouga(localite.getAutredepartementLouga());
                }
                if (localite.getAutredepartementMatam() != null) {
                    existingLocalite.setAutredepartementMatam(localite.getAutredepartementMatam());
                }
                if (localite.getAutredepartementSaint() != null) {
                    existingLocalite.setAutredepartementSaint(localite.getAutredepartementSaint());
                }
                if (localite.getAutredepartementSedhiou() != null) {
                    existingLocalite.setAutredepartementSedhiou(localite.getAutredepartementSedhiou());
                }
                if (localite.getAutredepartementTambacounda() != null) {
                    existingLocalite.setAutredepartementTambacounda(localite.getAutredepartementTambacounda());
                }
                if (localite.getAutredepartementThis() != null) {
                    existingLocalite.setAutredepartementThis(localite.getAutredepartementThis());
                }
                if (localite.getAutredepartementZiguinchor() != null) {
                    existingLocalite.setAutredepartementZiguinchor(localite.getAutredepartementZiguinchor());
                }
                if (localite.getCommune() != null) {
                    existingLocalite.setCommune(localite.getCommune());
                }
                if (localite.getNomQuartier() != null) {
                    existingLocalite.setNomQuartier(localite.getNomQuartier());
                }

                return existingLocalite;
            })
            .map(localiteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Localite> findAll(Pageable pageable) {
        log.debug("Request to get all Localites");
        return localiteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Localite> findOne(Long id) {
        log.debug("Request to get Localite : {}", id);
        return localiteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Localite : {}", id);
        localiteRepository.deleteById(id);
    }
}
