package pibackend.domain.book.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "book")
public class Book {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "first_publish_date")
    private String firstPublishDate;

    @Column(name = "description")
    private String description;

}
