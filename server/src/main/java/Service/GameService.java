package Service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import model.GameData;

import java.util.Collection;

public class GameService {
  private GameDAO gameDAO;
  private AuthDAO authDAO;

  public GameService(GameDAO gameDAO, AuthDAO authDAO){
    this.gameDAO = gameDAO;
    this.authDAO = authDAO;
  }
  public Collection<GameData> getGames(String authToken) throws DataAccessException {
    authDAO.checkAuth(authToken);
    return gameDAO.listGames();
  }

  public GameData createGame(String authToken, String gameName) throws DataAccessException{
    authDAO.checkAuth(authToken);
    GameData game = gameDAO.createGame(gameName);
    return game;
  }
}
