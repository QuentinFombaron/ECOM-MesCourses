package ecom.mescourses.repository.search;

import ecom.mescourses.domain.CheckPoint;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CheckPoint entity.
 */
public interface CheckPointSearchRepository extends ElasticsearchRepository<CheckPoint, Long> {
}
