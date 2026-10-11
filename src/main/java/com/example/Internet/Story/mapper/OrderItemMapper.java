package com.example.Internet.Story.mapper;

import com.example.Internet.Story.dto.OrderItemDto;
import com.example.Internet.Story.entity.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);
    OrderItem toEntity(OrderItemDto orderItemDto);
}
