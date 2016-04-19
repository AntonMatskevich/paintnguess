package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {


    public static Result index() {
        return ok(views.html.index.render("Hallo world!"));
    }

}
