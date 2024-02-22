package server;

import Service.ClearService;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.ExceptionData;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler extends Handler {
  public ClearHandler(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
    super(userDAO, authDAO, gameDAO);
  }
  @Override
  public Object handle(Request request, Response response) {
    try {
      ClearService service=new ClearService(super.userDAO, authDAO, gameDAO);
      service.clearDB();
      return "{}";
    }catch(DataAccessException e){
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }
  }
}
