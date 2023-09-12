package pibackend.domain.booksubject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
