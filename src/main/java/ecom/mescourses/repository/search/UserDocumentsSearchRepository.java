package ecom.mescourses.repository.search;

import ecom.mescourses.domain.UserDocuments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserDocuments entity.
 */
public interface UserDocumentsSearchRepository extends ElasticsearchRepository<UserDocuments, Long> {
}
