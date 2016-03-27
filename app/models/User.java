package models;

import javax.persistence.*;

/**
 * Created by Anton on 25.03.16.
 */
@Entity
//@Table(name="user", schema = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public String id;

    public String userName;

}
