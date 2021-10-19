package com.makul.fitness.service;

import com.makul.fitness.dao.BookmarkDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Bookmark;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class BookmarkServiceImplTest {

    @Mock
    private BookmarkDao bookmarkDao;
    @InjectMocks
    private BookmarkServiceImpl bookmarkService;

    @Test
    void whenCreate_returnBookmark() {
        Bookmark bookmark = getBookmark();
        Mockito.when(bookmarkDao.save(bookmark)).thenReturn(bookmark);
        Bookmark actual = bookmarkService.create(bookmark);
        Bookmark expected = bookmark;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookmarkDao, Mockito.times(1)).save(bookmark);
    }

    @Test
    void whenRead_returnBookmark() {
        Bookmark bookmark = getBookmark();
        Mockito.when(bookmarkDao.findById(1L)).thenReturn(Optional.ofNullable(bookmark));
        Bookmark actual = bookmarkService.read(1L);
        Bookmark expected = bookmark;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookmarkDao, Mockito.times(1)).findById(1L);
    }

    @Test
    void whenRead_throwException() {
        Bookmark bookmark = getBookmark();
        Mockito.when(bookmarkDao.findById(4L)).thenReturn(Optional.empty());
        NoEntityException noEntityException = Assertions.assertThrows(NoEntityException.class,
                ()->bookmarkService.read(4L));
        Assertions.assertEquals(noEntityException.getMessage(),
                "Такой записи для Bookmark в базе данных не существует");
        Mockito.verify(bookmarkDao, Mockito.times(1)).findById(4L);

        IncorrectDataException incorrectDataException = Assertions.assertThrows(IncorrectDataException.class,
                ()->bookmarkService.read(-1L));
        Assertions.assertEquals(incorrectDataException.getMessage(),
                "Введены некорректные данные для Bookmark id");
        Mockito.verify(bookmarkDao, Mockito.times(0)).findById(-1L);
    }


    @Test
    void whenReadAll_returnListBookmark() {
        List <Bookmark> bookmarks = Stream
                .generate(() -> getBookmark())
                .limit(3)
                .collect(Collectors.toList());
        Mockito.when(bookmarkDao.findAll()).thenReturn(bookmarks);
        List <Bookmark> actual = bookmarkService.readAll();
        List <Bookmark> expected = bookmarks;
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookmarkDao, Mockito.times(1)).findAll();
    }

    @Test
    void whenDelete_thenOk() {
        bookmarkService.delete(2L);
        Mockito.verify(bookmarkDao, Mockito.times(1)).deleteById(2L);
    }

    @Test
    void whenDelete_throwException() {
        IncorrectDataException incorrectDataException = Assertions.assertThrows(IncorrectDataException.class,
                ()->bookmarkService.read(-2L));
        Assertions.assertEquals(incorrectDataException.getMessage(),
                "Введены некорректные данные для Bookmark id");
        Mockito.verify(bookmarkDao, Mockito.times(0)).deleteById(-2L);
    }

    private Bookmark getBookmark(){
        Bookmark bookmark = new Bookmark();
        bookmark.setId(1);
        return bookmark;
    }
}