package pibackend.domain.booksubject.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.view.BookViewReadList;

@Getter
@Setter
public class BookSubjectView {

    private Long id;

    private String subject;

    private BookViewReadList book;

}
