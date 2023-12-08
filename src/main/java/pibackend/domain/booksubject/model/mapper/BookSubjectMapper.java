package pibackend.domain.booksubject.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.domain.booksubject.model.view.BookSubjectViewReadList;
import pibackend.domain.booksubject.model.view.BookSubjectViewReadOne;

@Mapper
public interface BookSubjectMapper {

    BookSubjectViewReadOne toViewReadOne(BookSubject entity);

    BookSubjectViewReadList toViewReadList(BookSubject entity);

    BookSubject toEntity(BookSubjectViewReadOne view);

    BookSubject toEntity(@MappingTarget BookSubject entity, BookSubjectViewReadOne view);

    BookSubject toEntity(@MappingTarget BookSubject entity, BookSubjectViewReadList view);
}
