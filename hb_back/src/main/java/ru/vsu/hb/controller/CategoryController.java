package ru.vsu.hb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.service.CategoryService;
import ru.vsu.hb.utils.HBResponseBuilder;

import java.util.List;
import java.security.Principal;
import java.util.UUID;


@PreAuthorize("hasAnyAuthority('USER')")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public ResponseEntity<? super HBResponseData<? super CategoryDto>> addCategory(
            @RequestBody CategoryDto category, Principal principal) {
        return HBResponseBuilder.fromHBResult(service.addCategory(category, principal.getName())).build();
    }

    @PutMapping
    public ResponseEntity<? super HBResponseData<? super CategoryDto>> updateCategory(
            Principal principal, @RequestBody CategoryDto category) {
        return HBResponseBuilder.fromHBResult(service.updateCategory(category, principal.getName())).build();
    }

    @DeleteMapping
    public ResponseEntity<? super HBResponseData<? super Integer>> removeCategory(
            Principal principal, @RequestParam UUID categoryId) {
        return HBResponseBuilder
                .fromHBResult(service.deleteByCategoryId(categoryId, principal.getName())).build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<? super HBResponseData<? super CategoryDto>> getCategoryById(
            Principal principal, @PathVariable UUID categoryId) {
        return HBResponseBuilder
                .fromHBResult(service.getByUserCategoryId(principal.getName(), categoryId)
                        .mapSuccess(CategoryDto::fromEntity))
                .build();
    }

    @GetMapping
    public ResponseEntity<? super HBResponseData<? super List<CategoryDto>>> getAllCategories(
            Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getAll(principal.getName()))
                .build();
    }


}
