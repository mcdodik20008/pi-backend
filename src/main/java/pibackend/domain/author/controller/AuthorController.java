package pibackend.domain.author.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<AuthorViewReadOne> getList() {
        return service.getList();
    }

}
