package pibackend.domain.customer.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import pibackend.domain.customer.model.entity.Customer;
import pibackend.domain.customer.model.entity.QCustomer;
import pibackend.domain.customer.model.mapper.CustomerMapper;
import pibackend.domain.customer.model.view.CustomerView;
import pibackend.domain.customer.repository.CustomerRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    public Page<CustomerView> getPageByIdLike(Pageable pageable, String id) {
        BooleanExpression expression = QCustomer.customer.id.like(id);
        return repository.findAll(expression, pageable).map(mapper::toView);
    }

    public Page<CustomerView> getPageByNameLike(Pageable pageable, String name) {
        BooleanExpression expression = QCustomer.customer.name.like(name);
        return repository.findAll(expression, pageable).map(mapper::toView);
    }

    public Page<CustomerView> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toView);
    }

    public CustomerView getOne(String id) {
        return mapper.toView(getObject(id));
    }

    public Customer getObject(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Не найден клиент с идентификатором: " + id));
    }

    public void create(CustomerView view) {
        var entity = mapper.toEntity(view);
        repository.save(entity);
    }

    public void update(String id, CustomerView view) {
        var entity = mapper.toEntity(getObject(id), view);
        repository.save(entity);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

}
