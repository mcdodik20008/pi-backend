package pibackend.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.book.model.view.BookViewReadList;
import pibackend.domain.book.model.view.BookViewReadOne;
import pibackend.domain.book.service.BookService;
import pibackend.infrastructure.export.BookExcelExporter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/book", produces = "application/json")
public class BookController {

    private final BookService service;

    @GetMapping
    public Page<BookViewReadList> getPage(Pageable pageable,
            @RequestParam(required = false) String filter) {
        if (filter != null) {
            return service.getPageFiltered(pageable, filter);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public BookViewReadOne getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public BookViewReadOne create(@RequestBody BookViewReadOne view) {
        String id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public BookViewReadOne update(@PathVariable String id, @RequestBody BookViewReadOne view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        var currentDateTime = dateFormatter.format(new Date());

        var headerKey = "Content-Disposition";
        var headerValue = "attachment; filename=books_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        var books = service.getList();
        var excelExporter = new BookExcelExporter(books);
        excelExporter.export(response);
    }

}
