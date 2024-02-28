package dataAccess;

import model.AuthData;

import java.util.Collection;

public interface AuthDAO {
  AuthData createAuth(String username) throws DataAccessException;
  void clearAuth() throws DataAccessException;
  void deleteAuth(String authToken) throws DataAccessException;
  void checkAuth(String authToken) throws DataAccessException;
  String getUser(String authToken) throws DataAccessException;
  Collection<AuthData> getAuthList();
}
