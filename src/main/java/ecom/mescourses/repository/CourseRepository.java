package ecom.mescourses.repository;

import ecom.mescourses.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "select distinct course from Course course left join fetch course.participants",
        countQuery = "select count(distinct course) from Course course")
    Page<Course> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct course from Course course left join fetch course.participants")
    List<Course> findAllWithEagerRelationships();

    @Query("select course from Course course left join fetch course.participants where course.id =:id")
    Optional<Course> findOneWithEagerRelationships(@Param("id") Long id);

}
