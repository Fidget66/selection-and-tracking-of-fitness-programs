package com.makul.fitness.service;

import com.makul.fitness.dao.BookmarkDao;
import com.makul.fitness.model.Bookmark;
import com.makul.fitness.service.api.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bookmark create(Bookmark bookmark) {
        return bookmarkDao.save(bookmark);
    }

    @Override
    public Page<Bookmark> readByUserId(UUID id, Pageable pageable) {
        return bookmarkDao.findByUserId(id, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(UUID id) {
        bookmarkDao.deleteById(id);
    }
}
