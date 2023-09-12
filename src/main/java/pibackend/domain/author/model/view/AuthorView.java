package pibackend.domain.author.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorView {

    private String uuid;

    private String title;

    private String subTitle;

    private String firstPublishDate;

    private String description;

}
