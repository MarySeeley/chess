package dataAccessTests;

import model.UserData;

public interface UserDAO {
  UserData checkUser(UserData user) throws DataAccessException;
  UserData getUser(String username) throws DataAccessException;
  void createUser(UserData user) throws DataAccessException;
  void clearUsers() throws DataAccessException;

}
