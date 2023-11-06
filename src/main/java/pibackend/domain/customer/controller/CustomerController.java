package pibackend.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.customer.model.view.CustomerView;
import pibackend.domain.customer.model.view.CustomerViewList;
import pibackend.domain.customer.service.CustomerService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer", produces = "application/json")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public Page<CustomerViewList> getPage(Pageable pageable,
                                          @RequestParam(required = false) String filterId,
                                          @RequestParam(required = false) String filterName) {
        if (filterId != null) {
            return service.getPageByIdLike(pageable, filterId);
        }
        if (filterName != null) {
            return service.getPageByNameLike(pageable, filterName);
        }
        return service.getPage(pageable);
    }

    @GetMapping("/{id}")
    public CustomerView getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public void create(@RequestBody CustomerView view) {
        service.create(view);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody CustomerView view) {
        service.update(id, view);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}
