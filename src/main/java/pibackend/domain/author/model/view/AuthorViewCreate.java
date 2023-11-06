package pibackend.domain.author.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorViewCreate {


    private String name;

    private String bio;

    private String birthDate;

    private String deathDate;

    private String wikipedia;

}
