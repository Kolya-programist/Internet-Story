package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderItem;
import com.example.Internet.Story.entity.Product;
import com.example.Internet.Story.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    public void getAllOrderItem(){
        OrderItem o1 = new OrderItem();
        o1.setId(1L);

        OrderItem o2 = new OrderItem();
        o2.setId(2L);

        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<OrderItem> result = orderItemService.getAllOrderItem();

        assertEquals(2, result.size());
    }

    @Test
    public void getOrderItemById(){
        OrderItem o1 = new OrderItem();
        o1.setId(1L);

        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(o1));

        OrderItem result = orderItemService.getOrderItemById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void getAllOrderItemByIdIncrease(){
        OrderItem o1 = new OrderItem();
        o1.setId(1L);

        OrderItem o2 = new OrderItem();
        o2.setId(2L);

        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<Long> result = orderItemService.getAllOrderItemByIdIncrease()
                .stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L, 2L), result);
    }

    @Test
    public void getAllOrderItemByIdDecline(){
        OrderItem o1 = new OrderItem();
        o1.setId(1L);

        OrderItem o2 = new OrderItem();
        o2.setId(2L);

        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(o2, o1));

        List<Long> result = orderItemService.getAllOrderItemByIdDecline()
                .stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(2L, 1L), result);
    }

    @Test
    public void getOrderItemByOrder(){
        Order order = new Order();
        order.setId(1L);

        OrderItem o1 = new OrderItem();
        o1.setId(10L);
        o1.setOrder(order);

        when(orderItemRepository.findByOrder(order)).thenReturn(Arrays.asList(o1));

        List<Long> result = orderItemService.getOrderItemByOrder(order)
                .stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(10L), result);

    }

    @Test
    public void getOrderItemByProduct(){
        Product p1 = new Product();
        p1.setId(1L);

        OrderItem o1 = new OrderItem();
        o1.setId(10L);

        when(orderItemRepository.findByProduct(p1)).thenReturn(Arrays.asList(o1));

        List <Long> result = orderItemService.getOrderItemByProduct(p1)
                .stream()
                .map(OrderItem::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(10L), result);
    }

    @Test
    public void postOrderItem(){
        OrderItem o1 = new OrderItem();
        o1.setId(1L);

        when(orderItemRepository.save(o1)).thenReturn(o1);

        OrderItem result = orderItemService.postOrderItem(o1);

        assertEquals(1L, result.getId());
        verify(orderItemRepository).save(o1);
    }

    @Test
    public void putOrderItem(){
        OrderItem existing = new OrderItem();
        existing.setId(1L);
        existing.setPrice(new BigDecimal("67"));
        existing.setQuantity(52);

        OrderItem upDate = new OrderItem();
        upDate.setPrice(new BigDecimal("6767"));

        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(orderItemRepository.save(existing)).thenReturn(existing);

        OrderItem result = orderItemService.putOrderItem(1L, upDate);

        assertNotNull(result);
        assertEquals(new BigDecimal("6767"), result.getPrice());
        verify(orderItemRepository).save(existing);
    }

    @Test
    public void deleteOrderItem(){
        OrderItem o1 = new OrderItem();
        o1.setId(1L);

        OrderItem o2 = new OrderItem();
        o2.setId(2L);

        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(o1));

        orderItemService.deleteOrderItem(1L);

        verify(orderItemRepository).deleteById(1L);
    }
}
