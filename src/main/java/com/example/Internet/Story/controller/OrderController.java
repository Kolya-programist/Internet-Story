package com.example.Internet.Story.controller;

import com.example.Internet.Story.dto.OrderDto;
import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderStatus;
import com.example.Internet.Story.mapper.OrderMapper;
import com.example.Internet.Story.service.CustomerService;
import com.example.Internet.Story.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, OrderMapper orderMapper, CustomerService customerService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.customerService = customerService;
    }

//Нати все заказы
    @GetMapping
    public List<OrderDto> getAll(){
        return orderService.getAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

//Найти заказ по дате
    @GetMapping("/date/{date}")
    public List<OrderDto> getAllByOrdersLocalDateTime(@PathVariable LocalDateTime date){
        return orderService.getAllByOrdersLocalDateTime(date)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти все заказы по возрастанию даты оформления заказа
    @GetMapping("/date/increase")
    public List<OrderDto> getLAllByOrdersLocalDateTimeIncrease(){
        return orderService.getLAllByOrdersLocalDateTimeIncrease()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти все заказы по убыванию даты оформления заказа
    @GetMapping("/date/decline")
    public List<OrderDto> getAllByOrdersLocalDateTimeDecline(){
        return orderService.getAllByOrdersLocalDateTimeDecline()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

//Найти первый заказ
    @GetMapping("/date/first")
    public OrderDto getFirstOrder(){
        return orderMapper.toDto(orderService.getFirstOrder());
    }
//Найти последний заказ
    @GetMapping("/date/last")
    public OrderDto getLastOrder(){
        return  orderMapper.toDto(orderService.getLastOrder());
    }
//Найти заказы по id
    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable Long id){
        return orderMapper.toDto(orderService.getOrderById(id));
    }

//Найти все заказы по возрастанию Id
    @GetMapping("/id/increase")
    public List<OrderDto> getAllOrderItemByIdIncrease(){
        return orderService.getAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти все заказы по убыванию Id
    @GetMapping("/id/decline")
    public List<OrderDto> getAllOrderItemByIdDecline(){
        return orderService.getAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти заказы по пользователю
    @GetMapping("/customer/{id}")
    public List<OrderDto> getOrderByCustomer(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        return orderService.getOrderByCustomer(customer)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти заказы по статусу
    @GetMapping("/status/{status}")
    public List<OrderDto> getByStatus(@PathVariable OrderStatus status){
        return orderService.getOrderByStatus(status)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
//Загрузить заказ
    @PostMapping
    public OrderDto post(@RequestBody OrderDto orderDto){
        Order order = orderMapper.toEntity(orderDto);
        Order save = orderService.postOrder(order);
        return orderMapper.toDto(save);
    }
//Обновить заказ
    @PutMapping("/{id}")
    public OrderDto put(@PathVariable Long id, @RequestBody OrderDto orderDto){
        Order order = orderMapper.toEntity(orderDto);
        Order save = orderService.putOrder(id, order);
        return orderMapper.toDto(save);
    }
//Удалить заказ
    @DeleteMapping("/{id}")
    public OrderDto delete(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        orderService.deleteOrder(id);
        return orderMapper.toDto(order);
    }
}
