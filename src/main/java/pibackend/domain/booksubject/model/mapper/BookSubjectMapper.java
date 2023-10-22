package pibackend.domain.booksubject.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.domain.booksubject.model.view.BookSubjectView;

@Mapper
public interface BookSubjectMapper {

    BookSubjectView toView(BookSubject entity);

    BookSubject toEntity(BookSubjectView view);

    BookSubject toEntity(@MappingTarget BookSubject entity, BookSubjectView view);
}
