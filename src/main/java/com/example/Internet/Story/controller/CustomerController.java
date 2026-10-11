package com.example.Internet.Story.controller;

import com.example.Internet.Story.dto.CustomerDto;
import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.mapper.CustomerMapper;
import com.example.Internet.Story.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    public List<CustomerDto> getAll(){
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable Long id){
        return customerMapper.toDto(customerService.getCustomerById(id));
    }

    @GetMapping("/id/increase")
    public List<CustomerDto> getAllCustomByIdIncrease(){
        return customerService.getAllCustomByIdIncrease()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/decline")
    public List<CustomerDto> getAllCustomItemByIdDecline(){
        return customerService.getAllCustomItemByIdDecline()
                .stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/email/{email}")
    public CustomerDto getByEmail(@PathVariable String email){
        return customerMapper.toDto(customerService.getByEmail(email));
    }

    @PostMapping
    public CustomerDto post(@RequestBody CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Customer save = customerService.postCustomer(customer);
        return customerMapper.toDto(save);
    }

    @PutMapping("/{id}")
    public CustomerDto put(@PathVariable Long id, @RequestBody CustomerDto customerDto){
        Customer customer = customerMapper.toEntity(customerDto);
        Customer save = customerService.putCustomer(id, customer);
        return customerMapper.toDto(save);
    }

    @DeleteMapping("/{id}")
    public CustomerDto delete(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        customerService.deleteCustomer(id);
        return customerMapper.toDto(customer);
    }
}
