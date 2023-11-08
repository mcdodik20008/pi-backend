package pibackend.domain.issue.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.customer.model.entity.Customer;

import java.util.Date;

@Getter
@Setter
public class IssueView {

    private Long id;

    private Date dateOfIssue;

    private Date returnUntil;

    private boolean isReturned;

    private Customer customer;

    private Book book;

}
