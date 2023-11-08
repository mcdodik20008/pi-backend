package pibackend.domain.issue.model.entity;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.customer.model.entity.Customer;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "issue")
public class Issue {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Column(name = "return_until")
    private Date returnUntil;

    @Column(name = "is_returned")
    private boolean isReturned;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
