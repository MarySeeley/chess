package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.lang.Integer;


public class MemoryGameDAO implements GameDAO{
  final private Collection<GameData> games = new ArrayList<>();

  @Override
  public void clearGames() {
    games.clear();
  }
  public Collection<GameData> listGames(){
    return games;
  }
  public GameData createGame(String gameName){
    Random random = new Random();
    int randomID = Math.abs(random.nextInt());
    boolean loop = true;
    while(loop) {
      boolean repeat = false;
      for (GameData i : games) {
        if (i.gameID() == randomID) {
          repeat = true;
        }
      }
      if(!repeat){
        loop = false;
      }
      else{
        randomID = Math.abs(random.nextInt());
      }
    }
    GameData game = new GameData(randomID, null, null, gameName, new ChessGame());
    games.add(game);
    return game;
  }
}
