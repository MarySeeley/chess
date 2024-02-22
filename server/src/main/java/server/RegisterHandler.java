package server;

import Service.UserService;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
  private final UserDAO userDAO;
  private final AuthDAO authDAO;

  public RegisterHandler(UserDAO userDAO, AuthDAO authDAO){
    this.userDAO = userDAO;
    this.authDAO = authDAO;
  }
  @Override
  public Object handle(Request req, Response res) throws Exception {
    UserData user = new Gson().fromJson(req.body(), UserData.class);
    UserService userService= new UserService(userDAO, authDAO);
    AuthData auth = userService.register(user);
    return new Gson().toJson(auth);
  }
}
