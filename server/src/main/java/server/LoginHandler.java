package server;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler extends UserHandler{
  public LoginHandler(UserDAO userDAO, AuthDAO authDAO) {
    super(userDAO, authDAO);
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}
