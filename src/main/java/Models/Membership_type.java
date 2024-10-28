package Models;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Membership_type {

    private int max_books;
    private String membership_name;
    private UUID membership_type_Id;
    private int price;
}
