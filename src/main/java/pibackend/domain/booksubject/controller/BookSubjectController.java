package pibackend.domain.booksubject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import pibackend.domain.booksubject.model.view.BookSubjectView;
import pibackend.domain.booksubject.service.BookSubjectService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book_subject", produces = "application/json")
public class BookSubjectController {

    private final BookSubjectService service;

    @GetMapping
    public List<BookSubjectView> getList() {
        return service.getList();
    }

    @GetMapping("/{id}")
    public BookSubjectView getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookSubjectView create(BookSubjectView view) {
        Long id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookSubjectView update(@PathVariable Long id, BookSubjectView view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
