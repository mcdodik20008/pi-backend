package pibackend.domain.author.model.mapper;

import org.mapstruct.Mapper;
import pibackend.domain.author.model.entity.Author;
import pibackend.domain.author.model.view.AuthorViewReadOne;

@Mapper
public interface AuthorMapper {

    AuthorViewReadOne toView(Author entity);

    Author toEntity(AuthorViewReadOne view);

}
