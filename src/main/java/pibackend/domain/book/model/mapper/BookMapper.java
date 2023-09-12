package pibackend.domain.book.model.mapper;

import org.mapstruct.Mapper;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.book.model.view.BookViewReadList;

@Mapper
public interface BookMapper {

    BookViewReadList toViewReadOne(Book entity);

    BookViewReadList toViewReadList(Book entity);

    Book toEntity(BookViewReadList view);

}
