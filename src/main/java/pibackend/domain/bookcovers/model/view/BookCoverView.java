package pibackend.domain.bookcovers.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.view.BookViewReadList;

@Getter
@Setter
public class BookCoverView {

    private Long id;

    private Integer coverFile;

    private BookViewReadList book;

}
