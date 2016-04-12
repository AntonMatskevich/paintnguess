package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

/**
 * Created by Anton on 12.04.16.
 */
@Entity
public class Room extends Model {

    @Id
    public Long id;
    public String name;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<Player> members = new ArrayList<Player>();

    public Room(String name, Player owner) {
        this.name = name;
        this.members.add(owner);
    }

    public static Model.Finder<Long, Room> find = new Model.Finder(Long.class, Room.class);

    public static Room create(String name, String owner) {
        Room room = new Room(name, Player.find.ref(owner));
        room.save();
        room.saveManyToManyAssociations("members");
        return room;
    }

    public static List<Room> findInvolving(String player) {
        return find.where()
                .eq("members.playerName", player)
                .findList();
    }

}
