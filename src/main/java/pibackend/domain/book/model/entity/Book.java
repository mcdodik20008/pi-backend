package pibackend.domain.book.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pibackend.domain.author.model.entity.Author;
import pibackend.domain.bookcovers.model.entity.BookCover;
import pibackend.domain.booksubject.model.entity.BookSubject;
import pibackend.infrastructure.StringPrefixedSequenceIdGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookId")
    @GenericGenerator(
            name = "bookId",
            strategy = "pibackend.infrastructure.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "book_id_sqns"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "B")})
    @Column(name = "id")
    private String uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "first_publish_date")
    private String firstPublishDate;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private List<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<BookCover> covers;

    @OneToMany(mappedBy = "book")
    private List<BookSubject> subjects;

}
