package pibackend.domain.booksubject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.booksubject.model.entity.BookSubject;

public interface BookSubjectRepository extends JpaRepository<BookSubject, Long>, QuerydslPredicateExecutor<BookSubject> {
}
