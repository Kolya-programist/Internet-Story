package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.entity.Order;
import com.example.Internet.Story.entity.OrderStatus;
import com.example.Internet.Story.repository.CustomerRepository;
import com.example.Internet.Story.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static com.example.Internet.Story.entity.OrderStatus.NEW;
import static com.example.Internet.Story.entity.OrderStatus.PAID;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    public void getAll() {
        Order o1 = new Order();
        o1.setId(1L);

        Order o2 = new Order();
        o2.setId(2L);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<Order> result = orderService.getAll();
        assertEquals(2, result.size());
    }

    @Test
    public void getAllByOrdersLocalDateTime() {
        LocalDateTime date = LocalDateTime.of(2022, 2, 24, 4, 0);

        Order o1 = new Order();
        o1.setId(1L);
        o1.setDate(date);

        Order o2 = new Order();
        o2.setId(2L);
        o2.setDate(LocalDateTime.now());

        Order o3 = new Order();
        o3.setId(3L);
        o3.setDate(date);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2, o3));

        List<Long> result = orderService.getAllByOrdersLocalDateTime(date)
                .stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L, 3L), result);
    }

    @Test
    public void getLAllByOrdersLocalDateTimeIncrease() {
        Order o1 = new Order();
        o1.setId(1L);
        o1.setDate(LocalDateTime.of(2022, 2, 2, 2, 2));

        Order o2 = new Order();
        o2.setId(2L);
        o2.setDate(LocalDateTime.of(2023, 2, 2, 2, 2));

        Order o3 = new Order();
        o3.setId(3L);
        o3.setDate(LocalDateTime.of(2024, 2, 2, 2, 2));

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2, o3));

        List<Long> result = orderService.getLAllByOrdersLocalDateTimeIncrease()
                .stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L, 2L, 3L), result);
    }

    @Test
    public void getAllByOrdersLocalDateTimeDecline() {
        Order o1 = new Order();
        o1.setId(1L);
        o1.setDate(LocalDateTime.of(2022, 2, 2, 2, 2));

        Order o2 = new Order();
        o2.setId(2L);
        o2.setDate(LocalDateTime.of(2023, 2, 2, 2, 2));

        Order o3 = new Order();
        o3.setId(3L);
        o3.setDate(LocalDateTime.of(2024, 2, 2, 2, 2));

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2, o3));

        List<Long> result = orderService.getAllByOrdersLocalDateTimeDecline()
                .stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(3L, 2L, 1L), result);
    }

    @Test
    public void getFirstOrder() {
        Order o1 = new Order();
        o1.setId(1L);
        o1.setDate(LocalDateTime.of(2022, 2, 2, 2, 2));

        Order o2 = new Order();
        o2.setId(2L);
        o2.setDate(LocalDateTime.of(2023, 2, 2, 2, 2));

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        Order result = orderService.getFirstOrder();

        assertEquals(1L, result.getId());
    }

    @Test
    public void getLastOrder() {
        Order o1 = new Order();
        o1.setId(1L);
        o1.setDate(LocalDateTime.of(23022, 2, 2, 2, 2));

        Order o2 = new Order();
        o2.setId(2L);
        o2.setDate(LocalDateTime.of(2032, 2, 2, 2, 2));

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        Order result = orderService.getLastOrder();

        assertEquals(1L, result.getId());
    }

    @Test
    public void getOtderById() {
        Order o1 = new Order();
        o1.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(o1));

        Order result1 = orderService.getOrderById(1L);

        assertEquals(1L, result1.getId());
    }

    @Test
    public void getAllOrderItemByIdIncrease(){
        Order o1 = new Order();
        o1.setId(1L);

        Order o2 = new Order();
        o2.setId(2L);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

                List<Long> result = orderService.getAllOrderItemByIdIncrease()
                        .stream()
                        .map(Order::getId)
                        .collect(Collectors.toList());

                assertEquals(Arrays.asList(1L, 2L), result);
    }

    @Test
    public void getAllOrderItemByIdDecline(){
        Order o1 = new Order();
        o1.setId(1L);

        Order o2 = new Order();
        o2.setId(2L);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<Long> result = orderService.getAllOrderItemByIdDecline()
                .stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(2L, 1L), result);
    }

    @Test
    public void getOrderByCustomer(){
        Customer c1 = new Customer();
        c1.setId(1L);
        Customer c2 = new Customer();
        c2.setId(2L);

        Order o1 = new Order();
        o1.setId(1L);
        o1.setCustomer(c2);

        Order o2 = new Order();
        o2.setId(2L);
        o2.setCustomer(c1);

        when(orderRepository.findByCustomer(c2)).thenReturn(Arrays.asList(o1));
        when(orderRepository.findByCustomer(c1)).thenReturn(Arrays.asList(o2));

        List<Long> result1 = orderService.getOrderByCustomer(c1)
                .stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        List<Long> result2 = orderService.getOrderByCustomer(c2)
                .stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L), result2);
        assertEquals(Arrays.asList(2L), result1);
    }

    @Test
    public void getOrderByStatus(){
    Order o1 = new Order();
    o1.setId(1L);
    o1.setStatus(OrderStatus.NEW);

    Order o2 = new Order();
    o2.setId(2L);
    o2.setStatus(OrderStatus.PAID);

    Order o3 = new Order();
    o3.setId(3L);
    o3.setStatus(OrderStatus.PAID);

    when(orderRepository.findByStatus(NEW)).thenReturn(Arrays.asList(o1));
    when(orderRepository.findByStatus(PAID)).thenReturn(Arrays.asList(o2, o3));

    List<Long> result1 = orderService.getOrderByStatus(NEW)
            .stream()
            .map(Order::getId)
            .collect(Collectors.toList());
    List<Long> result2 = orderService.getOrderByStatus(PAID)
                    .stream()
                    .map(Order::getId)
                    .collect(Collectors.toList());

    assertEquals(Arrays.asList(1L), result1);
    assertEquals(Arrays.asList(2L, 3L), result2);
    }

    @Test
    public void postOrder(){
        Customer c1 = new Customer();
        c1.setId(1L);

        Order o1 = new Order();
        o1.setId(1L);
        o1.setDate(LocalDateTime.now());
        o1.setStatus(NEW);
        o1.setCustomer(c1);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(c1));
        when(orderRepository.save(any(Order.class))).thenReturn(o1);

        Order result = orderService.postOrder(o1);

        assertNotNull(result);
        verify(orderRepository).save(o1);
    }

    @Test
    public void putOrder(){
        Customer c1 = new Customer();
        c1.setId(1L);

        Order existing = new Order();
        existing.setId(1L);
        existing.setDate(LocalDateTime.now());
        existing.setCustomer(c1);

        Order upDate = new Order();
        upDate.setDate(LocalDateTime.of(2020,2, 2, 2, 2));
        upDate.setCustomer(c1);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(c1));
        when(orderRepository.save(existing)).thenReturn(existing);

        Order result = orderService.putOrder(1L, upDate);

        assertNotNull(result);
        assertEquals(upDate.getDate(), result.getDate());
        verify(orderRepository).save(existing);
    }

    @Test
    public void deleteOrder(){
        Order o1 = new Order();
        o1.setId(1L);

        Order o2 = new Order();
        o2.setId(2L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(o1));

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }

}