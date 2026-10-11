package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.repository.CustomerRepository;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    public void getAllCustomers(){
        Customer c1 = new Customer();
        c1.setId(1L);

        Customer c2 = new Customer();
        c2.setId(2L);

        when(customerRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
    }

    @Test
    public void getCustomerById(){
        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setName("Тимоха");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(c1));

        Customer result = customerService.getCustomerById(1L);

        assertEquals("Тимоха", result.getName());
    }

    @Test
    public void getAllCustomByIdIncrease(){
        Customer c1 = new Customer();
        c1.setId(1L);

        Customer c2 = new Customer();
        c2.setId(2L);

        when(customerRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Long> result = customerService.getAllCustomByIdIncrease()
                .stream()
                .map(Customer::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L, 2L), result);
    }

    @Test
    public void getAllCustomItemByIdDecline(){
        Customer c1 = new Customer();
        c1.setId(1L);

        Customer c2 = new Customer();
        c2.setId(2L);

        when(customerRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Long> result = customerService.getAllCustomItemByIdDecline()
                .stream()
                .map(Customer::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(2L, 1L), result);
    }

    @Test
    public void getByEmail(){
        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setEmail("fadan@gmail.com");

        when(customerRepository.findByEmail(c1.getEmail())).thenReturn(Optional.of(c1));

        Customer result = customerService.getByEmail(c1.getEmail());

        assertEquals("fadan@gmail.com", result.getEmail());
        assertEquals(1L, result.getId());
    }

    @Test
    public void postCustomer(){
        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setName("Maxim");

        when(customerRepository.save(c1)).thenReturn(c1);

        Customer result = customerService.postCustomer(c1);

        assertEquals(1L, result.getId());
        verify(customerRepository).save(c1);
    }

    @Test
    public void putCustomer() {
        Customer c1 = new Customer();
        c1.setId(1L);
        c1.setName("Maxim");

        Customer update = new Customer();
        update.setEmail("gosha");
        update.setName("Grisha");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(c1));
        when(customerRepository.save(c1)).thenReturn(update);

        Customer result = customerService.putCustomer(1L, update);

        assertEquals("Grisha", result.getName());
        assertEquals("gosha", result.getEmail());
    }

    @Test
    public void deleteCustomer(){
        Customer c1 = new Customer();
        c1.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(c1));
        customerService.deleteCustomer(1L);

        verify(customerRepository).deleteById(1L);
    }
}
