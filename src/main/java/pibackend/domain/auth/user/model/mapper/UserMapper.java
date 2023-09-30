package pibackend.domain.auth.user.model.mapper;

import org.mapstruct.Mapper;
import pibackend.domain.auth.user.model.entity.User;
import pibackend.domain.auth.user.model.view.UserView;

@Mapper
public interface UserMapper {

    UserView toView(User entity);

    User toEntity(UserView view);

}
