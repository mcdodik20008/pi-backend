package pibackend.domain.auth.user.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.auth.role.model.entity.Role;

import java.util.List;

@Getter
@Setter
public class UserView {

    private Long id;

    private String username;

    private String password;

    //TODO: private List<Role> role;

}