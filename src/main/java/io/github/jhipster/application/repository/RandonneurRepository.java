package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Randonneur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Randonneur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RandonneurRepository extends JpaRepository<Randonneur, Long> {
    @Query("select distinct randonneur from Randonneur randonneur left join fetch randonneur.marcheurs left join fetch randonneur.envoyeurs")
    List<Randonneur> findAllWithEagerRelationships();

    @Query("select randonneur from Randonneur randonneur left join fetch randonneur.marcheurs left join fetch randonneur.envoyeurs where randonneur.id =:id")
    Randonneur findOneWithEagerRelationships(@Param("id") Long id);

}
