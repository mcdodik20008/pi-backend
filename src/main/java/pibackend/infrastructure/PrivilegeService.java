package pibackend.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import pibackend.domain.auth.role.model.entity.Registry;

public abstract class PrivilegeService<T, V> {

    @Autowired
    private JpaRepository<T, V> repository;

    public abstract Registry getRegistry();
}
