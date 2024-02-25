package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.ExceptionData;
import model.UserData;
import spark.Request;
import spark.Response;
import userHandler.UserHandler;

import java.security.AuthProvider;

public class LoginHandler extends UserHandler {
  public LoginHandler(UserDAO userDAO, AuthDAO authDAO) {
    super(userDAO, authDAO);
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      UserData user=new Gson().fromJson(request.body(), UserData.class);
      AuthData auth=userService.login(user);
      return new Gson().toJson(auth);
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
