package com.example.Internet.Story.service;

import com.example.Internet.Story.entity.Category;
import com.example.Internet.Story.repository.CategoryRepository;
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
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void getAllCategory(){
        Category c1 = new Category();
        c1.setId(1L);

        Category c2 = new Category();
        c2.setId(2L);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Category> result = categoryService.getAllCategory();

        assertEquals(2, result.size());
    }

    @Test
    public void getCategoryById(){
        Category c1 = new Category();
        c1.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(c1));

        Category result = categoryService.getCategoryById(1L);

        assertEquals(1l, result.getId());
    }

    @Test
    public void getAllCategoryByIdIncrease(){
        Category c1 = new Category();
        c1.setId(1L);

        Category c2 = new Category();
        c2.setId(2L);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Long> result = categoryService.getAllCategoryByIdIncrease()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toUnmodifiableList());

        assertEquals(Arrays.asList(1L, 2L), result);
    }

    @Test
    public void getAllCategoryByIdDecline(){
        Category c1 = new Category();
        c1.setId(1L);

        Category c2 = new Category();
        c2.setId(2L);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Long> result = categoryService.getAllCategoryByIdDecline()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(2L, 1L), result);
    }

    @Test
    public void getCategoryByName(){
        Category c1 = new Category();
        c1.setName("Выпечка");

        when(categoryRepository.findByName(c1.getName())).thenReturn(Arrays.asList(c1));

        List<String> result = categoryService.getCategoryByName(c1.getName())
                .stream()
                .map(Category::getName)
                .collect(Collectors.toUnmodifiableList());

        assertEquals(Arrays.asList("Выпечка"), result);
    }

   @Test
   public void getAllCategorySortedName(){
       Category c1 = new Category();
       c1.setName("Выпечка");

       Category c2 = new Category();
       c2.setName("Лимонады");

       when(categoryRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

       List<String> result = categoryService.getAllCategorySortedName()
               .stream()
               .map(Category::getName)
               .collect(Collectors.toList());
       assertEquals(Arrays.asList("Выпечка", "Лимонады"), result);
   }

   @Test
    public void postCategory(){
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Фрукты");

        when(categoryRepository.save(c1)).thenReturn(c1);

        Category result = categoryService.postCategory(c1);

        assertNotNull(result);
        verify(categoryRepository).save(c1);
   }

   @Test
    public void putCategory(){
       Category c1 = new Category();
       c1.setId(1L);
       c1.setName("Фрукты");

       Category upDate = new Category();
       upDate.setId(1L);
       upDate.setName("Ягоды");

       when(categoryRepository.findById(1L)).thenReturn(Optional.of(c1));
       when(categoryRepository.save(c1)).thenReturn(upDate);

       Category result = categoryService.putCategory(1L, upDate);

       assertEquals(1L, result.getId());
       assertEquals("Ягоды", result.getName());
       verify(categoryRepository).save(c1);
   }

   @Test
    public void deleteCategory(){
        Category c1 = new Category();
        c1.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(c1));
        categoryService.deleteCategory(1L);
        verify(categoryRepository).deleteById(1L);
   }
}
