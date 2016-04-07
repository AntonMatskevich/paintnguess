package controllers;

import models.Task;
import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {


    public static Result index() {
        return ok(views.html.index.render());
//        return redirect(routes.DatabaseController.getUsers());
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

    public static Result login() {
        return redirect(routes.Application.getUsers());
    }


    ///////DATABASE
    static Form<User> userForm = Form.form(User.class);

    public static Result addUser(){
        Form<User> filledForm = userForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.login.render(User.all(), filledForm)
            );
        } else {
            User.create(filledForm.get());
            return redirect(routes.Application.mode());
        }
    }

    //View users
    public static Result getUsers(){
        return ok(views.html.login.render(User.all(), userForm));
    }

    public static Result deleteUser(Integer id) {
        User.delete(id);
        return redirect(routes.Application.getUsers());
    }

}
