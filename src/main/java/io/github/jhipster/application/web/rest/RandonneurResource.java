package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Randonneur;
import io.github.jhipster.application.service.RandonneurService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Randonneur.
 */
@RestController
@RequestMapping("/api")
public class RandonneurResource {

    private final Logger log = LoggerFactory.getLogger(RandonneurResource.class);

    private static final String ENTITY_NAME = "randonneur";

    private final RandonneurService randonneurService;

    public RandonneurResource(RandonneurService randonneurService) {
        this.randonneurService = randonneurService;
    }

    /**
     * POST  /randonneurs : Create a new randonneur.
     *
     * @param randonneur the randonneur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new randonneur, or with status 400 (Bad Request) if the randonneur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/randonneurs")
    @Timed
    public ResponseEntity<Randonneur> createRandonneur(@Valid @RequestBody Randonneur randonneur) throws URISyntaxException {
        log.debug("REST request to save Randonneur : {}", randonneur);
        if (randonneur.getId() != null) {
            throw new BadRequestAlertException("A new randonneur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Randonneur result = randonneurService.save(randonneur);
        return ResponseEntity.created(new URI("/api/randonneurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /randonneurs : Updates an existing randonneur.
     *
     * @param randonneur the randonneur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated randonneur,
     * or with status 400 (Bad Request) if the randonneur is not valid,
     * or with status 500 (Internal Server Error) if the randonneur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/randonneurs")
    @Timed
    public ResponseEntity<Randonneur> updateRandonneur(@Valid @RequestBody Randonneur randonneur) throws URISyntaxException {
        log.debug("REST request to update Randonneur : {}", randonneur);
        if (randonneur.getId() == null) {
            return createRandonneur(randonneur);
        }
        Randonneur result = randonneurService.save(randonneur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, randonneur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /randonneurs : get all the randonneurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of randonneurs in body
     */
    @GetMapping("/randonneurs")
    @Timed
    public ResponseEntity<List<Randonneur>> getAllRandonneurs(Pageable pageable) {
        log.debug("REST request to get a page of Randonneurs");
        Page<Randonneur> page = randonneurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/randonneurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /randonneurs/:id : get the "id" randonneur.
     *
     * @param id the id of the randonneur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the randonneur, or with status 404 (Not Found)
     */
    @GetMapping("/randonneurs/{id}")
    @Timed
    public ResponseEntity<Randonneur> getRandonneur(@PathVariable Long id) {
        log.debug("REST request to get Randonneur : {}", id);
        Randonneur randonneur = randonneurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(randonneur));
    }

    /**
     * DELETE  /randonneurs/:id : delete the "id" randonneur.
     *
     * @param id the id of the randonneur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/randonneurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteRandonneur(@PathVariable Long id) {
        log.debug("REST request to delete Randonneur : {}", id);
        randonneurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
