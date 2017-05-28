package ru.javabegin.library.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javabegin.library.domain.Genre;

import java.util.List;

/**
 * Created by Artur on 16.03.2017.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{
    List<Genre> findByNameContainingIgnoreCaseOrderByName(String name);
    Page<Genre> findByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}
