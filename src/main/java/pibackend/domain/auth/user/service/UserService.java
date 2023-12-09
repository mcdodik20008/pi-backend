package pibackend.domain.auth.user.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.user.model.entity.User;
import pibackend.domain.auth.user.model.mapper.UserMapper;
import pibackend.domain.auth.user.model.view.UserChangePassword;
import pibackend.domain.auth.user.model.view.UserChangePasswordNoConfirmation;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.model.view.UserViewCreate;
import pibackend.domain.auth.user.repository.UserRepository;
import pibackend.infrastructure.SecurityContext;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private SecurityContext securityContext;

    private final UserRepository repository;

    private final UserMapper mapper;

    public Page<UserView> getPage(Pageable pageable, String login) {
        if (login != null) {
            return repository.findByLoginContainingIgnoreCase(login, pageable).map(mapper::toView);
        }
        return repository.findAll(pageable).map(mapper::toView);
    }

    public List<User> getList() {
        return repository.findAll();
    }

    public UserView getOne(String login) {
        return mapper.toView(getObject(login));
    }

    public User getObject(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() ->
                        new RuntimeException("Не найден пользователь с логином: " + login));
    }

    public String create(UserView view) {
        User entity = mapper.toEntity(view);
        return repository.save(entity).getLogin();
    }

    public void update(String login, UserView view) {
        User entity = mapper.toEntity(getObject(login), view);
        if (SecurityContext.currentUser.getLogin().equals(entity.getLogin())){
            securityContext.removeUserByLogin(login);
        }
        entity.setLogin(login);
        repository.save(entity);
    }

    public void delete(String login) {
        getObject(login);
        repository.deleteByLogin(login);
    }

    public Boolean registration(UserViewCreate view) {
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

    public Boolean changePassword(UserChangePassword view) {
        if (view.getNewPassword().equals(view.getNewPasswordConfirmation())) {
            var user = getObject(view.getLogin());
            var currentPasswordFromView = Hashing.sha256().hashString(view.getCurrentPassword(), StandardCharsets.UTF_8).toString();
            if (user.getPassword().equals(currentPasswordFromView)) {
                var newPassword = Hashing.sha256().hashString(view.getNewPassword(), StandardCharsets.UTF_8).toString();
                user.setPassword(newPassword);
                repository.save(user);
                return true;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный пароль");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Новый пароль и подтверждение не совпадают");
        }
    }

    public Boolean changePasswordAdmin(UserChangePasswordNoConfirmation view) {
        var user = getObject(view.getLogin());
        var newPassword = Hashing.sha256().hashString(view.getNewPassword(), StandardCharsets.UTF_8).toString();
        user.setPassword(newPassword);
        repository.save(user);
        return true;
    }

}
