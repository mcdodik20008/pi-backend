package pibackend.domain.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.book.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, String>, QuerydslPredicateExecutor<Book> {

    Page<Book> findByUuidContainingIgnoreCase(String id, Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
