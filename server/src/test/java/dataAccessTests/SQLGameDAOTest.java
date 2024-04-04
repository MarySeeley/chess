package dataAccessTests;

import chess.ChessGame;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SQLGameDAOTest {
  private static AuthDAO authDAO;
  private static GameDAO gameDAO;
  private static GameData game;
  private static AuthData auth;
  @BeforeEach
  void setUp() throws ResponseException, DataAccessException {
    gameDAO = new SQLGameDAO();
    authDAO = new SQLAuthDAO();
    auth = authDAO.createAuth("username");
    GameData game = new GameData(1234, null, null, "gameName", new ChessGame());

  }

  @AfterEach
  void tearDown() throws DataAccessException {
    gameDAO.clearGames();
  }

  @Test
  void clearGames() throws DataAccessException {
    ChessGame chessGame = ChessGame.createNewGame();
    GameData game = gameDAO.createGame("gameName", chessGame);
    gameDAO.clearGames();
    assertThrows(DataAccessException.class, ()->{gameDAO.checkGame(game.gameID());});
  }

  @Test
  void listGamesWorks() throws DataAccessException {
    ChessGame chessGame = ChessGame.createNewGame();
    gameDAO.createGame("name", chessGame);
    Collection<GameData> list = gameDAO.listGames();
    assertTrue(list instanceof Collection<GameData>);
  }

  @Test
  void listGamesFails(){

  }


  @Test
  void createGameWorks() throws DataAccessException {
    ChessGame chessGame = ChessGame.createNewGame();
    GameData game = gameDAO.createGame("name", chessGame);
    assertTrue(game instanceof GameData);

  }

  @Test
  void createGameFails(){
    ChessGame chessGame = ChessGame.createNewGame();
    assertThrows(DataAccessException.class, ()->{gameDAO.createGame(null, chessGame);});
  }

  @Test
  void checkColorWorks() throws DataAccessException {
    ChessGame chessGame = ChessGame.createNewGame();
    GameData game = gameDAO.createGame("name", chessGame);
    gameDAO.checkColor("WHITE", game.gameID());
  }
  @Test
  void checkColorFails(){
    assertThrows(DataAccessException.class, ()->{gameDAO.checkColor(null, 1);});
  }

  @Test
  void updateGameWorks() throws DataAccessException {
    ChessGame chessGame = ChessGame.createNewGame();
    GameData game = gameDAO.createGame("name", chessGame);
    gameDAO.updateGame(game.gameID(), "WHITE", "white");
  }
  @Test
  void updateGameFails(){
    assertThrows(DataAccessException.class, ()->{gameDAO.updateGame(99,"W","white");});
  }

  @Test
  void checkGameWorks() throws DataAccessException {
    ChessGame chessGame = ChessGame.createNewGame();
    game = gameDAO.createGame("name", chessGame);
    gameDAO.checkGame(game.gameID());
  }
  @Test
  void checkGameFails(){
    assertThrows(DataAccessException.class, ()->{gameDAO.checkGame(1);});
  }
}