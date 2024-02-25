package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.ExceptionData;
import model.GameData;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;

public class ListGamesHandler extends GameHandler {
  public ListGamesHandler(GameDAO gameDAO, AuthDAO authDAO) {
    super(gameDAO, authDAO);
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      String authToken = request.headers("Authorization");
      Collection<GameData> games = gameService.getGames(authToken);
      return new Gson().toJson(games);
    }catch(DataAccessException e){
      response.status(e.getStatusCode());
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }catch(Exception e){
      response.status(500);
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }
  }
}
