package pibackend.domain.booksubject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.booksubject.model.view.BookSubjectView;
import pibackend.domain.booksubject.service.BookSubjectService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book_subject", produces = "application/json")
public class BookSubjectController {

    private final BookSubjectService service;

    @GetMapping
    public Page<BookSubjectView> getPage(Pageable pageable,
                                         @RequestParam(required = false) String filter) {
        if (filter != null) {
            return service.getPageFiltered(pageable, filter);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public BookSubjectView getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookSubjectView create(@RequestBody BookSubjectView view) {
        Long id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookSubjectView update(@PathVariable Long id, @RequestBody BookSubjectView view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
