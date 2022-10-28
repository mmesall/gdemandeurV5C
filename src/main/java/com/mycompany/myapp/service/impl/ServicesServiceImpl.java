package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Services;
import com.mycompany.myapp.repository.ServicesRepository;
import com.mycompany.myapp.service.ServicesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Services}.
 */
@Service
@Transactional
public class ServicesServiceImpl implements ServicesService {

    private final Logger log = LoggerFactory.getLogger(ServicesServiceImpl.class);

    private final ServicesRepository servicesRepository;

    public ServicesServiceImpl(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @Override
    public Services save(Services services) {
        log.debug("Request to save Services : {}", services);
        return servicesRepository.save(services);
    }

    @Override
    public Optional<Services> partialUpdate(Services services) {
        log.debug("Request to partially update Services : {}", services);

        return servicesRepository
            .findById(services.getId())
            .map(existingServices -> {
                if (services.getLogo() != null) {
                    existingServices.setLogo(services.getLogo());
                }
                if (services.getLogoContentType() != null) {
                    existingServices.setLogoContentType(services.getLogoContentType());
                }
                if (services.getNomService() != null) {
                    existingServices.setNomService(services.getNomService());
                }
                if (services.getChefService() != null) {
                    existingServices.setChefService(services.getChefService());
                }
                if (services.getDescription() != null) {
                    existingServices.setDescription(services.getDescription());
                }
                if (services.getIsPilotage() != null) {
                    existingServices.setIsPilotage(services.getIsPilotage());
                }

                return existingServices;
            })
            .map(servicesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Services> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return servicesRepository.findAll(pageable);
    }

    public Page<Services> findAllWithEagerRelationships(Pageable pageable) {
        return servicesRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Services> findOne(Long id) {
        log.debug("Request to get Services : {}", id);
        return servicesRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Services : {}", id);
        servicesRepository.deleteById(id);
    }
}
