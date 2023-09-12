package pibackend.domain.book.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookViewReadList {

    private String uuid;

    private String title;

    private String subTitle;

    private String firstPublishDate;

    private String description;

}
