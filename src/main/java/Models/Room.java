package Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Room {
    @Id
    private String room_code;
    private UUID room_Id;
}
