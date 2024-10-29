package Models;

import Enums.Book_status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne; // Importing ManyToOne
import javax.persistence.OneToMany; // Importing OneToMany
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Book {

    @Id
    private UUID book_Id;
    private int edition;
    private String ISBNcode;
    private Date publication_year_date;
    private String publisher_name;

    @Enumerated(EnumType.STRING)
    private Book_status book_status;

    @ManyToOne // Establishing Many-to-One relationship with Shelf
    private Shelf shelf;

    private String title;

    @OneToMany(mappedBy = "book")
    private List<Borrower> borrowers;


}
