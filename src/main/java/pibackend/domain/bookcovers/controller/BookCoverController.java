package pibackend.domain.bookcovers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import pibackend.domain.bookcovers.model.view.BookCoverView;
import pibackend.domain.bookcovers.service.BookCoverService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book-cover", produces = "application/json")
public class BookCoverController {

    private final BookCoverService service;

    @GetMapping
    public List<BookCoverView> getList() {
        return service.getList();
    }

    @GetMapping("/{id}")
    public BookCoverView getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookCoverView create(BookCoverView view) {
        Long id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookCoverView update(@PathVariable Long id, BookCoverView view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
