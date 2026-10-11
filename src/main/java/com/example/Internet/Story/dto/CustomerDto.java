package com.example.Internet.Story.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private List<Long> ordersIds;
}
