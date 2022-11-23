package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PieceJointe;
import com.mycompany.myapp.repository.PieceJointeRepository;
import com.mycompany.myapp.service.PieceJointeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PieceJointe}.
 */
@Service
@Transactional
public class PieceJointeServiceImpl implements PieceJointeService {

    private final Logger log = LoggerFactory.getLogger(PieceJointeServiceImpl.class);

    private final PieceJointeRepository pieceJointeRepository;

    public PieceJointeServiceImpl(PieceJointeRepository pieceJointeRepository) {
        this.pieceJointeRepository = pieceJointeRepository;
    }

    @Override
    public PieceJointe save(PieceJointe pieceJointe) {
        log.debug("Request to save PieceJointe : {}", pieceJointe);
        return pieceJointeRepository.save(pieceJointe);
    }

    @Override
    public Optional<PieceJointe> partialUpdate(PieceJointe pieceJointe) {
        log.debug("Request to partially update PieceJointe : {}", pieceJointe);

        return pieceJointeRepository
            .findById(pieceJointe.getId())
            .map(existingPieceJointe -> {
                if (pieceJointe.getTypePiece() != null) {
                    existingPieceJointe.setTypePiece(pieceJointe.getTypePiece());
                }
                if (pieceJointe.getNomPiece() != null) {
                    existingPieceJointe.setNomPiece(pieceJointe.getNomPiece());
                }

                return existingPieceJointe;
            })
            .map(pieceJointeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PieceJointe> findAll(Pageable pageable) {
        log.debug("Request to get all PieceJointes");
        return pieceJointeRepository.findAll(pageable);
    }

    public Page<PieceJointe> findAllWithEagerRelationships(Pageable pageable) {
        return pieceJointeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PieceJointe> findOne(Long id) {
        log.debug("Request to get PieceJointe : {}", id);
        return pieceJointeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PieceJointe : {}", id);
        pieceJointeRepository.deleteById(id);
    }
}
