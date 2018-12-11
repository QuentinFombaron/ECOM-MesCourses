package ecom.mescourses.web.rest;

import com.codahale.metrics.annotation.Timed;
import ecom.mescourses.domain.Inscription;
import ecom.mescourses.repository.InscriptionRepository;
import ecom.mescourses.repository.search.InscriptionSearchRepository;
import ecom.mescourses.web.rest.errors.BadRequestAlertException;
import ecom.mescourses.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Inscription.
 */
@RestController
@RequestMapping("/api")
public class InscriptionResource {

    private final Logger log = LoggerFactory.getLogger(InscriptionResource.class);

    private static final String ENTITY_NAME = "inscription";

    private final InscriptionRepository inscriptionRepository;

    private final InscriptionSearchRepository inscriptionSearchRepository;

    public InscriptionResource(InscriptionRepository inscriptionRepository, InscriptionSearchRepository inscriptionSearchRepository) {
        this.inscriptionRepository = inscriptionRepository;
        this.inscriptionSearchRepository = inscriptionSearchRepository;
    }

    /**
     * POST  /inscriptions : Create a new inscription.
     *
     * @param inscription the inscription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inscription, or with status 400 (Bad Request) if the inscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inscriptions")
    @Timed
    public ResponseEntity<Inscription> createInscription(@RequestBody Inscription inscription) throws URISyntaxException {
        log.debug("REST request to save Inscription : {}", inscription);
        if (inscription.getId() != null) {
            throw new BadRequestAlertException("A new inscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inscription result = inscriptionRepository.save(inscription);
        inscriptionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/inscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inscriptions : Updates an existing inscription.
     *
     * @param inscription the inscription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inscription,
     * or with status 400 (Bad Request) if the inscription is not valid,
     * or with status 500 (Internal Server Error) if the inscription couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inscriptions")
    @Timed
    public ResponseEntity<Inscription> updateInscription(@RequestBody Inscription inscription) throws URISyntaxException {
        log.debug("REST request to update Inscription : {}", inscription);
        if (inscription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Inscription result = inscriptionRepository.save(inscription);
        inscriptionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inscription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inscriptions : get all the inscriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inscriptions in body
     */
    @GetMapping("/inscriptions")
    @Timed
    public List<Inscription> getAllInscriptions() {
        log.debug("REST request to get all Inscriptions");
        return inscriptionRepository.findAll();
    }

    /**
     * GET  /inscriptions/:id : get the "id" inscription.
     *
     * @param id the id of the inscription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inscription, or with status 404 (Not Found)
     */
    @GetMapping("/inscriptions/{id}")
    @Timed
    public ResponseEntity<Inscription> getInscription(@PathVariable Long id) {
        log.debug("REST request to get Inscription : {}", id);
        Optional<Inscription> inscription = inscriptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inscription);
    }

    /**
     * DELETE  /inscriptions/:id : delete the "id" inscription.
     *
     * @param id the id of the inscription to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inscriptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteInscription(@PathVariable Long id) {
        log.debug("REST request to delete Inscription : {}", id);

        inscriptionRepository.deleteById(id);
        inscriptionSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/inscriptions?query=:query : search for the inscription corresponding
     * to the query.
     *
     * @param query the query of the inscription search
     * @return the result of the search
     */
    @GetMapping("/_search/inscriptions")
    @Timed
    public List<Inscription> searchInscriptions(@RequestParam String query) {
        log.debug("REST request to search Inscriptions for query {}", query);
        return StreamSupport
            .stream(inscriptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
