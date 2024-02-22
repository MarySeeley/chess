package Service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

public class ClearService {
  private final UserDAO userDAO;
  private final AuthDAO authDAO;
  private final GameDAO gameDAO;
  public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
    this.userDAO = userDAO;
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
  }
  public void clearDB() throws DataAccessException {
    userDAO.clearUsers();
    authDAO.clearAuth();
    gameDAO.clearGames();
  }
}
