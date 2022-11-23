package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Bailleur;
import com.mycompany.myapp.repository.BailleurRepository;
import com.mycompany.myapp.service.BailleurService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Bailleur}.
 */
@RestController
@RequestMapping("/api")
public class BailleurResource {

    private final Logger log = LoggerFactory.getLogger(BailleurResource.class);

    private static final String ENTITY_NAME = "bailleur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BailleurService bailleurService;

    private final BailleurRepository bailleurRepository;

    public BailleurResource(BailleurService bailleurService, BailleurRepository bailleurRepository) {
        this.bailleurService = bailleurService;
        this.bailleurRepository = bailleurRepository;
    }

    /**
     * {@code POST  /bailleurs} : Create a new bailleur.
     *
     * @param bailleur the bailleur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bailleur, or with status {@code 400 (Bad Request)} if the bailleur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bailleurs")
    public ResponseEntity<Bailleur> createBailleur(@RequestBody Bailleur bailleur) throws URISyntaxException {
        log.debug("REST request to save Bailleur : {}", bailleur);
        if (bailleur.getId() != null) {
            throw new BadRequestAlertException("A new bailleur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bailleur result = bailleurService.save(bailleur);
        return ResponseEntity
            .created(new URI("/api/bailleurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bailleurs/:id} : Updates an existing bailleur.
     *
     * @param id the id of the bailleur to save.
     * @param bailleur the bailleur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bailleur,
     * or with status {@code 400 (Bad Request)} if the bailleur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bailleur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bailleurs/{id}")
    public ResponseEntity<Bailleur> updateBailleur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bailleur bailleur
    ) throws URISyntaxException {
        log.debug("REST request to update Bailleur : {}, {}", id, bailleur);
        if (bailleur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bailleur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bailleurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bailleur result = bailleurService.save(bailleur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bailleur.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bailleurs/:id} : Partial updates given fields of an existing bailleur, field will ignore if it is null
     *
     * @param id the id of the bailleur to save.
     * @param bailleur the bailleur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bailleur,
     * or with status {@code 400 (Bad Request)} if the bailleur is not valid,
     * or with status {@code 404 (Not Found)} if the bailleur is not found,
     * or with status {@code 500 (Internal Server Error)} if the bailleur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bailleurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bailleur> partialUpdateBailleur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Bailleur bailleur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bailleur partially : {}, {}", id, bailleur);
        if (bailleur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bailleur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bailleurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bailleur> result = bailleurService.partialUpdate(bailleur);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bailleur.getId().toString())
        );
    }

    /**
     * {@code GET  /bailleurs} : get all the bailleurs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bailleurs in body.
     */
    @GetMapping("/bailleurs")
    public ResponseEntity<List<Bailleur>> getAllBailleurs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Bailleurs");
        Page<Bailleur> page;
        if (eagerload) {
            page = bailleurService.findAllWithEagerRelationships(pageable);
        } else {
            page = bailleurService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bailleurs/:id} : get the "id" bailleur.
     *
     * @param id the id of the bailleur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bailleur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bailleurs/{id}")
    public ResponseEntity<Bailleur> getBailleur(@PathVariable Long id) {
        log.debug("REST request to get Bailleur : {}", id);
        Optional<Bailleur> bailleur = bailleurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bailleur);
    }

    /**
     * {@code DELETE  /bailleurs/:id} : delete the "id" bailleur.
     *
     * @param id the id of the bailleur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bailleurs/{id}")
    public ResponseEntity<Void> deleteBailleur(@PathVariable Long id) {
        log.debug("REST request to delete Bailleur : {}", id);
        bailleurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
