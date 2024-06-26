package server;

import com.google.gson.Gson;
import dataAccessTests.AuthDAO;
import dataAccessTests.DataAccessException;
import dataAccessTests.UserDAO;
import model.AuthData;
import model.ExceptionData;
import model.UserData;
import spark.Request;
import spark.Response;
import userHandler.UserHandler;

public class LoginHandler extends UserHandler {
  public LoginHandler(UserDAO userDAO, AuthDAO authDAO) {
    super(userDAO, authDAO);
  }

  @Override
  public Object handle(Request request, Response response) throws Exception {
    try {
      UserData user=new Gson().fromJson(request.body(), UserData.class);
      System.out.println(user.username());
      AuthData auth=userService.login(user);
      return new Gson().toJson(auth);
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
