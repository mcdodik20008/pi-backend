package pibackend.domain.issue.model.view;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.customer.model.entity.Customer;

@Getter
@Setter
public class IssueView {

    private Long id;

    private LocalDate dateOfIssue;

    private LocalDate returnUntil;

    private LocalDate dateOfReturn;

    private Customer customer;

    private Book book;

}
