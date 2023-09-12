package pibackend.domain.author.model.mapper;

import org.mapstruct.Mapper;
import pibackend.domain.author.model.entity.Author;
import pibackend.domain.author.model.view.AuthorView;

@Mapper
public interface AuthorMapper {

    AuthorView toView(Author entity);

    Author toEntity(AuthorView view);

}
