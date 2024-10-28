package Models;

import Enums.Book_status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;
@Entity
public class Book {

    @Id
    private UUID book_Id;;
    @Enumerated(EnumType.STRING)
    private Book_status book_status;
    private int edition;
    private String ISBNcode;
    private Date publication_year_date;
    private  String publisher_name;
    private UUID shelf_Id;
    private String title;
}
