package pibackend.domain.author.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.view.BookViewReadList;

import java.util.List;

@Getter
@Setter
public class AuthorViewReadOne {

    private String uuid;

    private String name;

    private String bio;

    private String birthDate;

    private String deathDate;

    private String wikipedia;

    private List<BookViewReadList> books;

}
