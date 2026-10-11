package com.example.Internet.Story.mapper;

import com.example.Internet.Story.dto.CategoryDto;
import com.example.Internet.Story.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
}
