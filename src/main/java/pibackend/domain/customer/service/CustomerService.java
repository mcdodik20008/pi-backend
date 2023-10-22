package pibackend.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pibackend.domain.customer.model.entity.Customer;
import pibackend.domain.customer.model.mapper.CustomerMapper;
import pibackend.domain.customer.model.view.CustomerView;
import pibackend.domain.customer.repository.CustomerRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    public List<CustomerView> getList() {
        return repository.findAll().stream()
                .map(mapper::toView)
                .toList();
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
