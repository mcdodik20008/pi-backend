package pibackend.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.auth.user.model.entity.User;

public class PrivilegeService {

    public static void checkPrivilege(Registry registry, Level level) {
        User user = SecurityContext.currentUser;

        for (var privilege : user.getAllPrivilege()) {
            if (privilege.isGood(registry, level)) {
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет привилегий");
    }
}
