package Models;

import Enums.GENDER;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Person {
    private String person_Id;
    private String first_name;
    private  String last_name;
    @Enumerated(EnumType.STRING)
    private GENDER  gender;
    private String phone_number;
}
