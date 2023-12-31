package pibackend.domain.bookcovers.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.view.BookViewReadOne;

@Getter
@Setter
public class BookCoverViewReadOne {

    private Long id;

    private String coverFile;

    private BookViewReadOne book;

}
