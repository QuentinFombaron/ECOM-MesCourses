package ecom.mescourses.repository.search;

import ecom.mescourses.domain.Inscription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Inscription entity.
 */
public interface InscriptionSearchRepository extends ElasticsearchRepository<Inscription, Long> {
}
