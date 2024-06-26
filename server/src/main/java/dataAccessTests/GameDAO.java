package dataAccessTests;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDAO {
  void clearGames() throws DataAccessException;
  Collection<GameData> listGames() throws DataAccessException;
  GameData createGame(String gameName, ChessGame chessGame) throws DataAccessException;
  void checkColor(String playerColor, int gameID) throws DataAccessException;
  void updateGame(int gameID, String clientColor, String username) throws DataAccessException;
  void updateGameBoard(int gameID, ChessGame game) throws DataAccessException;
  void checkGame(int gameID) throws DataAccessException;

  //hello world
}
