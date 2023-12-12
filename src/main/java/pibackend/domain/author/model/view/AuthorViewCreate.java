package pibackend.domain.author.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.infrastructure.ReferenceView;

import java.util.List;

@Getter
@Setter
public class AuthorViewCreate {

    private String name;

    private String bio;

    private String birthDate;

    private String deathDate;

    private String wikipedia;


    private List<ReferenceView> books;
}
