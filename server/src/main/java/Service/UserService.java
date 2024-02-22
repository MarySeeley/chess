package Service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
  private UserDAO userDAO;
  private AuthDAO authDAO;

  public UserService(UserDAO userDAO, AuthDAO authDAO){
    this.userDAO = userDAO;
    this.authDAO = authDAO;
  }
  //returns AuthToken
  public AuthData register(UserData user) throws DataAccessException {
    if(user.username()==null||user.password()==null||user.email()==null){
      throw new DataAccessException(400, "Error: bad request");
    }
    UserData checkUser = userDAO.getUser(user);
    if(checkUser == null){
      userDAO.createUser(user);
    }
    AuthData auth = authDAO.createAuth(user.username());
    return auth;
  }
}
