package pibackend.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pibackend.domain.book.model.view.BookViewReadList;
import pibackend.domain.book.service.BookService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book", produces = "application/json")
public class BookController {

    private final BookService service;

    @GetMapping
    public List<BookViewReadList> getList() {
        return service.getList();
    }

}
