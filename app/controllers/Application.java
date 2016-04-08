package controllers;

import models.Gamer;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.*;

import java.util.List;

public class Application extends Controller {


    public static Result index() {
        return ok(views.html.index.render(Gamer.all(), gamerForm));
    }

    public static Result mode() {
        return ok(views.html.mode.render());
    }

    public static Result aboutUs() {
        return ok(views.html.aboutus.render());
    }

    public static Result card() {
        return ok(views.html.card.render());
    }

    public static Result donate() {
        return ok(views.html.donate.render());
    }

    public static Result drawing() {
        return ok(views.html.drawing.render());
    }

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

    //View gamers
    public static Result getGamers(){
        return ok(views.html.remove_gamer.render(Gamer.all(), gamerForm));
    }

    public static Result deleteGamer(Integer id) {
        Gamer.delete(id);
        return redirect(routes.Application.getGamers());
    }
    public static Result deleteGamer() {
        return TODO;
    }

}
