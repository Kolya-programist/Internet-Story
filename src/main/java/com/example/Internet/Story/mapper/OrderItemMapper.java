package com.example.Internet.Story.mapper;

import com.example.Internet.Story.dto.OrderItemDto;
import com.example.Internet.Story.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    OrderItemDto toDto(OrderItem orderItem);


    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(OrderItemDto orderItemDto);
}
