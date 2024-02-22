package server;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class Handler implements Route {
  final UserDAO userDAO;
  final AuthDAO authDAO;
  final GameDAO gameDAO;

  public Handler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
    this.userDAO = userDAO;
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}
