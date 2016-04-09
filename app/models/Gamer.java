package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Anton on 25.03.16.
 */
@Entity
public class Gamer extends Model{

    @Id
    public Integer id;
    @Constraints.Required
    public String name;

    public static Finder<Integer, Gamer> find = new Finder<Integer, Gamer>(Integer.class, Gamer.class);

    public static List<Gamer> all() {
        return find.all();
    }

    public static void create(Gamer gamer) {
        gamer.save();
    }

    public static void delete(Integer id) {
        find.ref(id).delete();
    }

    public static Gamer authenticate(String name) {
        return find.where().eq("name", name).findUnique();
    }

}
