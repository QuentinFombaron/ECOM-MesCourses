package ecom.mescourses.web.rest;

import com.codahale.metrics.annotation.Timed;
import ecom.mescourses.domain.CheckPoint;
import ecom.mescourses.repository.CheckPointRepository;
import ecom.mescourses.repository.search.CheckPointSearchRepository;
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
 * REST controller for managing CheckPoint.
 */
@RestController
@RequestMapping("/api")
public class CheckPointResource {

    private final Logger log = LoggerFactory.getLogger(CheckPointResource.class);

    private static final String ENTITY_NAME = "checkPoint";

    private final CheckPointRepository checkPointRepository;

    private final CheckPointSearchRepository checkPointSearchRepository;

    public CheckPointResource(CheckPointRepository checkPointRepository, CheckPointSearchRepository checkPointSearchRepository) {
        this.checkPointRepository = checkPointRepository;
        this.checkPointSearchRepository = checkPointSearchRepository;
    }

    /**
     * POST  /check-points : Create a new checkPoint.
     *
     * @param checkPoint the checkPoint to create
     * @return the ResponseEntity with status 201 (Created) and with body the new checkPoint, or with status 400 (Bad Request) if the checkPoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/check-points")
    @Timed
    public ResponseEntity<CheckPoint> createCheckPoint(@RequestBody CheckPoint checkPoint) throws URISyntaxException {
        log.debug("REST request to save CheckPoint : {}", checkPoint);
        if (checkPoint.getId() != null) {
            throw new BadRequestAlertException("A new checkPoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckPoint result = checkPointRepository.save(checkPoint);
        checkPointSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/check-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /check-points : Updates an existing checkPoint.
     *
     * @param checkPoint the checkPoint to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated checkPoint,
     * or with status 400 (Bad Request) if the checkPoint is not valid,
     * or with status 500 (Internal Server Error) if the checkPoint couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/check-points")
    @Timed
    public ResponseEntity<CheckPoint> updateCheckPoint(@RequestBody CheckPoint checkPoint) throws URISyntaxException {
        log.debug("REST request to update CheckPoint : {}", checkPoint);
        if (checkPoint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckPoint result = checkPointRepository.save(checkPoint);
        checkPointSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, checkPoint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /check-points : get all the checkPoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of checkPoints in body
     */
    @GetMapping("/check-points")
    @Timed
    public List<CheckPoint> getAllCheckPoints() {
        log.debug("REST request to get all CheckPoints");
        return checkPointRepository.findAll();
    }

    /**
     * GET  /check-points/:id : get the "id" checkPoint.
     *
     * @param id the id of the checkPoint to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the checkPoint, or with status 404 (Not Found)
     */
    @GetMapping("/check-points/{id}")
    @Timed
    public ResponseEntity<CheckPoint> getCheckPoint(@PathVariable Long id) {
        log.debug("REST request to get CheckPoint : {}", id);
        Optional<CheckPoint> checkPoint = checkPointRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(checkPoint);
    }

    /**
     * DELETE  /check-points/:id : delete the "id" checkPoint.
     *
     * @param id the id of the checkPoint to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/check-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteCheckPoint(@PathVariable Long id) {
        log.debug("REST request to delete CheckPoint : {}", id);

        checkPointRepository.deleteById(id);
        checkPointSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/check-points?query=:query : search for the checkPoint corresponding
     * to the query.
     *
     * @param query the query of the checkPoint search
     * @return the result of the search
     */
    @GetMapping("/_search/check-points")
    @Timed
    public List<CheckPoint> searchCheckPoints(@RequestParam String query) {
        log.debug("REST request to search CheckPoints for query {}", query);
        return StreamSupport
            .stream(checkPointSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
