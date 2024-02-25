package userHandler;

import Service.UserService;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserHandler implements Route {
  final UserDAO userDAO;
  final AuthDAO authDAO;
  public final UserService userService;

  public UserHandler(UserDAO userDAO, AuthDAO authDAO){
    this.userDAO = userDAO;
    this.authDAO = authDAO;
    this.userService = new UserService(userDAO, authDAO);

  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}