package server;

import Service.UserService;
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
import spark.Route;

public class RegisterHandler extends UserHandler {

  public RegisterHandler(UserDAO userDAO, AuthDAO authDAO){
    super(userDAO, authDAO);
  }
  @Override
  public Object handle(Request req, Response res){
    try {
      UserData user=new Gson().fromJson(req.body(), UserData.class);
      AuthData auth=super.userService.register(user);
      return new Gson().toJson(auth);
    }catch(DataAccessException e){
      res.status(e.getStatusCode());
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }catch(Exception e){
      res.status(500);
      ExceptionData exception = new ExceptionData(e.getMessage());
      return new Gson().toJson(exception);
    }

  }
}
