package br.com.cursosEBS.courses.service;

import br.com.cursosEBS.courses.dto.CategoryDTO;
import br.com.cursosEBS.courses.dto.PageDTO;
import br.com.cursosEBS.courses.entity.Category;
import br.com.cursosEBS.courses.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    public CategoryDTO registerCategory(CategoryDTO dto) {
        var category = new Category(dto);
        repository.save(category);
        return new CategoryDTO(category);
    }

    public PageDTO<CategoryDTO> listCategory(Pageable pageable, String search) {
        Page<CategoryDTO> page = search == null || search.isEmpty() ? repository.findAll(pageable).map(CategoryDTO::new) : repository.findAllLikeSearchOrCod(pageable, search).map(CategoryDTO::new);
        return new PageDTO<>(page);
    }

    public CategoryDTO findCategoryById(Long id) {
        return new CategoryDTO(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {

        var category = new Category(dto);
        category.setId(id);
        repository.save(category);
        return new CategoryDTO(category);
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    public PageDTO<CategoryDTO> listCategoryWithParam(Pageable pageable, String search) {
        Page<CategoryDTO> page = repository.findAllLikeSearch(pageable, search).map(CategoryDTO::new);
        return new PageDTO<>(page);
    }
}
