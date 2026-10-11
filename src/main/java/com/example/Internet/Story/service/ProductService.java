package com.example.Internet.Story.service;


import com.example.Internet.Story.exception.CategoryNotFoundException;
import com.example.Internet.Story.exception.ProductNotFoundException;
import com.example.Internet.Story.entity.Category;
import com.example.Internet.Story.entity.Product;
import com.example.Internet.Story.exception.ProductsEmptyException;
import com.example.Internet.Story.repository.CategoryRepository;
import com.example.Internet.Story.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    //Найти все продукты
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //Найти всех продуктов ниже указанной цены
    public List<Product> getCheaperPrice(BigDecimal price) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getPrice().compareTo(price) < 0)
                .collect(Collectors.toList());
    }

    //Найти всех продуктов выше указанной цены
    public List<Product> getMoreExpensive(BigDecimal price) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getPrice().compareTo(price) > 0)
                .collect(Collectors.toList());
    }

//Сортировка продуктов по возратсанию цены
    public List<Product> getAllProductsByPriceIncrease(){
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());
    }
//Сортировка продуктов по убыванию цены
    public List<Product> getAllProductsByPriceDecline(){
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());
    }
//Найти самый дорогой продукт
    public Product getMaxExpensive(){
        return productRepository.findAll()
                .stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElseThrow(() -> new ProductsEmptyException());
    }

//Найти самый дешевый продукт
    public Product getMinExpensive(){
        return productRepository.findAll()
                .stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElseThrow(() -> new ProductsEmptyException());
    }

//Сортировать продукты по названию
    public List<Product> getAllProductsSortedName(){
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }
//Средняя цена продуктов
    public Double getAveragePrice(){
        return productRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getPrice().doubleValue())
                .average()
                .orElseThrow(() -> new ProductsEmptyException());
    }
//Найти количество позиций
    public long getQuantityProduct(){
        return productRepository.findAll()
                .stream()
                .count();
    }

//Сгрупировать товары по категориям
    public Map<Category, List <Product>> getGroupedByCategory(){
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

//Найти продукт по ID
    public Product getProductById(Long id){
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
//Найти продукты по возрастанию Id
    public List<Product> getProductByIncreaseId(){
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());
    }
//Найти продукты по убыванию Id
    public List<Product> getProductByDeclineId(){
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .collect(Collectors.toList());
    }
//Найти по продукт имени
    public List<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }

//Найти продукт по категории
    public List<Product> getProductByCategory(Category category){
        return productRepository.findByCategory(category);
    }

//Загрузить продукт
    @Transactional
    public Product postProduct(Product product){
        if(product.getCategory() != null){
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new CategoryNotFoundException(product.getCategory().getId()));
            product.setCategory(category);
        }
        Product saved = productRepository.save(product);
        log.info("Продукт {} сохранен", saved.getId());
        return saved;
    }

//Обновить продукт
    @Transactional
    public Product putProduct(Long id, Product product){
        Product existing = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        if(product.getCategory() != null){
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new CategoryNotFoundException(product.getCategory().getId()));
            existing.setCategory(category);
        }
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        productRepository.save(existing);
        log.info("Продукт {} обновлен", existing.getId());
        return existing;
    }

//Удалить продукт
    @Transactional
    public void deleteProduct(Long id){
       productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
        log.info("Продукт {} удален", id);
    }
}
