package server;

import serverTests.GameService;
import dataAccessTests.AuthDAO;
import dataAccessTests.GameDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class GameHandler implements Route {
  final GameDAO gameDAO;
  final AuthDAO authDAO;
  final GameService gameService;
  public GameHandler(GameDAO gameDAO, AuthDAO authDAO){
    this.gameDAO = gameDAO;
    this.authDAO = authDAO;
    this.gameService = new GameService(gameDAO, authDAO);
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    return null;
  }
}
