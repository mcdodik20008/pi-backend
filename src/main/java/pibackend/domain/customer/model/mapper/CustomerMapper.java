package pibackend.domain.customer.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.customer.model.entity.Customer;
import pibackend.domain.customer.model.view.CustomerView;

@Mapper
public interface CustomerMapper {

    CustomerView toView(Customer entity);

    Customer toEntity(CustomerView view);

    Customer toEntity(@MappingTarget Customer entity, CustomerView view);

}
