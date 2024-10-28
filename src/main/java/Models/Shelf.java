package Models;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Shelf {
    private int available_stock;
    private String book_category;
    private int borrowed_number;
    private int initial_stock;
    private UUID rooM_Id;
}
