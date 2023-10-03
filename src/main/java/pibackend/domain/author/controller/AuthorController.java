package pibackend.domain.author.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.author.model.view.AuthorViewReadList;
import pibackend.domain.author.model.view.AuthorViewReadOne;
import pibackend.domain.author.service.AuthorService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/author", produces = "application/json")
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    public List<AuthorViewReadList> getList() {
        return service.getList();
    }

    @GetMapping("/{id}")
    public AuthorViewReadOne getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public AuthorViewReadOne create(AuthorViewReadList view) {
        String id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public AuthorViewReadOne update(@PathVariable String id, AuthorViewReadList view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}
