package server;

import serverTests.ClearService;
import com.google.gson.Gson;
import dataAccessTests.AuthDAO;
import dataAccessTests.DataAccessException;
import dataAccessTests.GameDAO;
import dataAccessTests.UserDAO;
import model.ExceptionData;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route{
  final UserDAO userDAO;
  final AuthDAO authDAO;
  final GameDAO gameDAO;

  public ClearHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
    this.userDAO = userDAO;
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
  }  @Override
  public Object handle(Request request, Response response) {
    try {
      ClearService service=new ClearService(userDAO, authDAO, gameDAO);
      service.clearDB();
      return "{}";
    }catch(DataAccessException e){
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }
  }
}
