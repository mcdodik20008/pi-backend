package pibackend.domain.book.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookViewCreate {

    private String title;

    private String subTitle;

    private String firstPublishDate;

    private String description;

}
