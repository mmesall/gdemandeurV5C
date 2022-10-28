package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Commission;
import com.mycompany.myapp.repository.CommissionRepository;
import com.mycompany.myapp.service.CommissionService;
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
 * Service Implementation for managing {@link Commission}.
 */
@Service
@Transactional
public class CommissionServiceImpl implements CommissionService {

    private final Logger log = LoggerFactory.getLogger(CommissionServiceImpl.class);

    private final CommissionRepository commissionRepository;

    public CommissionServiceImpl(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    @Override
    public Commission save(Commission commission) {
        log.debug("Request to save Commission : {}", commission);
        return commissionRepository.save(commission);
    }

    @Override
    public Optional<Commission> partialUpdate(Commission commission) {
        log.debug("Request to partially update Commission : {}", commission);

        return commissionRepository
            .findById(commission.getId())
            .map(existingCommission -> {
                if (commission.getNomCommission() != null) {
                    existingCommission.setNomCommission(commission.getNomCommission());
                }
                if (commission.getMission() != null) {
                    existingCommission.setMission(commission.getMission());
                }

                return existingCommission;
            })
            .map(commissionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Commission> findAll(Pageable pageable) {
        log.debug("Request to get all Commissions");
        return commissionRepository.findAll(pageable);
    }

    /**
     *  Get all the commissions where Services is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Commission> findAllWhereServicesIsNull() {
        log.debug("Request to get all commissions where Services is null");
        return StreamSupport
            .stream(commissionRepository.findAll().spliterator(), false)
            .filter(commission -> commission.getServices() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Commission> findOne(Long id) {
        log.debug("Request to get Commission : {}", id);
        return commissionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commission : {}", id);
        commissionRepository.deleteById(id);
    }
}
