package com.example.Internet.Story.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CustomerDto {
    private Long id;

    private String role;

    private String name;

    private String email;

    private String password;

    private String address;

    private List<Long> ordersIds;
}