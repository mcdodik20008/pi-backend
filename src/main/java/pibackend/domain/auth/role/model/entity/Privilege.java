package pibackend.domain.auth.role.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "privilege")
public class Privilege {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "privilege_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "registry", nullable = false)
    private Registry registry;

    @Enumerated(EnumType.STRING)
    @Column(name = "levels", nullable = false)
    private Level levels;

    public boolean isGood(Registry registry, Level level) {
        if (this.registry.equals(Registry.ALL)) {
            return true;
        }

        if (this.registry.equals(registry)) {
            if (this.levels.equals(Level.ALL)) {
                return true;
            }

            if (this.levels.equals(level)) {
                return true;
            }
        }

        return false;
    }
}
