package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
  final private Collection<GameData> games = new ArrayList<>();
  @Override
  public void clearGames() throws DataAccessException {
    games.clear();
  }
}
