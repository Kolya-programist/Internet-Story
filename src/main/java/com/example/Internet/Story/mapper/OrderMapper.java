package com.example.Internet.Story.mapper;

import com.example.Internet.Story.dto.OrderDto;
import com.example.Internet.Story.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "customer.id", target = "customerId")
    OrderDto toDto(Order order);
    @Mapping(source = "customerId", target = "customer.id")
    Order toEntity(OrderDto orderDto);
}
