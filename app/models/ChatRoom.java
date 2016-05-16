package models;

import play.mvc.*;
import play.libs.*;
import play.libs.F.*;

import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import akka.actor.*;
import static akka.pattern.Patterns.ask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;



import java.util.*;

import static java.util.concurrent.TimeUnit.*;

/**
 * A chat room is an Actor.
 */
public class ChatRoom extends UntypedActor {

    static ActorRef defaultRoom = Akka.system().actorOf(Props.create(ChatRoom.class));

    public static void join(final String username, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) throws Exception{

        String result = (String)Await.result(ask(defaultRoom,new Join(username, out), 1000), Duration.create(1, SECONDS));

        if("OK".equals(result)) {

            in.onMessage(new Callback<JsonNode>() {
                public void invoke(JsonNode event) {
                    defaultRoom.tell(new Talk(username, event.get("text").asText()), null);
                }
            });
            in.onClose(new Callback0() {
                public void invoke() {
                    defaultRoom.tell(new Quit(username), null);
                }
            });
        } else {
            ObjectNode error = Json.newObject();
            error.put("error", result);
            out.write(error);
        }
    }
    Map<String, WebSocket.Out<JsonNode>> members = new HashMap<String, WebSocket.Out<JsonNode>>();

    public void onReceive(Object message) throws Exception {
        if(message instanceof Join) {
            Join join = (Join)message;
            if(members.containsKey(join.username)) {
                getSender().tell("This username is already used", getSelf());
            } else {
                members.put(join.username, join.channel);
                notifyAll("join", join.username, "has entered the room");
                getSender().tell("OK", getSelf());
            }

        } else if(message instanceof Talk)  {
            Talk talk = (Talk)message;
            notifyAll("talk", talk.username, talk.text);
        } else if(message instanceof Quit)  {
            Quit quit = (Quit)message;
            members.remove(quit.username);
            notifyAll("quit", quit.username, "has left the room");
        } else {
            unhandled(message);
        }
    }

    public void notifyAll(String kind, String user, String text) {
        for(WebSocket.Out<JsonNode> channel: members.values()) {
            ObjectNode event = Json.newObject();
            event.put("kind", kind);
            event.put("user", user);
            event.put("message", text);
            ArrayNode m = event.putArray("members");
            for(String u: members.keySet()) {
                m.add(u);
            }
            channel.write(event);
        }
    }

    //Messages
    public static class Join {

        final String username;
        final WebSocket.Out<JsonNode> channel;

        public Join(String username, WebSocket.Out<JsonNode> channel) {
            this.username = username;
            this.channel = channel;
        }

    }

    public static class Talk {

        final String username;
        final String text;

        public Talk(String username, String text) {
            this.username = username;
            this.text = text;
        }

    }

    public static class Quit {

        final String username;

        public Quit(String username) {
            this.username = username;
        }

    }

}
