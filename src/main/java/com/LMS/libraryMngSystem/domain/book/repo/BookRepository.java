package com.LMS.libraryMngSystem.domain.book.repo;

import com.LMS.libraryMngSystem.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByKey(String key);

    List<Book> findAll();

    @Query(value = """
            SELECT * FROM book b
            WHERE b.book_name ILIKE CONCAT('%', :title, '%')
            """, nativeQuery = true)
    List<Book> findByBookName(String title);
}
