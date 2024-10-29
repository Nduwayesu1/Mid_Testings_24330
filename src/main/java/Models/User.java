package Models;

import Enums.ROLE;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class User  extends Person {

    @Id
    private UUID userId;
    private String password;
    private String username;

    @Enumerated(EnumType.STRING)
    private ROLE role_type;

    private UUID village_Id;

    @OneToMany(mappedBy = "user")
    private List<Membership> memberships;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    @OneToMany(mappedBy = "user")
    private List<Borrower> borrowers;
}
