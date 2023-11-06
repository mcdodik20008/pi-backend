package pibackend.domain.auth.user.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.auth.role.model.entity.Role;

import java.util.List;

@Getter
@Setter
public class UserView {

    private String login;

    private String firstName;

    private String secondName;

    private String lastName;

    private String password;

    private List<Role> roles;

}