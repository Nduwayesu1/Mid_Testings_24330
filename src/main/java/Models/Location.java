package Models;

import Enums.Location_type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Location {
    @Id
    private UUID location_Id;
    private String location_code;
    private String location_name;
    private Location_type location_type;
    private UUID parent_id;

    @ManyToOne
    private User user;
}
