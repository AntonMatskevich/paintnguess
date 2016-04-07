package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Anton on 25.03.16.
 */
@Entity
public class User extends Model{

    @Id
    public Integer id;
    @Constraints.Required
    public String userName;

    public static Finder<Integer, User> find = new Finder<Integer, User>(Integer.class, User.class);

    public static List<User> all() {
        return find.all();
    }

    public static void create(User user) {
        user.save();
    }

    public static void delete(Integer id) {
        find.ref(id).delete();
    }

}
