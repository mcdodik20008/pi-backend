package pibackend.domain.auth.user.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {

    private String login;

    private String firstName;

    private String secondName;

    private String lastName;

    private String password;

    //TODO: private List<Role> role;

}