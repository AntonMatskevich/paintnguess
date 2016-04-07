package controllers;


import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by Anton on 25.03.16.
 */
public class DatabaseController extends Controller {

//    static Form<User> userForm = Form.form(User.class);
//
//    public static Result addUser(){
//        Form<User> filledForm = userForm.bindFromRequest();
//        if(filledForm.hasErrors()) {
//            return badRequest(
//                    views.html.index.render(filledForm)
//            );
//        } else {
//            User.create(filledForm.get());
//            return redirect(routes.Application.mode());
//        }
//    }
//
//    //View users
//    public static Result getUsers(){
//        return ok(views.html.users.render(User.all(), userForm));
//    }
//
//    public static Result deleteUser(Integer id) {
//        User.delete(id);
//        return redirect(routes.DatabaseController.getUsers());
//    }

}
