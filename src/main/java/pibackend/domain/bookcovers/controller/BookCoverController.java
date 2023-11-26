package pibackend.domain.bookcovers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.bookcovers.model.view.BookCoverView;
import pibackend.domain.bookcovers.service.BookCoverService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book-cover", produces = "application/json")
public class BookCoverController {

    private final BookCoverService service;

    @GetMapping
    public Page<BookCoverView> getPage(Pageable pageable) {
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public BookCoverView getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookCoverView create(@RequestBody BookCoverView view) {
        Long id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookCoverView update(@PathVariable Long id, @RequestBody BookCoverView view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
