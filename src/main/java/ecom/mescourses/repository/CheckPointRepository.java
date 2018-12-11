package ecom.mescourses.repository;

import ecom.mescourses.domain.CheckPoint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CheckPoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {

}
