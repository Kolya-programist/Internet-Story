package com.example.Internet.Story.controller;

import com.example.Internet.Story.dto.ProductDto;
import com.example.Internet.Story.entity.Category;
import com.example.Internet.Story.entity.Product;
import com.example.Internet.Story.mapper.ProductMapper;
import com.example.Internet.Story.service.CategoryService;
import com.example.Internet.Story.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, ProductMapper productMapper, CategoryService categoryService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }
//Найти все товары
    @GetMapping
    public List<ProductDto> getAll(){
        return productService.getAllProducts()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

//Найти все товары, кторые ниже указанной цены
    @GetMapping("/cheaper/{price}")
    public List<ProductDto> getCheaperPrice(@PathVariable BigDecimal price){
        return productService.getCheaperPrice(price)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

//Найти все товары, которые выше указанной цены
    @GetMapping("/expensive/{price}")
    public List<ProductDto> getMoreExpensive(@PathVariable BigDecimal price){
        return productService.getMoreExpensive(price)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

//Сортировка продуктов по возратсанию цены
    @GetMapping("/sorted/price/increase")
    public List<ProductDto> getAllProductByPriceIncrease(){
        return productService.getAllProductsByPriceIncrease()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
// Сортировка продуктов по убыванию цены
    @GetMapping("/sorted/price/decline")
    public List<ProductDto> getAllProductByPriceDecline(){
        return productService.getAllProductsByPriceDecline()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
//Самый дорогой продукт
    @GetMapping("/sorted/max")
    public ProductDto getMaxExpensive(){
        return productMapper.toDto(productService.getMaxExpensive());
    }
//Самый дешевый продукт
    @GetMapping("/sorted/min")
    public ProductDto getMinExpensive(){
        return productMapper.toDto(productService.getMinExpensive());
    }
//Сортировать продукты по имени
    @GetMapping("/sorted/name/increase")
    public List<ProductDto> getAllProductsSortedName(){
        return  productService.getAllProductsSortedName()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

//Средняя цена продуктов
    @GetMapping("/sorted/price/avarge")
    public Double getAveragePrice(){
        return productService.getAveragePrice();
    }
//Найти количество позиций
    @GetMapping("/quantity")
    public long getQuantityProduct(){
        return productService.getQuantityProduct();
    }
//Сгрупировать товары по категориям
    @GetMapping("/category")
    public Map<String, List<ProductDto>> getGroupedByCategory(){
        return productService.getGroupedByCategory()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(  e -> e.getKey().getName(),
                        e -> e.getValue()
                        .stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toList())));
    }
//Найти продукт по id
    @GetMapping("/id/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return productMapper.toDto(productService.getProductById(id));
    }
//Найти продукты по возрастанию Id
    @GetMapping("/id/increase")
    public List<ProductDto> getProductByIncreaseId(){
        return productService.getProductByIncreaseId()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
//Найти продукты по убыванию Id
    @GetMapping("/id/decline")
    public List<ProductDto> getProductByDeclineId(){
        return productService.getProductByDeclineId()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
    //Найти продукт по названию
    @GetMapping("/name/{name}")
    public List<ProductDto> getByName(@PathVariable String name){
        return productService.getProductByName(name)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
//Найти продукт по категории
    @GetMapping("/category/{id}")
    public List<ProductDto> getByCategory(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);
        return productService.getProductByCategory(category)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
//Загрузить продукт
    @PostMapping
    public ProductDto post(@Valid @RequestBody ProductDto productDto){
        Product product = productMapper.toEntity(productDto);
        Product save = productService.postProduct(product);
        return productMapper.toDto(save);
    }
//Обновить продукт
    @PutMapping("/{id}")
    public ProductDto put(@PathVariable Long id, @Valid @RequestBody ProductDto productDto){
        Product product = productMapper.toEntity(productDto);
        Product save = productService.putProduct(id, product);
        return productMapper.toDto(save);
    }
//Удалить продукт
    @DeleteMapping("/{id}")
    public ProductDto delete(@PathVariable Long id){
        Product product = productService.getProductById(id);
        productService.deleteProduct(id);
        return productMapper.toDto(product);
    }
}
