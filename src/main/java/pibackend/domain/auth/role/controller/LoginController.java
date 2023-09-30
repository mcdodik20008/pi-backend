package pibackend.domain.auth.role.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;
import pibackend.infrastructure.SecurityContext;

import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "", produces = "application/json")
public class LoginController {

    private final SecurityContext context;

    @PostMapping("/auth")
    public String login(String login, String password) {
        String uuid = UUID.randomUUID().toString();
        context.addLoginUser(uuid, new SecurityProperties.User());
        return uuid;
    }

    @GetMapping("/authout")
    public void logout(String userCode) {
        context.removeUser(userCode);
    }

}
