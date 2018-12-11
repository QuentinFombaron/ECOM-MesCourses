package ecom.mescourses.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of InscriptionSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InscriptionSearchRepositoryMockConfiguration {

    @MockBean
    private InscriptionSearchRepository mockInscriptionSearchRepository;

}
