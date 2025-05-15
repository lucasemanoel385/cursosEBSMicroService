package br.com.cursosEBS.courses.repository;

import br.com.cursosEBS.courses.entity.Course;
import br.com.cursosEBS.courses.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l FROM Lesson l WHERE CAST(l.id AS string) = :search% or l.title LIKE :search%")
    Page<Lesson> findAllLikeSearch(Pageable page, String search);

    List<Lesson> findByCourseId_Id(Long id);
}
