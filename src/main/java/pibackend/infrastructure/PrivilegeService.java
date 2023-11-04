package pibackend.infrastructure;

import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.auth.user.model.entity.User;

public class PrivilegeService {

    public static void checkPrivilege(Registry registry, Level level){
        User user = SecurityContext.currentUser;

        for (var privilege: user.getAllPrivilege()) {
            if (privilege.isGood(registry, level)){
                return;
            }
        }
        throw new RuntimeException("Нет привилегий");
    }
}
