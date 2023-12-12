package pibackend.domain.author.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.author.model.view.AuthorViewCreate;
import pibackend.domain.author.model.view.AuthorViewReadList;
import pibackend.domain.author.model.view.AuthorViewReadOne;
import pibackend.domain.author.service.AuthorService;
import pibackend.infrastructure.export.AuthorExcelExporter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/author", produces = "application/json")
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    public Page<AuthorViewReadList> getPage(
            Pageable pageable,
            @RequestParam(required = false) String filter) {
        if (filter != null) {
            return service.getPageFiltered(pageable, filter);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public AuthorViewReadOne getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public AuthorViewCreate create(@RequestBody AuthorViewCreate view) {
        return service.create(view);
    }

    @PutMapping("/{id}")
    public AuthorViewReadOne update(@PathVariable String id, @RequestBody AuthorViewCreate view) {
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
        var headerValue = "attachment; filename=authors_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        var authors = service.getList();
        var excelExporter = new AuthorExcelExporter(authors);
        excelExporter.export(response);
    }

}
