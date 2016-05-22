package models;


import play.db.ebean.Model;

import javax.persistence.*;

import static play.mvc.Controller.flash;

/**
 * Created by Anton on 19.04.16.
 */

@Entity
public class Player extends Model {

    @Id
    public String name;
    public Long id;
    public String password;
    public int points;

    public static Long idCounter = Long.valueOf(0);

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
        points = 0;
    }

    public static Finder<String, Player> find = new Finder<String, Player>(String.class, Player.class);

    public static Player authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }


    public static Player create(Player player) {
            player.id = idCounter++;
        if(find.where().eq("name", player.name).findUnique() == null) {
            player.save();
            return player;
        } else {
            flash("existing_player", "This username already exists!");
            return find.where().eq("name", player.name)
                    .eq("password", player.password).findUnique();
        }
    }

    public static void delete(Long id) {
        for(Player p : find.all()) {
            if(p.id == id) {
                find.ref(p.name).delete();
            }
        }
    }

}
