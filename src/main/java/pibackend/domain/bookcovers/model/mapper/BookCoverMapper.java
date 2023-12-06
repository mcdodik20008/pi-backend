package pibackend.domain.bookcovers.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.bookcovers.model.entity.BookCover;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadList;
import pibackend.domain.bookcovers.model.view.BookCoverViewReadOne;

@Mapper
public interface BookCoverMapper {

    BookCoverViewReadOne toViewReadOne(BookCover entity);
    BookCoverViewReadList toViewReadList(BookCover entity);

    BookCover toEntity(BookCoverViewReadOne view);
    BookCover toEntity(BookCoverViewReadList view);

    BookCover toEntity(@MappingTarget BookCover entity, BookCoverViewReadOne view);
    BookCover toEntity(@MappingTarget BookCover entity, BookCoverViewReadList view);

}
