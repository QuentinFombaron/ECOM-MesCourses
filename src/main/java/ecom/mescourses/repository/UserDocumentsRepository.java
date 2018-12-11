package ecom.mescourses.repository;

import ecom.mescourses.domain.UserDocuments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserDocuments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDocumentsRepository extends JpaRepository<UserDocuments, Long> {

}
