package pibackend.domain.auth.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.auth.user.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, QuerydslPredicateExecutor<User> {
    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

    void deleteByLogin(String login);
}