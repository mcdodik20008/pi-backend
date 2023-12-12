package pibackend.domain.book.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.book.model.view.BookViewCreate;
import pibackend.domain.book.model.view.BookViewReadList;
import pibackend.domain.book.model.view.BookViewReadOne;

@Mapper
public interface BookMapper {

    BookViewReadOne toViewReadOne(Book entity);

    BookViewReadList toViewReadList(Book entity);

    Book toEntity(BookViewReadList view);

    Book toEntity(BookViewReadOne view);

    Book toEntity(BookViewCreate view);

    Book toEntity(@MappingTarget Book entity, BookViewCreate view);

    Book toEntity(@MappingTarget Book entity, BookViewReadList view);

    Book toEntity(@MappingTarget Book entity, BookViewReadOne view);

}
