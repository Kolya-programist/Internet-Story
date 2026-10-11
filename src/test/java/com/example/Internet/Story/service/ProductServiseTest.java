package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Category;
import com.example.Internet.Story.entity.Product;
import com.example.Internet.Story.repository.CategoryRepository;
import com.example.Internet.Story.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiseTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    ProductService productService;


    @Test
    public void getAllProducts(){
        List<Product> products =  Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    public void getCheaperPrice(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(500));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(1000));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(1500));

        Product p4 = new Product();
        p4.setId(4L);
        p4.setPrice(new BigDecimal(2000));

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4));

        List<Product> result = productService.getCheaperPrice(new BigDecimal("1500"));

        assertEquals(2, result.size());

        List<Long> id = result.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertTrue(id.contains(1L));
        assertTrue(id.contains(2L));
        assertFalse(id.contains(3L));
        assertFalse(id.contains(4L));
    }

    @Test
    public void getMoreExpensive(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(1000));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(2000));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(3000));

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3));

        List<Product> id = productService.getMoreExpensive(new BigDecimal("1500"));

        assertEquals(2, id.size());

        List<Long> result = id.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertFalse(result.contains(1L));
        assertTrue(result.contains(2L));
        assertTrue(result.contains(3L));
    }

    @Test
    public void getAllProductsByPriceIncrease(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(600));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(1300));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(100));

        Product p4 = new Product();
        p4.setId(4L);
        p4.setPrice(new BigDecimal(30));

        Product p5 = new Product();
        p5.setId(5L);
        p5.setPrice(new BigDecimal(3000));


        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5));

        List<Long> result = productService.getAllProductsByPriceIncrease()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(4L, 3L, 1L, 2L, 5L), result);
    }

    @Test
    public void getAllProductsByPriceDecline(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(600));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(1300));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(100));

        Product p4 = new Product();
        p4.setId(4L);
        p4.setPrice(new BigDecimal(30));

        Product p5 = new Product();
        p5.setId(5L);
        p5.setPrice(new BigDecimal(3000));


        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5));

        List<Long> result = productService.getAllProductsByPriceDecline()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(5L, 2L, 1L, 3L, 4L), result);
    }

    @Test
    public void getMaxExpensive(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(600));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(1300));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(100));

        Product p4 = new Product();
        p4.setId(4L);
        p4.setPrice(new BigDecimal(30));

        Product p5 = new Product();
        p5.setId(5L);
        p5.setPrice(new BigDecimal(3000));


        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5));

        Product result = productService.getMaxExpensive();
        assertEquals(5L, result.getId());
    }

    @Test
    public void getMinExpensive(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(600));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(1300));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(100));

        Product p4 = new Product();
        p4.setId(4L);
        p4.setPrice(new BigDecimal(30));

        Product p5 = new Product();
        p5.setId(5L);
        p5.setPrice(new BigDecimal(3000));


        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5));

        Product result = productService.getMinExpensive();
        assertEquals(4L, result.getId());
    }

    @Test
    public void getAllProductsSortedName(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Дыня");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Яблоко");

        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Груша");

        Product p4 = new Product();
        p4.setId(4L);
        p4.setName("Арбуз");

        Product p5 = new Product();
        p5.setId(5L);
        p5.setName("Coca-Cola");

        Product p6 = new Product();
        p6.setId(6L);
        p6.setName("Pringls");

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5, p6));

        List<Long> result = productService.getAllProductsSortedName()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(5L, 6L, 4L, 3L, 1L, 2L), result);
    }

    @Test
    public void getAveragePrice(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(216));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(180));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(470));

        Product p4 = new Product();
        p4.setId(4L);
        p4.setPrice(new BigDecimal(93));

        Product p5 = new Product();
        p5.setId(5L);
        p5.setPrice(new BigDecimal(120));

        Product p6 = new Product();
        p6.setId(6L);
        p6.setPrice(new BigDecimal(430));

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5, p6));

        Double result = productService.getAveragePrice();

        assertEquals(251.5, result);
    }

    @Test
    public void getQuantityProduct(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(new BigDecimal(216));

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(new BigDecimal(180));

        Product p3 = new Product();
        p3.setId(3L);
        p3.setPrice(new BigDecimal(470));

        Product p4 = new Product();
        p4.setId(4L);
        p4.setPrice(new BigDecimal(93));

        Product p5 = new Product();
        p5.setId(5L);
        p5.setPrice(new BigDecimal(120));

        Product p6 = new Product();
        p6.setId(6L);
        p6.setPrice(new BigDecimal(430));

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5, p6));

        Long result = productService.getQuantityProduct();

        assertEquals(6, result);
    }

    @Test
    public void getGroupedByCategory() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Кисло-молочная");

        Category c2 = new Category();
        c2.setId(2L);
        c2.setName("Напитки");

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Творог");
        p1.setCategory(c1);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Fanta");
        p2.setCategory(c2);

        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Яблочный сок");
        p3.setCategory(c2);

        Product p4 = new Product();
        p4.setId(4L);
        p4.setName("Вода");
        p4.setCategory(c2);

        Product p5 = new Product();
        p5.setId(5L);
        p5.setName("Морс");
        p5.setCategory(c2);

        Product p6 = new Product();
        p6.setId(6L);
        p6.setName("Молоко");
        p6.setCategory(c1);

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3, p4, p5, p6));

        Map<Category, List<Product>> result = productService.getGroupedByCategory();

        assertEquals(2, result.size());
        assertTrue(result.containsKey(c1));
        assertTrue(result.containsKey(c2));

        List<Long> idsC1 = result.get(c1)
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        List<Long> idsC2 = result.get(c2)
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L, 6L), idsC1);
        assertEquals(Arrays.asList(2L, 3L, 4L, 5L), idsC2);
    }

    @Test
    public void getProductById(){
        Product p1 = new Product();
        p1.setName("Andrey");
        p1.setId(476L);

        when(productRepository.findById(476L)).thenReturn(Optional.of(p1));

        Product result1 = productService.getProductById(476L);

        assertEquals(476L, result1.getId());
    }

    @Test
    public void getProductByIncreaseId(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Картофель");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Морковь");

        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Капуста");

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3));

        List<Long> result = productService.getProductByIncreaseId()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L, 2L, 3L), result);
    }

    @Test
    public void getProductByDeclineId(){
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Картофель");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Морковь");

        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Капуста");

        when(productRepository.findAll()).thenReturn(Arrays.asList(p3, p2, p1));

        List<Long> result = productService.getProductByDeclineId()
                        .stream()
                        .map(Product::getId)
                        .collect(Collectors.toList());
        assertEquals(Arrays.asList(3L, 2L, 1L), result);
    }

    @Test
    public void getProductByName(){
        Product p1 = new Product();
        p1.setName("Andrey");
        p1.setId(476L);

        Product p2 = new Product();
        p2.setName("Victor");
        p2.setId(945L);

        when(productRepository.findByName("Andrey")).thenReturn(Arrays.asList(p1));
        when(productRepository.findByName("Victor")).thenReturn(Arrays.asList(p2));

        List<Product> result1 = productService.getProductByName("Andrey");
        List<Product> result2 = productService.getProductByName("Victor");

        assertEquals("Andrey", result1.get(0).getName());
        assertEquals("Victor", result2.get(0).getName());
    }

    @Test
    public void getProductByCategory(){
        Category c1 = new Category();
        c1.setName("Торты");

        Category c2 = new Category();
        c2.setName("Конфеты");

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Шоколадный торт");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Лёвушка");

        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Пчёлка");

        when(productRepository.findByCategory(c1)).thenReturn(Arrays.asList(p1));
        when(productRepository.findByCategory(c2)).thenReturn(Arrays.asList(p2, p3));

        List<Long> result1 = productService.getProductByCategory(c1)
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        List<Long> result2 = productService.getProductByCategory(c2)
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(1L), result1);
        assertEquals(Arrays.asList(2L, 3L), result2);
    }

    @Test
    public void postProduct(){
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Снеки");

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Чипсы");
        p1.setPrice(new BigDecimal(200));
        p1.setQuantity(30);
        p1.setCategory(c1);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(c1));
        when(productRepository.save(any(Product.class))).thenReturn(p1);

        Product result = productService.postProduct(p1);
        assertNotNull(result);
        verify(productRepository).save(p1);
    }

    @Test
    public void putProduct() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Снеки");

        Product existing = new Product();
        existing.setId(1L);
        existing.setName("Чипсы");
        existing.setPrice(new BigDecimal(200));
        existing.setQuantity(30);
        existing.setCategory(c1);

        Product upDate = new Product();
        upDate.setName("Сухарики");
        upDate.setPrice(new BigDecimal(150));
        upDate.setQuantity(60);
        upDate.setCategory(c1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(c1));
        when(productRepository.save(existing)).thenReturn(existing);

        Product result = productService.putProduct(1L, upDate);

        assertEquals("Сухарики", result.getName());
        assertEquals(new BigDecimal(150), result.getPrice());
        verify(productRepository).save(existing);
    }

    @Test
    public void deleteProduct(){
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Снеки");

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Чипсы");
        p1.setPrice(new BigDecimal(200));
        p1.setQuantity(30);
        p1.setCategory(c1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(p1));

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }
}
