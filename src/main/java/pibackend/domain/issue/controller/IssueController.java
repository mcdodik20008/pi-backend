package pibackend.domain.issue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.issue.model.view.IssueView;
import pibackend.domain.issue.service.IssueService;

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

}
