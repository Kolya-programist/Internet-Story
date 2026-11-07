package com.example.Internet.Story.mapper;

import com.example.Internet.Story.dto.CustomerDto;
import com.example.Internet.Story.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}
