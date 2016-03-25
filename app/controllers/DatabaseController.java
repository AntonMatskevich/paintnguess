package controllers;

import models.User;
import play.data.FormFactory;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.List;

import static play.libs.Json.toJson;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by Anton on 25.03.16.
 */
public class DatabaseController {

    @Inject
    FormFactory formFactory;
    @Transactional
    public Result addUser(){
        User user = formFactory.form(User.class).bindFromRequest().get();
        JPA.em().persist(user);
        return redirect(routes.Application.mode());
    }

    //View users
    @Transactional(readOnly = true)
    public Result getUsers(){
        List<User> users = (List<User>) JPA.em().createQuery("select u from User u").getResultList();
        return ok(toJson(users));
    }

}
