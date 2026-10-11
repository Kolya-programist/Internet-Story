package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderItem;
import com.example.Internet.Story.entity.Product;
import com.example.Internet.Story.exception.OrderItemNotFoundException;
import com.example.Internet.Story.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
//Найти все позиции
    public List<OrderItem> getAllOrderItem(){
        return orderItemRepository.findAll();
    }
//Найти позицию по ID
    public OrderItem getOrderItemById(Long id){
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
    }
//Найти все позиции по возрастанию Id
    public List<OrderItem> getAllOrderItemByIdIncrease(){
        return orderItemRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(OrderItem::getId))
                .collect(Collectors.toList());

    }
//Найти все позиции по убыванию Id
    public List<OrderItem> getAllOrderItemByIdDecline(){
        return orderItemRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(OrderItem::getId).reversed())
                .collect(Collectors.toList());
    }
//Найти позиции по заказу
    public List<OrderItem> getOrderItemByOrder(Order order){
        return orderItemRepository.findByOrder(order);
    }

//Найти позиции по продукту
    public List<OrderItem> getOrderItemByProduct(Product product){
        return orderItemRepository.findByProduct(product);
    }

//Загрузить позицию
    @Transactional
    public OrderItem postOrderItem(OrderItem orderItem){
        OrderItem saved = orderItemRepository.save(orderItem);
        log.info("Позиция {} загружен", saved.getId());
        return saved;
    }

//Обновить позицию
    @Transactional
    public OrderItem putOrderItem(Long id, OrderItem orderItem){
        OrderItem existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
        existing.setProduct(orderItem.getProduct());
        existing.setOrder(orderItem.getOrder());
        existing.setPrice(orderItem.getPrice());
        existing.setQuantity(orderItem.getQuantity());
        orderItemRepository.save(existing);
        log.info("Позиция {} обновлен", existing.getId());
        return existing;
    }

    //Удалить позицию
    @Transactional
    public void deleteOrderItem(Long id){
        orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
        orderItemRepository.deleteById(id);
        log.info("Позиция {} удален", id);
    }
}
