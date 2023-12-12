package pibackend.domain.author.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.author.model.entity.Author;
import pibackend.domain.author.model.view.AuthorViewCreate;
import pibackend.domain.author.model.view.AuthorViewReadList;
import pibackend.domain.author.model.view.AuthorViewReadOne;

@Mapper
public interface AuthorMapper {

    AuthorViewReadList toViewReadList(Author entity);

    AuthorViewReadOne toViewReadOne(Author entity);

    AuthorViewCreate toViewCreate(Author entity);

    Author toEntity(AuthorViewReadOne view);

    Author toEntity(AuthorViewReadList view);

    Author toEntity(AuthorViewCreate view);

    Author toEntity(@MappingTarget Author entity, AuthorViewCreate view);

    Author toEntity(@MappingTarget Author entity, AuthorViewReadOne view);

    Author toEntity(@MappingTarget Author entity, AuthorViewReadList view);

}
