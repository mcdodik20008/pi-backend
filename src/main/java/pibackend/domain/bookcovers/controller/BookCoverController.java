package pibackend.domain.bookcovers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
