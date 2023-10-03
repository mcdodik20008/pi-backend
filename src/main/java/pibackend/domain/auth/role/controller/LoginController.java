package pibackend.domain.auth.role.controller;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pibackend.domain.auth.user.model.entity.User;
import pibackend.domain.auth.user.repository.UserRepository;
import pibackend.infrastructure.SecurityContext;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "", produces = "application/json")
public class LoginController {

    private final SecurityContext context;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public String login(String login, String password) {
        Optional<User> user = userRepository.findById(login);
        if (user.isEmpty()
                || Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString().equals(user.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ошибка авторизации. Проверьте логин или пароль");
        }

        String uuid = UUID.randomUUID().toString();
        context.addLoginUser(uuid, user.get());
        return uuid;
    }

    @GetMapping("/logout")
    public void logout(String userCode) {
        context.removeUser(userCode);
    }

}
