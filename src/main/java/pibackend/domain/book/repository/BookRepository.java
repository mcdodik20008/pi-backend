package pibackend.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.book.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, String>, QuerydslPredicateExecutor<Book> {
}
