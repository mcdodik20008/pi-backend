package pibackend.domain.bookcovers.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.view.BookViewReadOne;

@Getter
@Setter
public class BookCoverView {

    private Long id;

    private Integer coverFile;

    private BookViewReadOne book;

}
