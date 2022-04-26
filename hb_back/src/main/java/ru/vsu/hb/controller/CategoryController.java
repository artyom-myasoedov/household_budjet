package ru.vsu.hb.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vsu.hb.dto.CategoryDto;
import ru.vsu.hb.dto.response.HBResponseData;
import ru.vsu.hb.service.CategoryService;
import ru.vsu.hb.utils.HBResponseBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.security.Principal;
import java.util.UUID;


@PreAuthorize("hasAnyAuthority('USER')")
@RestController
@RequestMapping(value = "/category", produces = {"application/json"})
@Api(description = "Категория расходов")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    @ApiOperation(value = "Добавление категории расходов к пользователю")
    public ResponseEntity<? extends HBResponseData<? extends CategoryDto>> addCategory(
            @RequestBody CategoryDto category, @ApiIgnore Principal principal) {
        return HBResponseBuilder.fromHBResult(service.addCategory(category, principal.getName())).build();
    }

    @PutMapping
    @ApiOperation(value = "Обновление категории расходов пользователя")
    public ResponseEntity<? extends HBResponseData<? extends CategoryDto>> updateCategory(
            @ApiIgnore Principal principal, @RequestBody CategoryDto category) {
        return HBResponseBuilder.fromHBResult(service.updateCategory(category, principal.getName())).build();
    }

    @DeleteMapping
    @ApiOperation(value = "Удаление категории расходов у пользователя")
    public ResponseEntity<? extends HBResponseData<? extends Integer>> removeCategory(
            @ApiIgnore Principal principal, @RequestParam @ApiParam(value = "Идентификатор категории") UUID categoryId) {
        return HBResponseBuilder
                .fromHBResult(service.deleteByCategoryId(categoryId, principal.getName())).build();
    }

    @GetMapping("/{categoryId}")
    @ApiOperation(value = "Получение категории по id")
    public ResponseEntity<? extends HBResponseData<? extends CategoryDto>> getCategoryById(
            @ApiIgnore Principal principal, @PathVariable @ApiParam(value = "Идентификатор категории") UUID categoryId) {
        return HBResponseBuilder
                .fromHBResult(service.getByUserCategoryId(principal.getName(), categoryId))
                .build();
    }

    @GetMapping
    @ApiOperation(value = "Получение списка категорий для пользователя")
    public ResponseEntity<? extends HBResponseData<? extends List<CategoryDto>>> getAllCategories(
            @ApiIgnore Principal principal) {
        return HBResponseBuilder.fromHBResult(service.getAll(principal.getName()))
                .build();
    }


}
