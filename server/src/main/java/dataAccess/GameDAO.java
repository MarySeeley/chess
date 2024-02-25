package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
  void clearGames() throws DataAccessException;
  Collection<GameData> listGames() throws DataAccessException;
  GameData createGame(String gameName) throws DataAccessException;
}
