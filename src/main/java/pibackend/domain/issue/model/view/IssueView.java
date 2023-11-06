package pibackend.domain.issue.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.customer.model.entity.Customer;

import java.time.LocalDate;

@Getter
@Setter
public class IssueView {

    private Long id;

    private LocalDate dateOfIssue;

    private LocalDate returnUntil;

    private Customer customer;

    private Book book;

}
