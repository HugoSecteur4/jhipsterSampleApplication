package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Randonnee;
import io.github.jhipster.application.repository.RandonneeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Randonnee.
 */
@Service
@Transactional
public class RandonneeService {

    private final Logger log = LoggerFactory.getLogger(RandonneeService.class);

    private final RandonneeRepository randonneeRepository;

    public RandonneeService(RandonneeRepository randonneeRepository) {
        this.randonneeRepository = randonneeRepository;
    }

    /**
     * Save a randonnee.
     *
     * @param randonnee the entity to save
     * @return the persisted entity
     */
    public Randonnee save(Randonnee randonnee) {
        log.debug("Request to save Randonnee : {}", randonnee);
        return randonneeRepository.save(randonnee);
    }

    /**
     * Get all the randonnees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Randonnee> findAll(Pageable pageable) {
        log.debug("Request to get all Randonnees");
        return randonneeRepository.findAll(pageable);
    }

    /**
     * Get one randonnee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Randonnee findOne(Long id) {
        log.debug("Request to get Randonnee : {}", id);
        return randonneeRepository.findOne(id);
    }

    /**
     * Delete the randonnee by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Randonnee : {}", id);
        randonneeRepository.delete(id);
    }
}
