package pibackend.domain.author.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.author.model.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, String>, QuerydslPredicateExecutor<Author> {

    Page<Author> findByUuidContaining(String id, Pageable pageable);

    Page<Author> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
