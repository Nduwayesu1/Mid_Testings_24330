package Models;

import Enums.GENDER;

import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public abstract class Person {

    @Id
    private String person_Id;

    private String first_name;
    private String last_name;

    @Enumerated(EnumType.STRING)
    private GENDER gender;

    private String phone_number;


}
