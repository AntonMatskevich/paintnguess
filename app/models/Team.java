package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Anton on 12.04.16.
 */
@Entity
public class Team extends Model {

    @Id
    public Long id;
    public String title;
    public Player memberOf;
    public Room room;

    public static Finder<Long, Team> find = new Finder(Long.class, Team.class);

    public static List<Team> findTeamMember(String player) {
        return find.fetch("room").where()
                .eq("room.members.playerName", player).findList();
    }

    public static Team create(Team team, Long room) {
        team.room = Room.find.ref(room);
        team.save();
        return team;
    }

}
