package pibackend.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.book.model.view.BookViewReadList;
import pibackend.domain.book.model.view.BookViewReadOne;
import pibackend.domain.book.service.BookService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book", produces = "application/json")
public class BookController {

    private final BookService service;

    @GetMapping
    public Page<BookViewReadList> getPage(Pageable pageable,
            @RequestParam(required = false) String filterId,
            @RequestParam(required = false) String filterTitle) {
        if (filterId != null) {
            return service.getPageByIdLike(pageable, filterId);
        }
        if (filterTitle != null) {
            return service.getPageByTitleLike(pageable, filterTitle);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public BookViewReadOne getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookViewReadOne create(BookViewReadList view) {
        String id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookViewReadOne update(@PathVariable String id, BookViewReadList view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}
