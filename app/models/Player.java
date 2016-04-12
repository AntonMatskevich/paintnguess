package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Anton on 12.04.16.
 */
@Entity
public class Player extends Model {

    @Id
    public String playerName;
    public String password;

    public Player(String playerName, String password) {
        this.playerName = playerName;
        this.password = password;
    }

    public static Finder<String, Player> find = new Finder<String, Player>(String.class, Player.class);

}
