package dataAccess;

import model.AuthData;
import model.UserData;

public interface UserDAO {
  UserData getUser(UserData user) throws DataAccessException;
  void createUser(UserData user) throws DataAccessException;
  void clearUsers() throws DataAccessException;

}
