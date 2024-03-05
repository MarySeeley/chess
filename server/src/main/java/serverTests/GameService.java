package serverTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;
import model.JoinData;

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
  public void joinGame(String authToken, JoinData join) throws DataAccessException{
    authDAO.checkAuth(authToken);
    if(join.playerColor() == null){
      gameDAO.checkGame(join.gameID());
      return;
    }
    gameDAO.checkColor(join.playerColor(), join.gameID());
    String userName = authDAO.getUser(authToken);
    gameDAO.updateGame(join.gameID(), join.playerColor(), userName);
  }
}
