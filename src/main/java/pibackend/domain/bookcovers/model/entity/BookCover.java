package pibackend.domain.bookcovers.model.entity;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "book_cover")
public class BookCover {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cover_file", nullable = false)
    private String coverFile;

    @Column(name = "path", nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
