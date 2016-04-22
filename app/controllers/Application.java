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
                teamForm
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
        return ok(views.html.pages.login.render(Form.form(Login.class)));
    }

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
            return badRequest(views.html.pages.login.render(loginForm));
        } else {
            session().clear();
            session("name", loginForm.get().name);
            return redirect(
                    routes.Application.index()
            );
        }
    }


    //team making
    static Form<Team> teamForm = Form.form(Team.class);

    public static Result newTeam() {
        Form<Team> filledForm = teamForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.pages.rooms.render(Room.find.all(),
                            Team.find.all(),
                            teamForm)
            );
        } else {
            Team.create(filledForm.get());
            return redirect(routes.Application.rooms());
        }
    }

    public static Result deleteTeam(Long id) {
        Team.delete(id);
        return redirect(routes.Application.rooms());
    }

}
