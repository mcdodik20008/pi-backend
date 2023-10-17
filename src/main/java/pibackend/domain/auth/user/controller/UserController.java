package pibackend.domain.auth.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public UserView getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping("/registration")
    public Boolean registration(@RequestBody UserView user) {
        return service.registration(user);
    }


    @PutMapping("/{id}")
    public UserView update(@PathVariable String id, UserView view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}