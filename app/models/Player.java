package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import java.util.List;

@Entity
public class Player extends Model {

    @Id
    public String name;
    public String password;

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static Finder<String,Player> find = new Finder<String,Player>(
            String.class, Player.class
    );

    public static List<Player> all() {
        return find.all();
    }

    public static Player create(String name, String password) {
        Player player = new Player(name, password);
        player.save();
        return player;
    }

    public static Player authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }
}