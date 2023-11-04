package pibackend.domain.customer.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestBody;
import pibackend.domain.customer.model.entity.Customer;
import pibackend.domain.customer.model.mapper.CustomerMapper;
import pibackend.domain.customer.model.view.CustomerView;
import pibackend.domain.customer.model.view.CustomerViewList;
import pibackend.domain.customer.repository.CustomerRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    public Page<CustomerViewList> getPageByIdLike(Pageable pageable, String id) {
        return repository.findByIdContaining(id, pageable).map(mapper::toViewList);
    }

    public Page<CustomerViewList> getPageByNameLike(Pageable pageable, String name) {
        return repository.findByNameContainingIgnoreCase(name, pageable).map(mapper::toViewList);
    }

    public Page<CustomerViewList> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toViewList);
    }

    public CustomerView getOne(String id) {
        return mapper.toView(getObject(id));
    }

    public Customer getObject(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найден клиент с идентификатором: " + id));
    }

    public void create(@RequestBody CustomerView view) {
        var entity = mapper.toEntity(view);
        repository.save(entity);
    }

    public void update(String id, @RequestBody CustomerView view) {
        var entity = mapper.toEntity(getObject(id), view);
        repository.save(entity);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

}
