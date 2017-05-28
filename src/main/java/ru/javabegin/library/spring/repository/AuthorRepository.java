package ru.javabegin.library.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.library.domain.Author;

import java.util.List;

/**
 * Created by Artur on 14.03.2017.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByFioContainingIgnoreCaseOrderByFio(String fio);
    Page<Author> findByFioContainingIgnoreCaseOrderByFio(String fio, Pageable pageable);
}