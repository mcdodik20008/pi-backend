package pibackend.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.customer.model.view.CustomerView;
import pibackend.domain.customer.model.view.CustomerViewList;
import pibackend.domain.customer.service.CustomerService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer", produces = "application/json")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public Page<CustomerViewList> getPage(Pageable pageable) {
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
