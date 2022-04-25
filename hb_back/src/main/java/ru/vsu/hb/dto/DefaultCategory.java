package ru.vsu.hb.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public enum DefaultCategory {
    FOOD("Еда", UUID.fromString("88f45e2e-c470-11ec-9d64-0242ac120002")),
    AUTO("Транспорт", UUID.fromString("88f462ac-c470-11ec-9d64-0242ac120002")),
    CLOTHERS("Одежда", UUID.fromString("88f46414-c470-11ec-9d64-0242ac120002")),
    HEALTH("Здоровье", UUID.fromString("88f466f8-c470-11ec-9d64-0242ac120002")),
    HOUSE("Дом", UUID.fromString("88f46842-c470-11ec-9d64-0242ac120002")),
    GOING_OUT("Развлечения", UUID.fromString("88f4696e-c470-11ec-9d64-0242ac120002")),
    ;

    private String name;
    private UUID categoryId;

    DefaultCategory(String name, UUID categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public static List<CategoryDto> getDefaultCategories(){
        List<CategoryDto> list = new ArrayList<>();
        list.add(FOOD.toDto());
        list.add(AUTO.toDto());
        list.add(CLOTHERS.toDto());
        list.add(HEALTH.toDto());
        list.add(HOUSE.toDto());
        list.add(GOING_OUT.toDto());
        return list;
    }

    private CategoryDto toDto(){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(this.getCategoryId());
        categoryDto.setCategoryName(this.getName());
        categoryDto.setDefault(true);
        categoryDto.setOutSumLastMonth(BigDecimal.valueOf(0));
        return  categoryDto;
    }

    public String getName() {
        return name;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
}
