package com.example.Internet.Story.mapper;

import com.example.Internet.Story.dto.OrderDto;
import com.example.Internet.Story.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}
