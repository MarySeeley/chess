package serverTests;

import chess.ChessGame;
import dataAccessTests.AuthDAO;
import dataAccessTests.DataAccessException;
import dataAccessTests.GameDAO;
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
    ChessGame chessGame = ChessGame.createNewGame();
    GameData game = gameDAO.createGame(gameName, chessGame);
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
