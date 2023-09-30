package pibackend.domain.auth.role.model.entity;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.auth.user.model.entity.User;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "role")
public class Role {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "privilege_name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Privilege> privileges;

    @ManyToOne
    @JoinColumn(name = "user_account_id", foreignKey = @ForeignKey(name = "FK_user_account"))
    private User user;

}
