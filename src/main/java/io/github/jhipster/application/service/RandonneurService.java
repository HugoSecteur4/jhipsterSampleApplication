package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Randonneur;
import io.github.jhipster.application.repository.RandonneurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Randonneur.
 */
@Service
@Transactional
public class RandonneurService {

    private final Logger log = LoggerFactory.getLogger(RandonneurService.class);

    private final RandonneurRepository randonneurRepository;

    public RandonneurService(RandonneurRepository randonneurRepository) {
        this.randonneurRepository = randonneurRepository;
    }

    /**
     * Save a randonneur.
     *
     * @param randonneur the entity to save
     * @return the persisted entity
     */
    public Randonneur save(Randonneur randonneur) {
        log.debug("Request to save Randonneur : {}", randonneur);
        return randonneurRepository.save(randonneur);
    }

    /**
     * Get all the randonneurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Randonneur> findAll(Pageable pageable) {
        log.debug("Request to get all Randonneurs");
        return randonneurRepository.findAll(pageable);
    }

    /**
     * Get one randonneur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Randonneur findOne(Long id) {
        log.debug("Request to get Randonneur : {}", id);
        return randonneurRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the randonneur by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Randonneur : {}", id);
        randonneurRepository.delete(id);
    }
}
