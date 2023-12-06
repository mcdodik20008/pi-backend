package pibackend.domain.booksubject.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.view.BookViewReadOne;

@Getter
@Setter
public class BookSubjectViewReadOne {

    private Long id;

    private String subject;

    private BookViewReadOne book;

}
