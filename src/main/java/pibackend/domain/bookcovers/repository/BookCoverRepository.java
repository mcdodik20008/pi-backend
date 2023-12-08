package pibackend.domain.bookcovers.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.bookcovers.model.entity.BookCover;

public interface BookCoverRepository extends JpaRepository<BookCover, Long>, QuerydslPredicateExecutor<BookCover> {

    Page<BookCover> findByBook_Uuid(String bookId, Pageable pageable);

}
