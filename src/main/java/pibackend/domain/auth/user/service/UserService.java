package pibackend.domain.auth.user.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.user.model.entity.User;
import pibackend.domain.auth.user.model.mapper.UserMapper;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    public Page<UserView> getPage(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toView);
    }

    public UserView getOne(String id) {
        return mapper.toView(getObject(id));
    }

    public User getObject(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найден автор с идентификатором: " + id));
    }

    public String create(UserView view) {
        User entity = mapper.toEntity(view);
        return repository.save(entity).getLogin();
    }

    public void update(String id, UserView view) {
        User entity = mapper.toEntity(getObject(id), view);
        entity.setLogin(id);
        repository.save(entity);
    }

    public void delete(String id) {
        getObject(id);
        repository.deleteById(id);
    }

    public Boolean registration(UserView view) {
        var entity = mapper.toEntity(view);
        if (repository.existsByLogin(entity.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь с таким логином уже существует.");
        }
        entity.setPassword(
                Hashing.sha256()
                .hashString(entity.getPassword(), StandardCharsets.UTF_8)
                .toString());
        repository.save(entity);
        return true;
    }

}
