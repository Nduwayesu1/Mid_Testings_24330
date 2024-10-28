package Models;

import Enums.ROLE;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;
@Entity
public class User {

    private  String password;
    private String username;
    @Enumerated(EnumType.STRING)
    private ROLE role_type;
    private UUID  village_Id;

}
