package pibackend.domain.issue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.issue.model.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue> {

    Page<Issue> findByIdContaining(Long id, Pageable pageable);
}
