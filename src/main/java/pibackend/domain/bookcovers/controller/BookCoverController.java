package pibackend.domain.bookcovers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import pibackend.domain.bookcovers.model.view.BookCoverViewReadList;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadOne;
import pibackend.domain.bookcovers.service.BookCoverService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book-cover", produces = "application/json")
public class BookCoverController {

    private final BookCoverService service;

    @GetMapping
    public Page<BookCoverViewReadList> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public BookCoverViewReadOne getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookCoverViewReadOne create(@RequestBody BookCoverViewReadOne view) {
        Long id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookCoverViewReadOne update(@PathVariable Long id, @RequestBody BookCoverViewReadOne view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
