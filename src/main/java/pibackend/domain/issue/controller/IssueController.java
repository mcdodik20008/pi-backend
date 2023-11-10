package pibackend.domain.issue.controller;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.issue.model.view.IssueView;
import pibackend.domain.issue.service.IssueService;
import pibackend.infrastructure.export.IssueExcelExporter;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/issue", produces = "application/json")
public class IssueController {

    private final IssueService service;

    @GetMapping
    public Page<IssueView> getPage(Pageable pageable,
                                   @RequestParam(required = false) Long filterId) {
        if (filterId != null) {
            return service.getPageByIdLike(pageable, filterId);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public IssueView getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping
    public IssueView create(@RequestBody IssueView view) {
        Long id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public IssueView update(@PathVariable Long id, @RequestBody IssueView view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        var currentDateTime = dateFormatter.format(new Date());

        var headerKey = "Content-Disposition";
        var headerValue = "attachment; filename=issues_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        var issues = service.getList();
        var excelExporter = new IssueExcelExporter(issues);
        excelExporter.export(response);
    }

}
