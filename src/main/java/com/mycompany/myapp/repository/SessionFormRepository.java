package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SessionForm;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SessionForm entity.
 */
@Repository
public interface SessionFormRepository extends JpaRepository<SessionForm, Long> {
    default Optional<SessionForm> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SessionForm> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SessionForm> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct sessionForm from SessionForm sessionForm left join fetch sessionForm.formation left join fetch sessionForm.etablissement",
        countQuery = "select count(distinct sessionForm) from SessionForm sessionForm"
    )
    Page<SessionForm> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct sessionForm from SessionForm sessionForm left join fetch sessionForm.formation left join fetch sessionForm.etablissement"
    )
    List<SessionForm> findAllWithToOneRelationships();

    @Query(
        "select sessionForm from SessionForm sessionForm left join fetch sessionForm.formation left join fetch sessionForm.etablissement where sessionForm.id =:id"
    )
    Optional<SessionForm> findOneWithToOneRelationships(@Param("id") Long id);
}
