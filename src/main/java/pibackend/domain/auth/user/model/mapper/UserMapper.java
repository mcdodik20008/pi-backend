package pibackend.domain.auth.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.auth.user.model.entity.User;
import pibackend.domain.auth.user.model.view.UserChangePassword;
import pibackend.domain.auth.user.model.view.UserView;

@Mapper
public interface UserMapper {

    UserView toView(User entity);

    User toEntity(UserView view);

    User toEntity(@MappingTarget User entity, UserView view);

    User toEntity(UserChangePassword view);

}
