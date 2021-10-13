package com.makul.fitness.service;

import com.makul.fitness.dao.BookmarkDao;
import com.makul.fitness.exceptions.IncorrectDataException;
import com.makul.fitness.exceptions.NoEntityException;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.service.api.BookmarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;
    public BookmarkServiceImpl(BookmarkDao bookmarkDao) {
        this.bookmarkDao = bookmarkDao;
    }

    @Override
    @Transactional
    public Bookmark create(Bookmark bookmark) {
        return bookmarkDao.save(bookmark);
    }

    @Override
    public Bookmark read(long id) {
        if (id<1) throw new IncorrectDataException("bookmark id");
        return bookmarkDao.findById(id).orElseThrow(()->new NoEntityException("Bookmark"));
    }

    @Override
    public List<Bookmark> readAll() {
        return StreamSupport.stream(bookmarkDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (id<1) throw new IncorrectDataException("bookmark id");
        bookmarkDao.deleteById(id);
    }
}
