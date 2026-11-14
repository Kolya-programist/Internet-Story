package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Customer;
import com.example.Internet.Story.exception.CustomerNotFoundException;
import com.example.Internet.Story.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

//Найти всех покупателей
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

//Найти покупателя по ID
    public Customer getCustomerById(Long id){
        return  customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
    //Найти все позиции по возрастанию Id
    public List<Customer> getAllCustomByIdIncrease(){
        return customerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getId))
                .collect(Collectors.toList());

    }
    //Найти все позиции по убыванию Id
    public List<Customer> getAllCustomItemByIdDecline(){
        return customerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getId).reversed())
                .collect(Collectors.toList());
    }

    //Найти покупателя по email
    public Customer getByEmail(String email){
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(email));
    }

//Загрузить покупателя
    @Transactional
    public Customer postCustomer(Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

            customer.setRole("USER");

        Customer saved = customerRepository.save(customer);
        log.info("Покупатель {} загружен", saved.getId());
        return saved;
    }

//Обновить покупателя
    @Transactional
    public Customer putCustomer(Long id, Customer customer){
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        existing.setAddress(customer.getAddress());
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        log.info("Покупатель {} обновлен", existing.getId());
        return customerRepository.save(existing);
    }

//Удалить покупателя
    @Transactional
    public void deleteCustomer(Long id){
        customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
        log.info("Покупатель {} удален", id);
    }

//Админ может присвоить обычному пользователю роль админа
    @Transactional
    public void makeAdmin(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customer.setRole("ADMIN");
        customerRepository.save(customer);
    }

    @Transactional
    public void increaseFailedAttempts(Customer customer){
        int newFailAttempts = customer.getFailedAttempts() + 1;
        customer.setFailedAttempts(newFailAttempts);

        if(newFailAttempts >= 5){
            customer.setLockTime(LocalDateTime.now().plusMinutes(1));
        }

        customerRepository.save(customer);
    }

    @Transactional
    public void resetFailedAttempts(Customer customer){
        customer.setFailedAttempts(0);
        customer.setLockTime(null);
        customerRepository.save(customer);
    }
}
