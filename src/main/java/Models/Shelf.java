package Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany; // Importing OneToMany
import javax.persistence.ManyToOne; // Importing ManyToOne
import java.util.List;
import java.util.UUID;

@Entity
public class Shelf {

    @Id
    private UUID shelf_Id;
    private int available_stock;
    private String book_category;
    private int borrowed_number;
    private int initial_stock;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "shelf")
    private List<Book> books;

}
