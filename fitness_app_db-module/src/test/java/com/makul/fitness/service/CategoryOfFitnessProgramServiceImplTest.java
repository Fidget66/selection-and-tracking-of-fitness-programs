package com.makul.fitness.service;

import com.makul.fitness.dao.CategoryOfFitnessProgramDao;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class CategoryOfFitnessProgramServiceImplTest {

    @Mock
    private CategoryOfFitnessProgramDao categoryDao;
    @InjectMocks
    private  CategoryOfFitnessProgramServiceImpl categoryService;

    @Test
    void whenCreate_returnCategory() {
        CategoryOfFitnessProgram category = getCategory();
        Mockito.when(categoryDao.save(category)).thenReturn(category);
        CategoryOfFitnessProgram actual = categoryService.create(category);
        CategoryOfFitnessProgram expected = category;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryDao, Mockito.times(1)).save(category);
    }

    @Test
    void whenReadAll_returnListCategories() {
        List <CategoryOfFitnessProgram> categoryList = Stream
                .generate(() -> getCategory())
                .limit(3)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0,20);
        Page <CategoryOfFitnessProgram> page = new PageImpl<>(categoryList,pageable,3);
        Mockito.when(categoryDao.findAll(pageable)).thenReturn(page);
        List <CategoryOfFitnessProgram> actual = categoryService.readAll(0,20).getContent();
        List <CategoryOfFitnessProgram> expected = categoryList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryDao, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void whenRead_returnCategory() {
        CategoryOfFitnessProgram category = getCategory();
        UUID uuid = getUUID();
        Mockito.when(categoryDao.findById(uuid)).thenReturn(Optional.ofNullable(category));
        CategoryOfFitnessProgram actual = categoryService.read(uuid);
        CategoryOfFitnessProgram expected = category;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryDao, Mockito.times(1)).findById(uuid);
    }

    @Test
    void whenRead_throwException() {
        CategoryOfFitnessProgram category = getCategory();
        UUID uuid = getUUID();
        Mockito.when(categoryDao.findById(uuid)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->categoryService.read(uuid));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Category Of Fitness Program в базе данных не существует");
        Mockito.verify(categoryDao, Mockito.times(1)).findById(uuid);
    }

    private CategoryOfFitnessProgram getCategory(){
        CategoryOfFitnessProgram category = new CategoryOfFitnessProgram();
        category.setShortName("Test Category");
        category.setDescription("Category for test service");
        category.setId(getUUID());
        return category;
    }

    private UUID getUUID(){
        return UUID.randomUUID();
    }
}