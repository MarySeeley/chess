package serverTests;

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
    UserData checkUser = userDAO.checkUser(user);
    if(checkUser == null){
      userDAO.createUser(user);
    }
    AuthData auth = authDAO.createAuth(user.username());
    return auth;
  }

  public AuthData login(UserData user) throws DataAccessException{
    UserData grabbedUser = userDAO.getUser(user.username());
    if(grabbedUser.password().equals(user.password())){
      AuthData auth = authDAO.createAuth(user.username());
      return auth;
    }
    else{
      throw new DataAccessException(401, "Error: wrong password");
    }
  }

  public void logout(String auth) throws DataAccessException{
    authDAO.deleteAuth(auth);
  }
}
