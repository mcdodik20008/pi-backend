package pibackend.domain.issue.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.view.BookViewReadOne;
import pibackend.domain.customer.model.entity.Customer;

import java.time.LocalDate;

@Getter
@Setter
public class IssueView {

    private Long id;

    private LocalDate dateOfIssue;

    private LocalDate returnUntil;

    private LocalDate dateOfReturn;

    private Customer customer;

    private BookViewReadOne book;

}
