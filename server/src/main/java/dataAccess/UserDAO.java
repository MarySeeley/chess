package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;

public interface UserDAO {
  UserData checkUser(UserData user) throws DataAccessException;
  UserData getUser(String username) throws DataAccessException;
  void createUser(UserData user) throws DataAccessException;
  void clearUsers() throws DataAccessException;
  Collection<UserData> getUsers();

}
