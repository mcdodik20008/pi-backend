package pibackend.domain.auth.user.model.view;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.auth.role.model.entity.Level;
import pibackend.domain.auth.role.model.entity.Registry;
import pibackend.domain.auth.role.model.entity.Role;

import java.util.*;

@Getter
@Setter
public class UserViewCreate {

    private String login;

    private String firstName;

    private String secondName;

    private String lastName;

    private String password;

    private List<Role> roles;

    public Map<Registry, Set<Level>> getAllRegistry() {
        Map<Registry, Set<Level>> result = new HashMap<>();

        roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .forEach(x -> {
                    Set<Level> levels = result.computeIfAbsent(x.getRegistry(), y -> new HashSet<>());
                    levels.add(x.getLevels());
                });

        return result;
    }
}
