package pibackend.domain.auth.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.service.UserService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    private final UserService service;

    @PostMapping("/registration")
    public Boolean registration(@RequestBody UserView user) {
        return service.registration(user);
    }

}