package models;


import controllers.routes;
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

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static Finder<String, Player> find = new Finder<String, Player>(String.class, Player.class);

    public static Player authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }


    public static Player create(Player player) {
            player.id = new Long(find.all().size() + 1);
            player.save();
            return player;
    }

    public static void delete(Long id) {
        for(Player p : find.all()) {
            if(p.id == id) {
                find.ref(p.name).delete();
            }
        }
    }

}
