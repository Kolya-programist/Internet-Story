package com.example.Internet.Story.mapper;

import com.example.Internet.Story.dto.ProductDto;
import com.example.Internet.Story.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDto productDto);
}