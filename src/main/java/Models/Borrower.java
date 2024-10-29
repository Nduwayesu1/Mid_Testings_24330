package Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne; // Importing ManyToOne
import java.util.Date;
import java.util.UUID;

@Entity
public class Borrower {

    @Id
    private UUID book_Id;
    private Date due_date;
    private int fine;
    private UUID userId; // Renamed for clarity
    private int late_charge_fees;
    private Date pick_up_date;
    private UUID render_Id;
    private Date return_date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;


}
