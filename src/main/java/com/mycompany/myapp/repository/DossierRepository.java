package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Dossier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Dossier entity.
 */
@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {
    default Optional<Dossier> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Dossier> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Dossier> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dossier from Dossier dossier left join fetch dossier.user",
        countQuery = "select count(distinct dossier) from Dossier dossier"
    )
    Page<Dossier> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct dossier from Dossier dossier left join fetch dossier.user")
    List<Dossier> findAllWithToOneRelationships();

    @Query("select dossier from Dossier dossier left join fetch dossier.user where dossier.id =:id")
    Optional<Dossier> findOneWithToOneRelationships(@Param("id") Long id);
}
