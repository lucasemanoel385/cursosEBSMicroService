package br.com.cursosEBS.courses.controller;

import br.com.cursosEBS.courses.dto.CategoryDTO;
import br.com.cursosEBS.courses.dto.PageDTO;
import br.com.cursosEBS.courses.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDTO> registerCategory(@RequestBody @Valid CategoryDTO dto, UriComponentsBuilder uriBuilder) {
        var category = service.registerCategory(dto);
        URI uri = uriBuilder.path("/category/{id}").buildAndExpand(category.id()).toUri();

        return ResponseEntity.created(uri).body(category);
    }

    @GetMapping
    public ResponseEntity<PageDTO<CategoryDTO>> listCategory(@PageableDefault(page = 0, size = 7) Pageable pageable,
                                                             @RequestParam(required = false) String search){
        return ResponseEntity.ok(service.listCategory(pageable, search));
    }

    @GetMapping("/filter")
    public ResponseEntity<PageDTO<CategoryDTO>> listCategoryWithParam(@PageableDefault(size = 100) Pageable pageable,
                                                             @RequestParam String search){
        return ResponseEntity.ok(service.listCategoryWithParam(pageable, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable @NotNull Long id){
        return ResponseEntity.ok(service.findCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable @NotNull Long id, @RequestBody @Valid CategoryDTO dto){
        return ResponseEntity.ok(service.updateCategory(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable @NotNull Long id){
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
