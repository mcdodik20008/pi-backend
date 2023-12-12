package pibackend.domain.book.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.infrastructure.ReferenceView;

import java.util.List;

@Getter
@Setter
public class BookViewCreate {

    private String title;

    private String subTitle;

    private String firstPublishDate;

    private String description;

    private List<ReferenceView> authors;

    private List<ReferenceView> covers;

    private List<ReferenceView> subjects;

}
