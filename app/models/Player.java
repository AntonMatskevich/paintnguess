package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

import static play.mvc.Controller.session;

/**
 * Created by Anton on 12.04.16.
 */
@Entity
public class Player extends Model {

    @Id
    @Constraints.Required
    public String playerName;
    @Constraints.Required
    public String password;

    public Player(String playerName, String password) {
        this.playerName = playerName;
        this.password = password;
    }

    public static Finder<String, Player> find = new Finder<String, Player>(String.class, Player.class);

    public static Player authenticate(String playerName, String password) {
        return find.where().eq("playerName", playerName)
                .eq("password", password).findUnique();
    }

    public static List<Player> all() {
        return find.all();
    }

    public static void create(Player player) {
        session().clear();
        player.save();
        session("playerName", player.playerName);
    }

//    public static void delete(Integer id) {
//        find.ref(id).delete();
//    }
}
