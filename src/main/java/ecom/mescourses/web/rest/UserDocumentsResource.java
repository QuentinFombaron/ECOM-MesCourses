package ecom.mescourses.web.rest;

import com.codahale.metrics.annotation.Timed;
import ecom.mescourses.domain.UserDocuments;
import ecom.mescourses.repository.UserDocumentsRepository;
import ecom.mescourses.repository.search.UserDocumentsSearchRepository;
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
 * REST controller for managing UserDocuments.
 */
@RestController
@RequestMapping("/api")
public class UserDocumentsResource {

    private final Logger log = LoggerFactory.getLogger(UserDocumentsResource.class);

    private static final String ENTITY_NAME = "userDocuments";

    private final UserDocumentsRepository userDocumentsRepository;

    private final UserDocumentsSearchRepository userDocumentsSearchRepository;

    public UserDocumentsResource(UserDocumentsRepository userDocumentsRepository, UserDocumentsSearchRepository userDocumentsSearchRepository) {
        this.userDocumentsRepository = userDocumentsRepository;
        this.userDocumentsSearchRepository = userDocumentsSearchRepository;
    }

    /**
     * POST  /user-documents : Create a new userDocuments.
     *
     * @param userDocuments the userDocuments to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDocuments, or with status 400 (Bad Request) if the userDocuments has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-documents")
    @Timed
    public ResponseEntity<UserDocuments> createUserDocuments(@RequestBody UserDocuments userDocuments) throws URISyntaxException {
        log.debug("REST request to save UserDocuments : {}", userDocuments);
        if (userDocuments.getId() != null) {
            throw new BadRequestAlertException("A new userDocuments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDocuments result = userDocumentsRepository.save(userDocuments);
        userDocumentsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-documents : Updates an existing userDocuments.
     *
     * @param userDocuments the userDocuments to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userDocuments,
     * or with status 400 (Bad Request) if the userDocuments is not valid,
     * or with status 500 (Internal Server Error) if the userDocuments couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-documents")
    @Timed
    public ResponseEntity<UserDocuments> updateUserDocuments(@RequestBody UserDocuments userDocuments) throws URISyntaxException {
        log.debug("REST request to update UserDocuments : {}", userDocuments);
        if (userDocuments.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDocuments result = userDocumentsRepository.save(userDocuments);
        userDocumentsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userDocuments.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-documents : get all the userDocuments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userDocuments in body
     */
    @GetMapping("/user-documents")
    @Timed
    public List<UserDocuments> getAllUserDocuments() {
        log.debug("REST request to get all UserDocuments");
        return userDocumentsRepository.findAll();
    }

    /**
     * GET  /user-documents/:id : get the "id" userDocuments.
     *
     * @param id the id of the userDocuments to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userDocuments, or with status 404 (Not Found)
     */
    @GetMapping("/user-documents/{id}")
    @Timed
    public ResponseEntity<UserDocuments> getUserDocuments(@PathVariable Long id) {
        log.debug("REST request to get UserDocuments : {}", id);
        Optional<UserDocuments> userDocuments = userDocumentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userDocuments);
    }

    /**
     * DELETE  /user-documents/:id : delete the "id" userDocuments.
     *
     * @param id the id of the userDocuments to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-documents/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserDocuments(@PathVariable Long id) {
        log.debug("REST request to delete UserDocuments : {}", id);

        userDocumentsRepository.deleteById(id);
        userDocumentsSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-documents?query=:query : search for the userDocuments corresponding
     * to the query.
     *
     * @param query the query of the userDocuments search
     * @return the result of the search
     */
    @GetMapping("/_search/user-documents")
    @Timed
    public List<UserDocuments> searchUserDocuments(@RequestParam String query) {
        log.debug("REST request to search UserDocuments for query {}", query);
        return StreamSupport
            .stream(userDocumentsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
