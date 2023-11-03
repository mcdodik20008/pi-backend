package pibackend.domain.auth.user.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordNoConfirmation {

    private String login;

    private String newPassword;

}