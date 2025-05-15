package br.com.cursosEBS.courses.repository;

import br.com.cursosEBS.courses.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoursesRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE CAST(c.id AS string) = :search% or c.title LIKE :search%")
    Page<Course> findAllLikeSearch(Pageable page, String search);
    @Query("SELECT c FROM Course c JOIN c.category ca WHERE ca.name = :category")
    Page<Course> findAllLikeCategory(Pageable pageable, String category);
    @Query("SELECT c FROM Course c JOIN c.category ca WHERE c.title LIKE CONCAT('%', :search, '%') AND ca.name = :category")
    Page<Course> findAllLikeSearchAndCategory(Pageable pageable, String search, String category);
}
