package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.pages.index.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result rooms() {
        return ok(views.html.pages.rooms.render(
                Player.find.byId(request().username())
        ));
    }

    @Security.Authenticated(Secured.class)
    public static Result draw() {
        return ok(views.html.pages.draw.render(
                Player.find.byId(request().username())
        ));
    }

    public static Result about() {
        return ok(views.html.pages.about.render());
    }

    public static Result login() {
        return ok(views.html.pages.login.render(
                Form.form(Login.class),
                Player.find.all(),
                playerForm
        ));
    }

    @Security.Authenticated(Secured.class)
    public static Result admin() {
        String playerName = Player.find.byId(request().username()).name;
        if (!playerName.equals("admin")) {
            return redirect(routes.Application.login());
        } else {
            return ok(views.html.pages.admin.render(
                    Player.find.all(),
                    Player.find.byId(request().username())
            ));
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }

    public static class Login {

        public String name;
        public String password;

        public String validate() {
            if (Player.authenticate(name, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(views.html.pages.login.render(loginForm,
                    Player.find.all(),
                    playerForm
            ));
        } else {
            session().clear();
            session("name", loginForm.get().name);
            return redirect(
                    routes.Application.rooms()
            );
        }
    }

    static Form<Player> playerForm = Form.form(Player.class);

    public static Result newPlayer() {
        Form<Player> filledForm = playerForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.pages.login.render(Form.form(Login.class),
                            Player.find.all(),
                            playerForm
                    ));
        } else {
            Player.create(filledForm.get());
            return redirect(routes.Application.login());
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result deletePlayer(Long id) {
        Player.delete(id);
        return redirect(routes.Application.admin());
    }

    ////////Painter
    static Room room = new Room("Room #1");

    public static WebSocket<JsonNode> stream() {
        return new WebSocket<JsonNode>() {
            @Override
            public void onReady(In<JsonNode> in, Out<JsonNode> out) {
                try{
                    room.createPainter(in, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
