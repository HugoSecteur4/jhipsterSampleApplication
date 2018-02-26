package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Randonnee;
import io.github.jhipster.application.service.RandonneeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Randonnee.
 */
@RestController
@RequestMapping("/api")
public class RandonneeResource {

    private final Logger log = LoggerFactory.getLogger(RandonneeResource.class);

    private static final String ENTITY_NAME = "randonnee";

    private final RandonneeService randonneeService;

    public RandonneeResource(RandonneeService randonneeService) {
        this.randonneeService = randonneeService;
    }

    /**
     * POST  /randonnees : Create a new randonnee.
     *
     * @param randonnee the randonnee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new randonnee, or with status 400 (Bad Request) if the randonnee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/randonnees")
    @Timed
    public ResponseEntity<Randonnee> createRandonnee(@RequestBody Randonnee randonnee) throws URISyntaxException {
        log.debug("REST request to save Randonnee : {}", randonnee);
        if (randonnee.getId() != null) {
            throw new BadRequestAlertException("A new randonnee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Randonnee result = randonneeService.save(randonnee);
        return ResponseEntity.created(new URI("/api/randonnees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /randonnees : Updates an existing randonnee.
     *
     * @param randonnee the randonnee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated randonnee,
     * or with status 400 (Bad Request) if the randonnee is not valid,
     * or with status 500 (Internal Server Error) if the randonnee couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/randonnees")
    @Timed
    public ResponseEntity<Randonnee> updateRandonnee(@RequestBody Randonnee randonnee) throws URISyntaxException {
        log.debug("REST request to update Randonnee : {}", randonnee);
        if (randonnee.getId() == null) {
            return createRandonnee(randonnee);
        }
        Randonnee result = randonneeService.save(randonnee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, randonnee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /randonnees : get all the randonnees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of randonnees in body
     */
    @GetMapping("/randonnees")
    @Timed
    public ResponseEntity<List<Randonnee>> getAllRandonnees(Pageable pageable) {
        log.debug("REST request to get a page of Randonnees");
        Page<Randonnee> page = randonneeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/randonnees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /randonnees/:id : get the "id" randonnee.
     *
     * @param id the id of the randonnee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the randonnee, or with status 404 (Not Found)
     */
    @GetMapping("/randonnees/{id}")
    @Timed
    public ResponseEntity<Randonnee> getRandonnee(@PathVariable Long id) {
        log.debug("REST request to get Randonnee : {}", id);
        Randonnee randonnee = randonneeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(randonnee));
    }

    /**
     * DELETE  /randonnees/:id : delete the "id" randonnee.
     *
     * @param id the id of the randonnee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/randonnees/{id}")
    @Timed
    public ResponseEntity<Void> deleteRandonnee(@PathVariable Long id) {
        log.debug("REST request to delete Randonnee : {}", id);
        randonneeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
