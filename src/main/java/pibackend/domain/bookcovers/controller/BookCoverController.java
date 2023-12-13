package pibackend.domain.bookcovers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadList;
import pibackend.domain.bookcovers.service.BookCoverService;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book-cover", produces = "application/json")
public class BookCoverController {

    private final BookCoverService service;

    @GetMapping("/get/{bookId}")
    public Page<BookCoverViewReadList> getPage(@PathVariable String bookId, Pageable pageable) {
        return service.getPage(bookId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getOne(@PathVariable Long id) throws IOException {
        return service.getOne(id);
    }

    @PostMapping(value = "/add/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String create(@PathVariable String bookId, @RequestParam String fileName, @RequestParam("image") MultipartFile file) throws IOException {
        return service.create(bookId, fileName, file);
    }

//    @PutMapping("/{id}")
//    public BookCoverViewReadOne update(@PathVariable Long id, @RequestBody BookCoverViewReadOne view) {
//        service.update(id, view);
//        return service.getOne(id);
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
