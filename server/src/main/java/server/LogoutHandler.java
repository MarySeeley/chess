package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.ExceptionData;
import model.UserData;
import spark.Request;
import spark.Response;

public class LogoutHandler extends UserHandler{

  public LogoutHandler(UserDAO userDAO, AuthDAO authDAO) {
    super(userDAO, authDAO);
  }
  public Object handle(Request request, Response response) throws Exception {
    try {
      String authToken = request.headers("Authorization");
//      AuthData auth=new Gson().fromJson(request.headers("Authorization"), AuthData.class);
//      System.out.println(authToken);
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
