package dataAccess;

import model.AuthData;

public interface AuthDAO {
  AuthData createAuth(String username) throws DataAccessException;
  void clearAuth() throws DataAccessException;
  void deleteAuth(String authToken) throws DataAccessException;
}
