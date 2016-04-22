package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.pages.index.render("Hallo world!"));
    }

    @Security.Authenticated(Secured.class)
    public static Result rooms() {
        return ok(views.html.pages.rooms.render(
                Room.find.all(),
                Team.find.all(),
                teamForm,
                roomForm
        ));
    }

    @Security.Authenticated(Secured.class)
    public static Result draw() {
        return ok(views.html.pages.draw.render());
    }

    public static Result about() {
        return ok(views.html.pages.about.render());
    }

    public static Result login() {
        return ok(views.html.pages.login.render(Form.form(Login.class),
                Player.find.all(),
                playerForm));
    }

    @Security.Authenticated(Secured.class)
    public static Result admin() {
        String playerName = Player.find.byId(request().username()).name;
        if (playerName.equals("admin")) {
            return ok(views.html.pages.admin.render(Player.find.all(),
                    Room.find.all(),
                    Team.find.all()));
        } else {
            return redirect(routes.Application.login());
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
                    playerForm));
        } else {
            session().clear();
            session("name", loginForm.get().name);
            return redirect(
                    routes.Application.index()
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
                            playerForm)
            );
        } else {
            Player.create(filledForm.get());
            return redirect(routes.Application.login());
        }
    }


    @Security.Authenticated(Secured.class)
    public static Result deletePlayer(Long id) {
        Player.delete(id);
        return redirect(routes.Application.login());
    }

    //room making
    static Form<Room> roomForm = Form.form(Room.class);

    @Security.Authenticated(Secured.class)
    public static Result newRoom() {
        if(Room.find.all().size() < 2) {
            Form<Room> filledForm = roomForm.bindFromRequest();
            if (filledForm.hasErrors()) {
                return badRequest(
                        views.html.pages.rooms.render(Room.find.all(),
                                Team.find.all(),
                                teamForm,
                                roomForm)
                );
            } else {
                Room.create(filledForm.get());
                return redirect(routes.Application.rooms());
            }
        } else {
            flash("error", "There is too many rooms");
            return redirect(
                    routes.Application.rooms()
            );
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result deleteRoom(Long id) {
        Room.delete(id);
        return redirect(routes.Application.admin());
    }


    //team making
    static Form<Team> teamForm = Form.form(Team.class);

    @Security.Authenticated(Secured.class)
    public static Result newTeam() {
        Form<Team> filledForm = teamForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.pages.rooms.render(Room.find.all(),
                            Team.find.all(),
                            teamForm,
                            roomForm)
            );
        } else {
            Team.create(filledForm.get());
            return redirect(routes.Application.rooms());
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result deleteTeam(Long id) {
        Team.delete(id);
        return redirect(routes.Application.admin());
    }

}
