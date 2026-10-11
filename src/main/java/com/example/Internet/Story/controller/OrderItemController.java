package com.example.Internet.Story.controller;

import com.example.Internet.Story.dto.OrderItemDto;
import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderItem;
import com.example.Internet.Story.entity.Product;
import com.example.Internet.Story.mapper.OrderItemMapper;
import com.example.Internet.Story.service.OrderItemService;
import com.example.Internet.Story.service.OrderService;
import com.example.Internet.Story.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemController(OrderItemService orderItemService, OrderItemMapper orderItemMapper, OrderService orderService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderItemMapper = orderItemMapper;
        this.orderService = orderService;
        this.productService = productService;
    }
//Найти все позиции
    @GetMapping
    public List<OrderItemDto> getAll(){
        return orderItemService.getAllOrderItem()
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

//Найти позицю по Id
    @GetMapping("/id/{id}")
    public OrderItemDto getById(@PathVariable Long id){
       return orderItemMapper.toDto(orderItemService.getOrderItemById(id));
    }

//Найти все позиции по возрастанию Id
    @GetMapping("/id/increase")
    public List<OrderItemDto> getAllOrderItemByIdIncrease(){
        return orderItemService.getAllOrderItemByIdIncrease()
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти все позиции по возрастанию Id
    @GetMapping("/id/decline")
    public List<OrderItemDto> getAllOrderItemByIdDecline(){
        return orderItemService.getAllOrderItemByIdDecline()
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти позицию по заказу
    @GetMapping("/order/{id}")
    public List<OrderItemDto> getOrderItemByOrder(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        return orderItemService.getOrderItemByOrder(order)
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти позицию по продукту
    @GetMapping("/product/{id}")
    public List<OrderItemDto> getOrderItemByProduct(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return orderItemService.getOrderItemByProduct(product)
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }
//Загрузить позицию
    @PostMapping
    public OrderItemDto post(@Valid @RequestBody OrderItemDto orderItemDto){
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
        OrderItem save = orderItemService.postOrderItem(orderItem);
        return orderItemMapper.toDto(save);
    }
//Обновить позицию
    @PutMapping("/{id}")
    public OrderItemDto put(@PathVariable Long id, @Valid @RequestBody OrderItemDto orderItemDto){
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
        OrderItem save = orderItemService.putOrderItem(id, orderItem);
        return orderItemMapper.toDto(save);
    }
//Удалить позицию
    @DeleteMapping("/{id}")
    public OrderItemDto delete(@PathVariable Long id){
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        orderItemService.deleteOrderItem(id);
        return orderItemMapper.toDto(orderItem);
    }
}
