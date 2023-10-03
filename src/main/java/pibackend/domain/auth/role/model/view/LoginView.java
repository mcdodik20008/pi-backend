package pibackend.domain.auth.role.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginView {
    private String login;
    private String password;
}
