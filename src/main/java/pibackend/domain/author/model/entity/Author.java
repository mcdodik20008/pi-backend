package pibackend.domain.author.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pibackend.domain.book.model.entity.Book;
import pibackend.infrastructure.StringPrefixedSequenceIdGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorId")
    @GenericGenerator(
            name = "authorId",
            strategy = "pibackend.infrastructure.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "author_id_sqns"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "C")})
    private String uuid;

    @Column(name = "author_name", nullable = false)
    private String name;

    @Column(name = "bio")
    private String bio;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "death_date")
    private String deathDate;

    @Column(name = "wikipedia")
    private String wikipedia;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

}
