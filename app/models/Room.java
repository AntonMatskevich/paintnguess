package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.mvc.WebSocket;

/**
 * Created by Antos on 19.04.16.
 */
@Entity
public class Room extends Model {

    @Id
    public Long id;
    @Constraints.Required
    public String name;
    public Map<Integer, Painter> painters = new ConcurrentHashMap<Integer, Painter>();
    public AtomicInteger counter = new AtomicInteger(0);
    public AtomicInteger connections = new AtomicInteger(0);

    public Room(String name) {
        this.name = name;
    }

    public static Model.Finder<Long, Room> find = new Model.Finder<Long, Room>(Long.class, Room.class);

    public static Room create(Room room) {
        room.save();
        return room;
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }


    public void createPainter(final WebSocket.In<JsonNode> in, final WebSocket.Out<JsonNode> out) {
        counter.incrementAndGet();
        connections.incrementAndGet();
        final int pid = counter.intValue(); // the painter id

        // in: handle messages from the painter
        in.onMessage(new F.Callback<JsonNode>() {
            @Override
            public void invoke(JsonNode json) throws Throwable {
                String type = json.get("type").textValue();

                // The painter wants to change some of his property
                if("change".equals(type)) {
                    Painter painter = painters.get(pid);
                    if(painter == null) {
                        painter = new Painter(out);
                        painters.put(pid, painter);

                        // Inform the painter who he is (which pid, he can them identify himself)
                        ObjectNode self = Json.newObject();
                        self.put("type", "youAre");
                        self.put("pid", pid);
                        painter.channel.write(self);

                        // Inform the list of other painters
                        for(Map.Entry<Integer, Painter> entry : painters.entrySet()) {
                            ObjectNode other = (ObjectNode)entry.getValue().toJson();
                            other.put("pid", entry.getKey());
                            painter.channel.write(other);
                        }
                    }
                    painter.updateFromJson(json);
                }

                ObjectNode node = ((ObjectNode)json);
                node.put("pid", pid);
                Room.this.notifyAll(node);
            }
        });

        // User has disconnected.
        in.onClose(new F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                painters.remove(pid);
                connections.decrementAndGet();

                ObjectNode json = Json.newObject();
                json.put("type", "disconnect");
                json.put("pid", pid);

                Room.this.notifyAll(json);

                Logger.debug("(pid:"+pid+") disconnected.");
                Logger.info(connections+" painter(s) currently connected.");
            }
        });

        Logger.debug("(pid:"+pid+") connected.");
        Logger.info(connections+" painter(s) currently connected.");
    }

    public void notifyAll(JsonNode json) {
        for(Painter painter : painters.values()) {
            painter.channel.write(json);
        }
    }

}
