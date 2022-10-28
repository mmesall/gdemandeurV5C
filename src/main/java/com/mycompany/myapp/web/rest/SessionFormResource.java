package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SessionForm;
import com.mycompany.myapp.repository.SessionFormRepository;
import com.mycompany.myapp.service.SessionFormService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SessionForm}.
 */
@RestController
@RequestMapping("/api")
public class SessionFormResource {

    private final Logger log = LoggerFactory.getLogger(SessionFormResource.class);

    private static final String ENTITY_NAME = "sessionForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionFormService sessionFormService;

    private final SessionFormRepository sessionFormRepository;

    public SessionFormResource(SessionFormService sessionFormService, SessionFormRepository sessionFormRepository) {
        this.sessionFormService = sessionFormService;
        this.sessionFormRepository = sessionFormRepository;
    }

    /**
     * {@code POST  /session-forms} : Create a new sessionForm.
     *
     * @param sessionForm the sessionForm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionForm, or with status {@code 400 (Bad Request)} if the sessionForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/session-forms")
    public ResponseEntity<SessionForm> createSessionForm(@RequestBody SessionForm sessionForm) throws URISyntaxException {
        log.debug("REST request to save SessionForm : {}", sessionForm);
        if (sessionForm.getId() != null) {
            throw new BadRequestAlertException("A new sessionForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SessionForm result = sessionFormService.save(sessionForm);
        return ResponseEntity
            .created(new URI("/api/session-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /session-forms/:id} : Updates an existing sessionForm.
     *
     * @param id the id of the sessionForm to save.
     * @param sessionForm the sessionForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionForm,
     * or with status {@code 400 (Bad Request)} if the sessionForm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/session-forms/{id}")
    public ResponseEntity<SessionForm> updateSessionForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SessionForm sessionForm
    ) throws URISyntaxException {
        log.debug("REST request to update SessionForm : {}, {}", id, sessionForm);
        if (sessionForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SessionForm result = sessionFormService.save(sessionForm);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sessionForm.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /session-forms/:id} : Partial updates given fields of an existing sessionForm, field will ignore if it is null
     *
     * @param id the id of the sessionForm to save.
     * @param sessionForm the sessionForm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionForm,
     * or with status {@code 400 (Bad Request)} if the sessionForm is not valid,
     * or with status {@code 404 (Not Found)} if the sessionForm is not found,
     * or with status {@code 500 (Internal Server Error)} if the sessionForm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/session-forms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SessionForm> partialUpdateSessionForm(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SessionForm sessionForm
    ) throws URISyntaxException {
        log.debug("REST request to partial update SessionForm partially : {}, {}", id, sessionForm);
        if (sessionForm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionForm.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionFormRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SessionForm> result = sessionFormService.partialUpdate(sessionForm);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sessionForm.getId().toString())
        );
    }

    /**
     * {@code GET  /session-forms} : get all the sessionForms.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessionForms in body.
     */
    @GetMapping("/session-forms")
    public ResponseEntity<List<SessionForm>> getAllSessionForms(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of SessionForms");
        Page<SessionForm> page;
        if (eagerload) {
            page = sessionFormService.findAllWithEagerRelationships(pageable);
        } else {
            page = sessionFormService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /session-forms/:id} : get the "id" sessionForm.
     *
     * @param id the id of the sessionForm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionForm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/session-forms/{id}")
    public ResponseEntity<SessionForm> getSessionForm(@PathVariable Long id) {
        log.debug("REST request to get SessionForm : {}", id);
        Optional<SessionForm> sessionForm = sessionFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sessionForm);
    }

    /**
     * {@code DELETE  /session-forms/:id} : delete the "id" sessionForm.
     *
     * @param id the id of the sessionForm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/session-forms/{id}")
    public ResponseEntity<Void> deleteSessionForm(@PathVariable Long id) {
        log.debug("REST request to delete SessionForm : {}", id);
        sessionFormService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
