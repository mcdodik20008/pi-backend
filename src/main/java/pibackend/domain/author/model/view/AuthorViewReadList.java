package pibackend.domain.author.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorViewReadList {

    private String uuid;

    private String name;

    private String bio;

    private String birthDate;

    private String deathDate;

    private String wikipedia;

}
