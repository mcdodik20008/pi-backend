package pibackend.domain.booksubject.model.entity;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "book_subject")
public class BookSubject {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "subject", nullable = false)
    private String subject;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}
