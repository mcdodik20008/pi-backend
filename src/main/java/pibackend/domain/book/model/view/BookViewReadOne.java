package pibackend.domain.book.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.author.model.view.AuthorViewReadList;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadList;
import pibackend.domain.booksubject.model.view.BookSubjectViewReadList;

import java.util.List;

@Getter
@Setter
public class BookViewReadOne {

    private String uuid;

    private String title;

    private String subTitle;

    private String firstPublishDate;

    private String description;

    private List<AuthorViewReadList> authors;

    private List<BookCoverViewReadList> covers;

    private List<BookSubjectViewReadList> subjects;

}
