package models;

import play.db.ebean.Model;

import javax.persistence.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.WebSocket;

/**
 * Created by Anton on 19.04.16.
 */

@Entity
public class Player extends Model {

    @Id
    public String name;
    public Long id;
    public String password;
    public String color;
    public Long size;

    public final WebSocket.Out<JsonNode> channel;

    public static Long idCounter = Long.valueOf(0);

    public Player(String name, String password, String color, Long size, WebSocket.Out<JsonNode> channel) {
        this.name = name;
        this.password = password;
        this.color = color;
        this.size = size;
        this.channel = channel;
        this.id = idCounter++;
    }

    public Player(WebSocket.Out<JsonNode> channel) {
        this.channel = channel;
    }

    public static Finder<String, Player> find = new Finder<String, Player>(String.class, Player.class);

    public static Player authenticate(String name, String password) {
        return find.where().eq("name", name)
                .eq("password", password).findUnique();
    }


    public static Player create(Player player) {
            player.id = idCounter++;
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

    public void updateFromJson(JsonNode json) {
        if(json.has("name")) {
            this.name = json.get("name").textValue();
        } if(json.has("color")) {
            this.color = json.get("color").textValue();
        } if(json.has("size")) {
            this.size = json.get("size").longValue();
        }
    }

    public JsonNode toJson() {
        ObjectNode json = Json.newObject();
        json.put("name", this.name);
        json.put("color", this.color);
        json.put("size", this.size);
        return json;
    }

    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                '}';
    }

}
