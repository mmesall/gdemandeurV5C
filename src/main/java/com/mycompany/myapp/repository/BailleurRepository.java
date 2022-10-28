package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Bailleur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bailleur entity.
 */
@Repository
public interface BailleurRepository extends JpaRepository<Bailleur, Long> {
    default Optional<Bailleur> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Bailleur> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Bailleur> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct bailleur from Bailleur bailleur left join fetch bailleur.agent",
        countQuery = "select count(distinct bailleur) from Bailleur bailleur"
    )
    Page<Bailleur> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct bailleur from Bailleur bailleur left join fetch bailleur.agent")
    List<Bailleur> findAllWithToOneRelationships();

    @Query("select bailleur from Bailleur bailleur left join fetch bailleur.agent where bailleur.id =:id")
    Optional<Bailleur> findOneWithToOneRelationships(@Param("id") Long id);
}
