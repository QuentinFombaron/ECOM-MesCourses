package ecom.mescourses.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of UserDocumentsSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UserDocumentsSearchRepositoryMockConfiguration {

    @MockBean
    private UserDocumentsSearchRepository mockUserDocumentsSearchRepository;

}
