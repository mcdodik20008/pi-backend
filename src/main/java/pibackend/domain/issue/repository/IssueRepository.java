package pibackend.domain.issue.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.issue.model.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue> {

    Page<Issue> findByCustomerName(String name, Pageable pageable);

    Page<Issue> findByBookTitle(String title, Pageable pageable);

    List<Issue> findByCustomerName(String name);

    List<Issue> findByBookTitle(String title);
    
}
