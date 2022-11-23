package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PriseEnCharge;
import com.mycompany.myapp.repository.PriseEnChargeRepository;
import com.mycompany.myapp.service.PriseEnChargeService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PriseEnCharge}.
 */
@RestController
@RequestMapping("/api")
public class PriseEnChargeResource {

    private final Logger log = LoggerFactory.getLogger(PriseEnChargeResource.class);

    private static final String ENTITY_NAME = "priseEnCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PriseEnChargeService priseEnChargeService;

    private final PriseEnChargeRepository priseEnChargeRepository;

    public PriseEnChargeResource(PriseEnChargeService priseEnChargeService, PriseEnChargeRepository priseEnChargeRepository) {
        this.priseEnChargeService = priseEnChargeService;
        this.priseEnChargeRepository = priseEnChargeRepository;
    }

    /**
     * {@code POST  /prise-en-charges} : Create a new priseEnCharge.
     *
     * @param priseEnCharge the priseEnCharge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new priseEnCharge, or with status {@code 400 (Bad Request)} if the priseEnCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prise-en-charges")
    public ResponseEntity<PriseEnCharge> createPriseEnCharge(@RequestBody PriseEnCharge priseEnCharge) throws URISyntaxException {
        log.debug("REST request to save PriseEnCharge : {}", priseEnCharge);
        if (priseEnCharge.getId() != null) {
            throw new BadRequestAlertException("A new priseEnCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PriseEnCharge result = priseEnChargeService.save(priseEnCharge);
        return ResponseEntity
            .created(new URI("/api/prise-en-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prise-en-charges/:id} : Updates an existing priseEnCharge.
     *
     * @param id the id of the priseEnCharge to save.
     * @param priseEnCharge the priseEnCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priseEnCharge,
     * or with status {@code 400 (Bad Request)} if the priseEnCharge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the priseEnCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prise-en-charges/{id}")
    public ResponseEntity<PriseEnCharge> updatePriseEnCharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PriseEnCharge priseEnCharge
    ) throws URISyntaxException {
        log.debug("REST request to update PriseEnCharge : {}, {}", id, priseEnCharge);
        if (priseEnCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priseEnCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priseEnChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PriseEnCharge result = priseEnChargeService.save(priseEnCharge);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priseEnCharge.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /prise-en-charges/:id} : Partial updates given fields of an existing priseEnCharge, field will ignore if it is null
     *
     * @param id the id of the priseEnCharge to save.
     * @param priseEnCharge the priseEnCharge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated priseEnCharge,
     * or with status {@code 400 (Bad Request)} if the priseEnCharge is not valid,
     * or with status {@code 404 (Not Found)} if the priseEnCharge is not found,
     * or with status {@code 500 (Internal Server Error)} if the priseEnCharge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/prise-en-charges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PriseEnCharge> partialUpdatePriseEnCharge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PriseEnCharge priseEnCharge
    ) throws URISyntaxException {
        log.debug("REST request to partial update PriseEnCharge partially : {}, {}", id, priseEnCharge);
        if (priseEnCharge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, priseEnCharge.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!priseEnChargeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PriseEnCharge> result = priseEnChargeService.partialUpdate(priseEnCharge);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, priseEnCharge.getId().toString())
        );
    }

    /**
     * {@code GET  /prise-en-charges} : get all the priseEnCharges.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of priseEnCharges in body.
     */
    @GetMapping("/prise-en-charges")
    public ResponseEntity<List<PriseEnCharge>> getAllPriseEnCharges(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of PriseEnCharges");
        Page<PriseEnCharge> page;
        if (eagerload) {
            page = priseEnChargeService.findAllWithEagerRelationships(pageable);
        } else {
            page = priseEnChargeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prise-en-charges/:id} : get the "id" priseEnCharge.
     *
     * @param id the id of the priseEnCharge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the priseEnCharge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prise-en-charges/{id}")
    public ResponseEntity<PriseEnCharge> getPriseEnCharge(@PathVariable Long id) {
        log.debug("REST request to get PriseEnCharge : {}", id);
        Optional<PriseEnCharge> priseEnCharge = priseEnChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(priseEnCharge);
    }

    /**
     * {@code DELETE  /prise-en-charges/:id} : delete the "id" priseEnCharge.
     *
     * @param id the id of the priseEnCharge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prise-en-charges/{id}")
    public ResponseEntity<Void> deletePriseEnCharge(@PathVariable Long id) {
        log.debug("REST request to delete PriseEnCharge : {}", id);
        priseEnChargeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
