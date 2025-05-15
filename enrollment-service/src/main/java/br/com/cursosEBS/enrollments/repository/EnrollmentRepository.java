package br.com.cursosEBS.enrollments.repository;

import br.com.cursosEBS.enrollments.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("""
       SELECT COUNT(e) > 0 FROM Enrollment e WHERE e.userId = :userId AND e.courseId = :enrollId
       """)
    Boolean existsEnrollmentWithUserIdAndEnrollId(Long userId, Long enrollId);
}
