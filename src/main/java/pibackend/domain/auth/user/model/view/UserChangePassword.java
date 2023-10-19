package pibackend.domain.auth.user.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePassword {

    private String login;

    private String currentPassword;

    private String newPassword;

    private String newPasswordConfirmation;

}