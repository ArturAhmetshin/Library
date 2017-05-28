package ru.javabegin.library.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javabegin.library.domain.Publisher;

import java.util.List;

/**
 * Created by Artur on 16.03.2017.
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    List<Publisher> findByNameContainingIgnoreCaseOrderByName(String name);
    Page<Publisher> findByNameContainingIgnoreCaseOrderByName(String name, Pageable pageable);
}
