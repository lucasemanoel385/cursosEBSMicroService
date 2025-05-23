package br.com.cursosEBS.courses.repository;

import br.com.cursosEBS.courses.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE CAST(c.id AS string) = :search% or c.name LIKE :search%")
    Page<Category> findAllLikeSearchOrCod(Pageable page, String search);

    @Query("SELECT c FROM Category c WHERE c.name LIKE :search%")
    Page<Category> findAllLikeSearch(Pageable page, String search);

}
