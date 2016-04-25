package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by Antos on 19.04.16.
 */
@Entity
public class Team extends Model {

    @Id
    @Constraints.Required
    public String title;
    public Long id;
//    @ManyToOne
//    public Player memberOf;
    @ManyToMany
    public Long room;

    public static Long idCounter = Long.valueOf(0);

    public Team(String title, Long room) {
        this.title = title;
        this.room = room;
    }

    public static Model.Finder<String, Team> find = new Model.Finder<String, Team>(String.class, Team.class);

//    public static List<Team> findMembershipInvolving(String player) {
//        return find.fetch("room").where().eq("room.members.name", player).findList();
//    }

    public static Team create(Team team, Long room) {
        team.id = idCounter++;
        team.room = room;
        team.save();
        return team;
    }

    public static void delete(Long id) {
        for(Team t : find.all()) {
            if(t.id == id) {
                find.ref(t.title).delete();
            }
        }
    }

}
