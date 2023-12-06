package pibackend.domain.booksubject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import pibackend.domain.booksubject.model.view.BookSubjectViewReadList;
import pibackend.domain.booksubject.model.view.BookSubjectViewReadOne;
import pibackend.domain.booksubject.service.BookSubjectService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book_subject", produces = "application/json")
public class BookSubjectController {

    private final BookSubjectService service;

    @GetMapping
    public Page<BookSubjectViewReadList> getPage(Pageable pageable,
            @RequestParam(required = false) String filter) {
        if (filter != null) {
            return service.getPageFiltered(pageable, filter);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public BookSubjectViewReadOne getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookSubjectViewReadOne create(@RequestBody BookSubjectViewReadOne view) {
        Long id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookSubjectViewReadOne update(@PathVariable Long id, @RequestBody BookSubjectViewReadOne view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
