package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Serie;
import com.mycompany.myapp.repository.SerieRepository;
import com.mycompany.myapp.service.SerieService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Serie}.
 */
@Service
@Transactional
public class SerieServiceImpl implements SerieService {

    private final Logger log = LoggerFactory.getLogger(SerieServiceImpl.class);

    private final SerieRepository serieRepository;

    public SerieServiceImpl(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @Override
    public Serie save(Serie serie) {
        log.debug("Request to save Serie : {}", serie);
        return serieRepository.save(serie);
    }

    @Override
    public Optional<Serie> partialUpdate(Serie serie) {
        log.debug("Request to partially update Serie : {}", serie);

        return serieRepository
            .findById(serie.getId())
            .map(existingSerie -> {
                if (serie.getNomSerie() != null) {
                    existingSerie.setNomSerie(serie.getNomSerie());
                }
                if (serie.getNiveauEtude() != null) {
                    existingSerie.setNiveauEtude(serie.getNiveauEtude());
                }
                if (serie.getProgramme() != null) {
                    existingSerie.setProgramme(serie.getProgramme());
                }
                if (serie.getAutreSerie() != null) {
                    existingSerie.setAutreSerie(serie.getAutreSerie());
                }

                return existingSerie;
            })
            .map(serieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Serie> findAll(Pageable pageable) {
        log.debug("Request to get all Series");
        return serieRepository.findAll(pageable);
    }

    public Page<Serie> findAllWithEagerRelationships(Pageable pageable) {
        return serieRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Serie> findOne(Long id) {
        log.debug("Request to get Serie : {}", id);
        return serieRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Serie : {}", id);
        serieRepository.deleteById(id);
    }
}
