package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.ExceptionData;
import model.GameData;
import model.ListData;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListGamesHandler extends GameHandler {
  public ListGamesHandler(GameDAO gameDAO, AuthDAO authDAO) {
    super(gameDAO, authDAO);
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      String authToken = request.headers("Authorization");
      Collection<GameData> gameCollection = gameService.getGames(authToken);
      List<GameData> gamesList = new ArrayList<>(gameCollection);
      ListData games = new ListData(gamesList);
      return new Gson().toJson(games);
    }catch(DataAccessException e){
      response.status(e.getStatusCode());
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }catch(Exception e){
      response.status(500);
      e.printStackTrace();
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }
  }
}
