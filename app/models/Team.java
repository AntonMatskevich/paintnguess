package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Team extends Model {

    @Id
    public Long id;
    public String title;
    public boolean done = false;
    public Date dueDate;
    @ManyToOne
    public Player assignedTo;
    public String folder;
    @ManyToOne
    public Room room;

    public static Model.Finder<Long,Team> find = new Model.Finder(Long.class, Team.class);

    public static List<Team> findTodoInvolving(String player) {
        return find.fetch("room").where()
                .eq("done", false)
                .eq("room.members.name", player)
                .findList();
    }

    public static Team create(Team team, Long room, String folder) {
        team.room = Room.find.ref(room);
        team.folder = folder;
        team.save();
        return team;
    }
}