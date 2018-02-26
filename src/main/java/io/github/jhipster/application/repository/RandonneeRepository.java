package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Randonnee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Randonnee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RandonneeRepository extends JpaRepository<Randonnee, Long> {

}
