package com.makul.fitness.service;

import com.makul.fitness.dao.CategoryOfFitnessProgramDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.CategoryOfFitnessProgram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
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
        Mockito.when(categoryDao.findAll()).thenReturn(categoryList);
        List <CategoryOfFitnessProgram> actual = categoryService.readAll();
        List <CategoryOfFitnessProgram> expected = categoryList;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryDao, Mockito.times(1)).findAll();
    }

    @Test
    void whenRead_returnCategory() {
        CategoryOfFitnessProgram category = getCategory();
        Mockito.when(categoryDao.findById(2L)).thenReturn(Optional.ofNullable(category));
        CategoryOfFitnessProgram actual = categoryService.read(2);
        CategoryOfFitnessProgram expected = category;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryDao, Mockito.times(1)).findById(2L);
    }

    @Test
    void whenRead_throwException() {
        CategoryOfFitnessProgram category = getCategory();
        Mockito.when(categoryDao.findById(4L)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->categoryService.read(4L));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Category Of Fitness Program в базе данных не существует");
        Mockito.verify(categoryDao, Mockito.times(1)).findById(4L);

        IncorrectDataException incorrectDataException = Assertions.assertThrows(IncorrectDataException.class,
                ()->categoryService.read(-1L));
        Assertions.assertEquals(incorrectDataException.getMessage(),
                "Введены некорректные данные для Category Of Fitness Program id");
        Mockito.verify(categoryDao, Mockito.times(0)).findById(-1L);
    }

    private CategoryOfFitnessProgram getCategory(){
        CategoryOfFitnessProgram category = new CategoryOfFitnessProgram();
        category.setShortName("Test Category");
        category.setDescription("Category for test service");
        category.setId(2);
        return category;
    }
}