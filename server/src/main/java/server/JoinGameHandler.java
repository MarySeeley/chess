package server;

import com.google.gson.Gson;
import dataAccessTests.AuthDAO;
import dataAccessTests.DataAccessException;
import dataAccessTests.GameDAO;
import model.ExceptionData;
import model.JoinData;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends GameHandler{
  public JoinGameHandler(GameDAO gameDAO, AuthDAO authDAO) {
    super(gameDAO, authDAO);
  }

  @Override
  public Object handle(Request request, Response response) {
    try {
      String authToken = request.headers("Authorization");
      JoinData join = new Gson().fromJson(request.body(), JoinData.class);
      gameService.joinGame(authToken, join);

      return "{}";
    }catch(DataAccessException e){
      response.status(e.getStatusCode());
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }catch(Exception e){
      e.printStackTrace();
      response.status(500);
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }
  }
}
