package ru.javabegin.library.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.javabegin.library.domain.Book;

import java.util.List;

/**
 * Created by Artur on 12.03.2017.
 */
public interface BookDao extends GeneralDAO<Book> {

    List<Book> findTopBooks(int limit);
    byte[] getContent (Long id);
    Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId);
    void updateViewCount(long viewCount, long id);
    void updateRating(long totalRating, long totalVoteCount, int avgRating, long id);
}
