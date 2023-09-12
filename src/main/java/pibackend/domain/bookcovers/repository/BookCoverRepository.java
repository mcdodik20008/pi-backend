package pibackend.domain.bookcovers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.bookcovers.model.entity.BookCover;

public interface BookCoverRepository extends JpaRepository<BookCover, Long>, QuerydslPredicateExecutor<BookCover> {
}
