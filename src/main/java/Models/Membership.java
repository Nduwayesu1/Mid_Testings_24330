package Models;

import Enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;
@Entity
public class Membership {


    @Id
    private UUID membership_id;
    private Date  expring_time;
    private String membership_code;
    private int membership_type;
    @Enumerated(EnumType.STRING)
    private Status membership_status;
    private UUID reader_Id;
    private  Date registration_date;

}
