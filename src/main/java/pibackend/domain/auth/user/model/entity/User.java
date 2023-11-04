package pibackend.domain.auth.user.model.entity;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.auth.role.model.entity.Privilege;
import pibackend.domain.auth.role.model.entity.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity(name = "user_account")
public class User {

    @Id
    @Column(name = "user_login", nullable = false)
    private String login;

    @Column(name = "user_first_name", nullable = false)
    private String firstName;

    @Column(name = "user_second_name", nullable = false)
    private String secondName;

    @Column(name = "user_last_name", nullable = false)
    private String lastName;

    @Column(name = "user_password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public List<Privilege> getAllPrivilege(){
        return roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .distinct().toList();
    }
}