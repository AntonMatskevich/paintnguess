package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Gamer;
import models.PaintRoom;
import play.api.mvc.Session;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.*;
import play.data.*;

import static play.data.Form.*;

import java.util.List;

public class Application extends Controller {


    public static Result index() {
        return ok(views.html.index.render(Gamer.all(), gamerForm));
    }

    @Security.Authenticated(Secured.class)
    public static Result mode() {
        return ok(views.html.mode.render());
    }

    public static Result aboutUs() {
        return ok(views.html.aboutus.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result card() {
        return ok(views.html.card.render());
    }

    public static Result donate() {
        return ok(views.html.donate.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result drawing() {
        return ok(views.html.drawing.render());
    }

    @Security.Authenticated(Secured.class)
    public static Result drawingGame() {
        return ok(views.html.drawing_game.render());
    }
    
//    public static Result removeGamer() {
//        return redirect(routes.Application.getGamers());
//    }

    ///////DATABASE
    static Form<Gamer> gamerForm = Form.form(Gamer.class);

    public static Result addGamer(){
        Form<Gamer> filledForm = gamerForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(
                    views.html.index.render(Gamer.all(), filledForm)
            );
        } else {
            Gamer.create(filledForm.get());
            return redirect(routes.Application.mode());
        }
    }
    /////////////Authentication
    public static Result login() {
        return ok(
                views.html.login.render(Form.form(Login.class))
        );
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.index()
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(views.html.login.render(loginForm));
        } else {
            session().clear();
            session("name", loginForm.get().name);
            return redirect(
                    routes.Application.mode()
            );
        }
    }

    //View gamers
    public static Result getGamers(){
        return ok(views.html.remove_gamer.render(Gamer.all(), gamerForm));
    }

    public static Result deleteGamer(Integer id) {

        Gamer.delete(id);
        return redirect(routes.Application.getGamers());
    }

    public static class Login {

        public String name;

        public String validate() {
            if (Gamer.authenticate(name) == null) {
                return "Invalid username";
            }
            return null;
        }

    }

    //////////////PAINTER
    static PaintRoom env = new PaintRoom("Public");

    public static Result painter() {
        return ok(views.html.painter.render(env));
    }

    public static WebSocket<JsonNode> stream() {

        return new WebSocket<JsonNode>() {
            @Override
            public void onReady(In<JsonNode> in, Out<JsonNode> out) {
                try{
                    env.createPainter(in, out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

    }

}
