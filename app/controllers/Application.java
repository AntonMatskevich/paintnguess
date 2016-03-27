package controllers;

import play.*;
import play.mvc.*;
import play.db.jpa.*;
import views.html.*;
import models.Person;
import play.data.FormFactory;
import javax.inject.Inject;
import java.util.List;

import static play.libs.Json.*;

public class Application extends Controller {

//    String dirtyLink = "https://www.swedbank.com/idc/groups/public/@i/@sbg/@gs/documents/logotype/cid_007184@t~a1.jpg";

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result mode() {
        return ok(views.html.mode.render());
    }

    public Result aboutUs() {
        return ok(views.html.aboutus.render());
    }

    public Result card() {
        return ok(views.html.card.render());
    }

    public Result donate() {
        return ok(views.html.donate.render(dirtyLink));
    }

    public Result drawing() {
        return ok(views.html.drawing.render());
    }

    public Result drawingGame() {
        return ok(views.html.drawing_game.render());
    }



    ////////////////DEMO
    public Result demo() {
        return ok(demo.render());
    }

    @Inject
    FormFactory formFactory;
    @Transactional
    public Result addPerson() {
        Person person = formFactory.form(Person.class).bindFromRequest().get();
        JPA.em().persist(person);
        return redirect(routes.Application.demo());
    }

    @Transactional(readOnly = true)
    public Result getPersons() {
        List<Person> persons = (List<Person>) JPA.em().createQuery("select p from Person p").getResultList();
        return ok(toJson(persons));
    }
}
