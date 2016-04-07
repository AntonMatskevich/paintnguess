package controllers;

import models.Task;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {


    public static Result index() {
        return ok(views.html.index.render());
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



    ////////////////Das ist keine demo Scheisse
    static Form<Task> taskForm = Form.form(Task.class);

    public static Result tasks() {
        return ok(views.html.demo.render(Task.all(), taskForm));
    }

    public static Result newTask() {
        Form<Task> filledForm = taskForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(
                    views.html.demo.render(Task.all(), filledForm)
            );
        } else {
            Task.create(filledForm.get());
            return redirect(routes.Application.tasks());
        }
    }

    public static Result deleteTask(Long id) {
        Task.delete(id);
        return redirect(routes.Application.tasks());
    }

}
