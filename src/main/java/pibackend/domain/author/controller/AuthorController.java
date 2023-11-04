package pibackend.domain.author.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.author.model.view.AuthorViewReadList;
import pibackend.domain.author.model.view.AuthorViewReadOne;
import pibackend.domain.author.service.AuthorService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/author", produces = "application/json")
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    public Page<AuthorViewReadList> getPage(
            Pageable pageable,
            @RequestParam(required = false) String filterId,
            @RequestParam(required = false) String filterName) {
        if (filterId != null) {
            return service.getPageByIdLike(pageable, filterId);
        }
        if (filterName != null) {
            return service.getPageByNameLike(pageable, filterName);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public AuthorViewReadOne getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public AuthorViewReadOne create(@RequestBody AuthorViewReadList view) {
        String id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public AuthorViewReadOne update(@PathVariable String id, @RequestBody AuthorViewReadList view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}
