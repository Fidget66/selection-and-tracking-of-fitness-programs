package com.makul.fitness.service;

import com.makul.fitness.dao.BookmarkDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.service.api.BookmarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;
    public BookmarkServiceImpl(BookmarkDao bookmarkDao) {
        this.bookmarkDao = bookmarkDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bookmark create(Bookmark bookmark) {
        return bookmarkDao.save(bookmark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(long id) {
        if (id<1) throw new IncorrectDataException("Bookmark id");
        bookmarkDao.deleteById(id);
    }
}
