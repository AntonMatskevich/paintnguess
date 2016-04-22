package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Antos on 19.04.16.
 */
@Entity
public class Team extends Model {

    @Id
    public Long id;
    public String title;
    @ManyToOne
    public Player memberOf;
    @ManyToOne
    public Room room;

    public static Model.Finder<Long, Team> find = new Model.Finder<Long, Team>(Long.class, Team.class);

    public static List<Team> findMembershipInvolving(String player) {
        return find.fetch("room").where().eq("room.members.name", player).findList();
    }

    public static Team create(Team team/*, Long room*/) {
//        team.room = Room.find.ref(room);
        team.save();
        return team;
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

}
