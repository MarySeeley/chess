package dataAccessTests;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


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

  public void checkColor(String playerColor, int gameID)throws DataAccessException{
    GameData game = null;
    boolean found = false;
    for(GameData i : games){
      if(gameID==i.gameID()){
        game = i;
        found = true;
        break;
      }
    }
    if(!found){
      throw new DataAccessException(400, "Error: invalid gameID");
    }

    if(playerColor.equals("WHITE")){
      if(game.whiteUsername()!=null){
        throw new DataAccessException(403, "Error: white already taken");
      }
    }
    else if(playerColor.equals("BLACK")){
      if(game.blackUsername()!= null){
        throw new DataAccessException(403, "Error: black already taken");
      }
    }
  }
  public void updateGame(int gameID, String clientColor, String username) throws DataAccessException{
    GameData game = null;
    boolean found = false;
    for(GameData i : games){
      if(gameID==i.gameID()){
        game = i;
        found = true;
        games.remove(i);
        break;
      }
    }
    if(!found){
      throw new DataAccessException(400, "Error: invalid gameID");
    }
    if(clientColor.equals("WHITE")){
      GameData newGame = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
      games.add(newGame);
    }
    if(clientColor.equals("BLACK")){
      GameData newGame = new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game());
      games.add(newGame);
    }
  }

  @Override
  public void updateGameBoard(int gameID, ChessGame game) throws DataAccessException {

  }

  public void checkGame(int gameID) throws DataAccessException{
    boolean found = false;
    for(GameData i : games){
      if(gameID==i.gameID()){
        found = true;
        break;
      }
    }
    if(!found){
      throw new DataAccessException(400, "Error: invalid gameID");
    }
  }
}
