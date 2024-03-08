package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccessTests.AuthDAO;
import dataAccessTests.DataAccessException;
import dataAccessTests.GameDAO;
import model.ExceptionData;
import model.GameData;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends GameHandler{
  public CreateGameHandler(GameDAO gameDAO, AuthDAO authDAO) {
    super(gameDAO, authDAO);
  }
  @Override
  public Object handle(Request request, Response response) {
    try {
      String authToken = request.headers("Authorization");
      GameData game = new Gson().fromJson(request.body(), GameData.class);
      game = gameService.createGame(authToken, game.gameName());
      JsonObject gameID = new JsonObject();
      gameID.addProperty("gameID", game.gameID());
      return new Gson().toJson(gameID);
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
