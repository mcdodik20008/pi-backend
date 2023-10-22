package pibackend.domain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pibackend.domain.customer.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>, QuerydslPredicateExecutor<Customer> {
}
