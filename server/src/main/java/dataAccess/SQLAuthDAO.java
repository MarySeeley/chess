package dataAccess;

import model.AuthData;

public class SQLAuthDAO implements AuthDAO {
  public SQLAuthDAO() throws DataAccessException{
    configureDatabase();
  }

  private void configureDatabase() {
  }

  @Override
  public AuthData createAuth(String username) throws DataAccessException {
    return null;
  }

  @Override
  public void clearAuth() throws DataAccessException {

  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException {

  }

  @Override
  public void checkAuth(String authToken) throws DataAccessException {

  }

  @Override
  public String getUser(String authToken) throws DataAccessException {
    return null;
  }
}
