package pibackend.domain.author.model.entity;

import lombok.Getter;
import lombok.Setter;
import pibackend.domain.book.model.entity.Book;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "author")
public class Author {

    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "name", nullable = false)
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
