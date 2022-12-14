package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Membre;
import com.mycompany.myapp.repository.MembreRepository;
import com.mycompany.myapp.service.MembreService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Membre}.
 */
@RestController
@RequestMapping("/api")
public class MembreResource {

    private final Logger log = LoggerFactory.getLogger(MembreResource.class);

    private static final String ENTITY_NAME = "membre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MembreService membreService;

    private final MembreRepository membreRepository;

    public MembreResource(MembreService membreService, MembreRepository membreRepository) {
        this.membreService = membreService;
        this.membreRepository = membreRepository;
    }

    /**
     * {@code POST  /membres} : Create a new membre.
     *
     * @param membre the membre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new membre, or with status {@code 400 (Bad Request)} if the membre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/membres")
    public ResponseEntity<Membre> createMembre(@RequestBody Membre membre) throws URISyntaxException {
        log.debug("REST request to save Membre : {}", membre);
        if (membre.getId() != null) {
            throw new BadRequestAlertException("A new membre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Membre result = membreService.save(membre);
        return ResponseEntity
            .created(new URI("/api/membres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /membres/:id} : Updates an existing membre.
     *
     * @param id the id of the membre to save.
     * @param membre the membre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membre,
     * or with status {@code 400 (Bad Request)} if the membre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the membre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/membres/{id}")
    public ResponseEntity<Membre> updateMembre(@PathVariable(value = "id", required = false) final Long id, @RequestBody Membre membre)
        throws URISyntaxException {
        log.debug("REST request to update Membre : {}, {}", id, membre);
        if (membre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Membre result = membreService.save(membre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, membre.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /membres/:id} : Partial updates given fields of an existing membre, field will ignore if it is null
     *
     * @param id the id of the membre to save.
     * @param membre the membre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated membre,
     * or with status {@code 400 (Bad Request)} if the membre is not valid,
     * or with status {@code 404 (Not Found)} if the membre is not found,
     * or with status {@code 500 (Internal Server Error)} if the membre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/membres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Membre> partialUpdateMembre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Membre membre
    ) throws URISyntaxException {
        log.debug("REST request to partial update Membre partially : {}, {}", id, membre);
        if (membre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, membre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!membreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Membre> result = membreService.partialUpdate(membre);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, membre.getId().toString())
        );
    }

    /**
     * {@code GET  /membres} : get all the membres.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of membres in body.
     */
    @GetMapping("/membres")
    public ResponseEntity<List<Membre>> getAllMembres(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Membres");
        Page<Membre> page;
        if (eagerload) {
            page = membreService.findAllWithEagerRelationships(pageable);
        } else {
            page = membreService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /membres/:id} : get the "id" membre.
     *
     * @param id the id of the membre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the membre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/membres/{id}")
    public ResponseEntity<Membre> getMembre(@PathVariable Long id) {
        log.debug("REST request to get Membre : {}", id);
        Optional<Membre> membre = membreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membre);
    }

    /**
     * {@code DELETE  /membres/:id} : delete the "id" membre.
     *
     * @param id the id of the membre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/membres/{id}")
    public ResponseEntity<Void> deleteMembre(@PathVariable Long id) {
        log.debug("REST request to delete Membre : {}", id);
        membreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
