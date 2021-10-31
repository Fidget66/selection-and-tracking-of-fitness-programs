package com.makul.fitness.service;

import com.makul.fitness.dao.BookmarkDao;
import com.makul.fitness.model.Bookmark;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void whenDelete_thenOk() {
        bookmarkService.delete(2L);
        Mockito.verify(bookmarkDao, Mockito.times(1)).deleteById(2L);
    }

    private Bookmark getBookmark(){
        Bookmark bookmark = new Bookmark();
        bookmark.setId(1);
        return bookmark;
    }
}