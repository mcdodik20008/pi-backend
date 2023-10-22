package pibackend.domain.customer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pibackend.domain.customer.model.view.CustomerView;
import pibackend.domain.customer.service.CustomerService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customer", produces = "application/json")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public List<CustomerView> getList() {
        return service.getList();
    }

    @GetMapping("/{id}")
    public CustomerView getOne(@PathVariable String id) {
        return service.getOne(id);
    }

    @PostMapping
    public CustomerView create(CustomerView view) {
        String id = service.create(view);
        return service.getOne(id);
    }

    @PutMapping("/{id}")
    public CustomerView update(@PathVariable String id, CustomerView view) {
        service.update(id, view);
        return service.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

}
