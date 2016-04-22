package models;


import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Anton on 19.04.16.
 */

@Entity
public class Player extends Model {

    @Id
    public String name;
    public String email;
    public String password;

    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static Finder<String, Player> find = new Finder<String, Player>(String.class, Player.class);

    public static Player authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }

}
