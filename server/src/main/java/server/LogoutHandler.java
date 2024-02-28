package userHandler;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.ExceptionData;
import spark.Request;
import spark.Response;
import userHandler.UserHandler;

public class LogoutHandler extends UserHandler {

  public LogoutHandler(UserDAO userDAO, AuthDAO authDAO) {
    super(userDAO, authDAO);
  }
  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      String authToken = request.headers("Authorization");
      userService.logout(authToken);
      return "{}";
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
