package ru.javabegin.library.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by Artur on 13.03.2017.
 */
public interface GeneralDAO<T> {
    List<T> getAll();
    List<T> search(String... searchString);
    T get(long id);
    T save(T obj);
    void delete(T obj);
    List<T> getAll(Sort sort);
    Page<T> getAll(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection);
    Page<T> search (int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, String... searchString);
}
