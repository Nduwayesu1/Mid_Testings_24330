package Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany; // Importing OneToMany
import java.util.List;
import java.util.UUID;

@Entity
public class Room {

    @Id
    private UUID room_Id;
    private String room_name;
    private String room_location;

    @OneToMany(mappedBy = "room")
    private List<Shelf> shelves;

}

