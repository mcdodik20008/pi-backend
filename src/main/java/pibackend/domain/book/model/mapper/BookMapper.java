package pibackend.domain.book.model.mapper;

import org.mapstruct.Mapper;
import pibackend.domain.book.model.entity.Book;
import pibackend.domain.book.model.view.BookView;

@Mapper
public interface BookMapper {

    BookView toView(Book entity);

    Book toEntity(BookView view);

}
