package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.SessionForm;
import com.mycompany.myapp.repository.SessionFormRepository;
import com.mycompany.myapp.service.SessionFormService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SessionForm}.
 */
@Service
@Transactional
public class SessionFormServiceImpl implements SessionFormService {

    private final Logger log = LoggerFactory.getLogger(SessionFormServiceImpl.class);

    private final SessionFormRepository sessionFormRepository;

    public SessionFormServiceImpl(SessionFormRepository sessionFormRepository) {
        this.sessionFormRepository = sessionFormRepository;
    }

    @Override
    public SessionForm save(SessionForm sessionForm) {
        log.debug("Request to save SessionForm : {}", sessionForm);
        return sessionFormRepository.save(sessionForm);
    }

    @Override
    public Optional<SessionForm> partialUpdate(SessionForm sessionForm) {
        log.debug("Request to partially update SessionForm : {}", sessionForm);

        return sessionFormRepository
            .findById(sessionForm.getId())
            .map(existingSessionForm -> {
                if (sessionForm.getNomSession() != null) {
                    existingSessionForm.setNomSession(sessionForm.getNomSession());
                }
                if (sessionForm.getAnnee() != null) {
                    existingSessionForm.setAnnee(sessionForm.getAnnee());
                }
                if (sessionForm.getDateDebutSess() != null) {
                    existingSessionForm.setDateDebutSess(sessionForm.getDateDebutSess());
                }
                if (sessionForm.getDateFinSess() != null) {
                    existingSessionForm.setDateFinSess(sessionForm.getDateFinSess());
                }

                return existingSessionForm;
            })
            .map(sessionFormRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SessionForm> findAll(Pageable pageable) {
        log.debug("Request to get all SessionForms");
        return sessionFormRepository.findAll(pageable);
    }

    public Page<SessionForm> findAllWithEagerRelationships(Pageable pageable) {
        return sessionFormRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SessionForm> findOne(Long id) {
        log.debug("Request to get SessionForm : {}", id);
        return sessionFormRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SessionForm : {}", id);
        sessionFormRepository.deleteById(id);
    }
}
