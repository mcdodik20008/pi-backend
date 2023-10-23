package pibackend.domain.issue.model.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.customer.model.entity.Customer;

@Getter
@Setter
@Entity(name = "issue")
public class Issue {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @Column(name = "return_until", nullable = false)
    private LocalDate returnUntil;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
}
