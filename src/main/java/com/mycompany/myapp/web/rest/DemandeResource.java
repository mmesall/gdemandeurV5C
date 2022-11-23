package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Demande;
import com.mycompany.myapp.repository.DemandeRepository;
import com.mycompany.myapp.service.DemandeService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Demande}.
 */
@RestController
@RequestMapping("/api")
public class DemandeResource {

    private final Logger log = LoggerFactory.getLogger(DemandeResource.class);

    private static final String ENTITY_NAME = "demande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeService demandeService;

    private final DemandeRepository demandeRepository;

    public DemandeResource(DemandeService demandeService, DemandeRepository demandeRepository) {
        this.demandeService = demandeService;
        this.demandeRepository = demandeRepository;
    }

    /**
     * {@code POST  /demandes} : Create a new demande.
     *
     * @param demande the demande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demande, or with status {@code 400 (Bad Request)} if the demande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demandes")
    public ResponseEntity<Demande> createDemande(@RequestBody Demande demande) throws URISyntaxException {
        log.debug("REST request to save Demande : {}", demande);
        if (demande.getId() != null) {
            throw new BadRequestAlertException("A new demande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Demande result = demandeService.save(demande);
        return ResponseEntity
            .created(new URI("/api/demandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demandes/:id} : Updates an existing demande.
     *
     * @param id the id of the demande to save.
     * @param demande the demande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demande,
     * or with status {@code 400 (Bad Request)} if the demande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demandes/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable(value = "id", required = false) final Long id, @RequestBody Demande demande)
        throws URISyntaxException {
        log.debug("REST request to update Demande : {}, {}", id, demande);
        if (demande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demande.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Demande result = demandeService.save(demande);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demande.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demandes/:id} : Partial updates given fields of an existing demande, field will ignore if it is null
     *
     * @param id the id of the demande to save.
     * @param demande the demande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demande,
     * or with status {@code 400 (Bad Request)} if the demande is not valid,
     * or with status {@code 404 (Not Found)} if the demande is not found,
     * or with status {@code 500 (Internal Server Error)} if the demande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demandes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Demande> partialUpdateDemande(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Demande demande
    ) throws URISyntaxException {
        log.debug("REST request to partial update Demande partially : {}, {}", id, demande);
        if (demande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demande.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Demande> result = demandeService.partialUpdate(demande);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demande.getId().toString())
        );
    }

    /**
     * {@code GET  /demandes} : get all the demandes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandes in body.
     */
    @GetMapping("/demandes")
    public ResponseEntity<List<Demande>> getAllDemandes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Demandes");
        Page<Demande> page;
        if (eagerload) {
            page = demandeService.findAllWithEagerRelationships(pageable);
        } else {
            page = demandeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demandes/:id} : get the "id" demande.
     *
     * @param id the id of the demande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demandes/{id}")
    public ResponseEntity<Demande> getDemande(@PathVariable Long id) {
        log.debug("REST request to get Demande : {}", id);
        Optional<Demande> demande = demandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demande);
    }

    /**
     * {@code DELETE  /demandes/:id} : delete the "id" demande.
     *
     * @param id the id of the demande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demandes/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        log.debug("REST request to delete Demande : {}", id);
        demandeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
