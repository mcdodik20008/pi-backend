package pibackend.domain.auth.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.auth.user.model.view.UserChangePassword;
import pibackend.domain.auth.user.model.view.UserChangePasswordNoConfirmation;
import pibackend.domain.auth.user.model.view.UserView;
import pibackend.domain.auth.user.service.UserService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    private final UserService service;

    @GetMapping
    public Page<UserView> getList(Pageable pageable) {
        return service.getPage(pageable);
    }

    @GetMapping("/{login}")
    public UserView getOne(@PathVariable String login) {
        return service.getOne(login);
    }

    @PostMapping("/registration")
    public Boolean registration(@RequestBody UserView user) {
        return service.registration(user);
    }

    @PatchMapping("/change_password")
    public Boolean changePassword(@RequestBody UserChangePassword view) {
        return service.changePassword(view);
    }

    @PatchMapping("/change_password_admin")
    public Boolean changePasswordAdmin(@RequestBody UserChangePasswordNoConfirmation view) {
        return service.changePasswordAdmin(view);
    }

    @PutMapping("/{login}")
    public UserView update(@PathVariable String login, @RequestBody UserView view) {
        service.update(login, view);
        return service.getOne(login);
    }

    @DeleteMapping("/{login}")
    public void delete(@PathVariable String login) {
        service.delete(login);
    }

}
