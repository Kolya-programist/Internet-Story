package com.example.Internet.Story.dto;

import com.example.Internet.Story.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private OrderStatus status;
    @NotNull
    private Long customerId;
    @NotNull
    private List<Long> ordersIds;
}
