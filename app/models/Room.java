package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antos on 19.04.16.
 */
@Entity
public class Room extends Model {

    @Id
    public Long id;
    public String name;
    @OneToOne(cascade = CascadeType.REMOVE)
    public List<Player> members = new ArrayList<Player>();

    public Room(String name, Player owner) {
        this.name = name;
        this.members.add(owner);
    }

    public static Model.Finder<Long, Room> find = new Model.Finder<Long, Room>(Long.class, Room.class);

    public static Room create(String name, String owner) {
        Room room = new Room(name, Player.find.ref(owner));
        room.save();
        return room;
    }



    public static List<Room> findInvolving(String player) {
        return find.where().eq("members.name", player).findList();
    }

}
