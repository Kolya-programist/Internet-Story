package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderStatus;
import com.example.Internet.Story.exception.CustomerNotFoundException;
import com.example.Internet.Story.exception.OrderEmptyException;
import com.example.Internet.Story.exception.OrderNotFoundException;
import com.example.Internet.Story.repository.CategoryRepository;
import com.example.Internet.Story.repository.CustomerRepository;
import com.example.Internet.Story.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    public OrderService(OrderRepository orderRepository, CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

//Найти все заказы
    public List<Order> getAll(){
        return orderRepository.findAll();
    }

//Найти все заказы по дате
    public List<Order> getAllByOrdersLocalDateTime(LocalDateTime date){
        return orderRepository.findAll()
                .stream()
                .filter(f -> date.equals(f.getDate()))
                .collect(Collectors.toUnmodifiableList());
    }

//Найти все заказы по возрастанию даты оформления заказа
    public List<Order> getLAllByOrdersLocalDateTimeIncrease(){
        return orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getDate))
                .collect(Collectors.toList());
    }

//Найти все заказы по убыванию даты оформления заказа
    public List<Order> getAllByOrdersLocalDateTimeDecline(){
        return orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

//Найти первый заказ
    public Order getFirstOrder(){
        return orderRepository.findAll()
                .stream()
                .min(Comparator.comparing(Order::getDate))
                .orElseThrow(() -> new OrderEmptyException());
    }

//Найти последний заказ
    public Order getLastOrder(){
        return orderRepository.findAll()
                .stream()
                .max(Comparator.comparing(Order::getDate))
                .orElseThrow(() -> new OrderEmptyException());
    }

//Найти заказ по ID
    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

//Найти все заказы по возрастанию Id
public  List<Order> getAllOrderItemByIdIncrease(){
    return orderRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Order::getId))
            .collect(Collectors.toUnmodifiableList());
}

//Найти все заказы по убыванию Id
    public  List<Order> getAllOrderItemByIdDecline(){
        return orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getId).reversed())
                .collect(Collectors.toUnmodifiableList());
    }

//Найти заказы по покупателю
    public List<Order> getOrderByCustomer(Customer customer){
        return orderRepository.findByCustomer(customer);
    }

//Найти заказы по статусу
    public List<Order> getOrderByStatus(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

//Загрузить заказ
   @Transactional
    public Order postOrder(Order order){
        if(order.getCustomer() != null){
            Customer customer = customerRepository.findById(order.getCustomer().getId())
                    .orElseThrow(() -> new CustomerNotFoundException(order.getCustomer().getId()));
            order.setCustomer(customer);
        }
        Order saved = orderRepository.save(order);
       log.info("Заказ {} сохранен", saved.getId());
        return saved;
    }

//Обновить заказ
    @Transactional
    public Order putOrder(Long id, Order order){
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if(order.getCustomer() != null){
            Customer customer = customerRepository.findById(order.getCustomer().getId())
                    .orElseThrow(() -> new CustomerNotFoundException(order.getCustomer().getId()));
            existing.setCustomer(customer);
        }
        existing.setDate(order.getDate());
        existing.setStatus(order.getStatus());
        orderRepository.save(existing);
        log.info("Заказ {} обновлен", existing.getId());
        return existing;
    }

//Удалить заказ
    @Transactional
    public void deleteOrder(Long id){
        orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.deleteById(id);
        log.info("Заказ {} удален", id);
    }
}
