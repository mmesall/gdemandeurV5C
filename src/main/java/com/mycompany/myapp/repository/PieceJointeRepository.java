package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PieceJointe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PieceJointe entity.
 */
@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {
    default Optional<PieceJointe> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PieceJointe> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PieceJointe> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct pieceJointe from PieceJointe pieceJointe left join fetch pieceJointe.demande",
        countQuery = "select count(distinct pieceJointe) from PieceJointe pieceJointe"
    )
    Page<PieceJointe> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct pieceJointe from PieceJointe pieceJointe left join fetch pieceJointe.demande")
    List<PieceJointe> findAllWithToOneRelationships();

    @Query("select pieceJointe from PieceJointe pieceJointe left join fetch pieceJointe.demande where pieceJointe.id =:id")
    Optional<PieceJointe> findOneWithToOneRelationships(@Param("id") Long id);
}
