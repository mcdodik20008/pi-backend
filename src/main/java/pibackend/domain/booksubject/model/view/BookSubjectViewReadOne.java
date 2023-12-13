package pibackend.domain.booksubject.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.infrastructure.ReferenceView;

@Getter
@Setter
public class BookSubjectViewReadOne {

    private Long id;

    private String subject;

    private ReferenceView book;

}
