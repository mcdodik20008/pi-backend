package pibackend.domain.book.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.author.model.view.AuthorViewReadList;

import java.util.List;

@Getter
@Setter
public class BookViewReadOne {

    private String id;

    private String title;

    private String subTitle;

    private String firstPublishDate;

    private String description;

    private List<AuthorViewReadList> authors;
}
