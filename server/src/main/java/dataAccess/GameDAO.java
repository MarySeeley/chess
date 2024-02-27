package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
  void clearGames() throws DataAccessException;
  Collection<GameData> listGames() throws DataAccessException;
  GameData createGame(String gameName) throws DataAccessException;
  void checkColor(String playerColor, int gameID) throws DataAccessException;
  public void updateGame(int gameID, String clientColor, String username) throws DataAccessException;
  public void checkGame(int gameID) throws DataAccessException;
}
