package ru.javabegin.library.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javabegin.library.dao.BookDao;
import ru.javabegin.library.domain.Book;
import ru.javabegin.library.spring.repository.BookRepository;

import java.util.List;

/**
 * Created by Artur on 22.03.2017.
 */
@Service
@Transactional
public class BookService implements BookDao {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    @Override
    public Page<Book> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return bookRepository.findAllWithoutContent(new PageRequest(pageNumber, pageSize, new Sort(sortDirection, sortField)));
    }

    @Override
    public List<Book> search(String... searchString) {
        return null;
    }

    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString) {
        // чтобы название метода не было слишком длинным - можно использовать @Query c HQL (если больше 2-х переменных)
        return bookRepository.findByNameContainingIgnoreCaseOrAuthorFioContainingIgnoreCaseOrderByName(searchString[0], searchString[0], new PageRequest(pageNumber, pageSize, new Sort(sortDirection, sortField)));
    }


    @Override
    public Book save(Book book) {

        // отдельно сохраняем данные книги
        bookRepository.save(book);

        if (book.getContent() != null) {
            // отдельно сохраняем контент
            bookRepository.updateContent(book.getContent(), book.getId());
        }
        return book;
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book get(long id) {
        return bookRepository.findOne(id);
    }

    public List<Book> findTopBooks(int limit) {
        return bookRepository.findTopBooks(new PageRequest(0, limit, new Sort(Sort.Direction.DESC, "viewCount")));
    }

    @Override
    public byte[] getContent(Long id) {
        return bookRepository.getContent(id);
    }

    @Override
    public Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId) {
        return bookRepository.findByGenre(genreId, new PageRequest(pageNumber, pageSize, new Sort(sortDirection, sortField)));
    }

    @Override
    public void updateViewCount(long viewCount, long id) {
        bookRepository.updateViewCount(viewCount, id);
    }

    @Override
    public void updateRating(long totalRating, long totalVoteCount, int avgRating, long id) {
        bookRepository.updateRating(totalRating, totalVoteCount, avgRating, id);
    }

}
