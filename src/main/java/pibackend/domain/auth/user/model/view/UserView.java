package pibackend.domain.auth.user.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {

    private String login;

    private String username;

    private String password;

    //TODO: private List<Role> role;

}