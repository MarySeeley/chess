package serverTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.JoinData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
  private static GameService service;
  private static AuthDAO authDAO;
  private static GameDAO gameDAO;
  private static GameData game;
  private static AuthData auth;

  @BeforeEach
  void setUp() throws DataAccessException{
    gameDAO = new MemoryGameDAO();
    authDAO = new MemoryAuthDAO();
    service = new GameService(gameDAO, authDAO);
    auth = authDAO.createAuth("username");
    GameData game = new GameData(1234, null, null, "gameName", new ChessGame());
  }
  @Test
  @DisplayName("Get Games Worked Tests")
  void getGamesWorked() throws DataAccessException {
    assertInstanceOf(Collection.class, service.getGames(auth.authToken()));
  }

  @Test
  @DisplayName("Get Games Error Tests")
  void getGamesError(){
    assertThrows(DataAccessException.class, ()->{service.getGames("fake authToken");});
  }

  @Test
  @DisplayName("Create Game Worked Tests")
  void createGameWorked() throws DataAccessException{
    assertInstanceOf(GameData.class, service.createGame(auth.authToken(), "gameName"));
  }

  @Test
  @DisplayName("Create Game Error Tests")
  void createGameError(){
    assertThrows(DataAccessException.class, ()->{service.createGame("auth", "gameName");});
  }


  @Test
  @DisplayName("Join Game White Tests")
  void joinGameWhite() throws DataAccessException{
    GameData game = gameDAO.createGame("join test");
    service.joinGame(auth.authToken(), new JoinData("WHITE", game.gameID()));
    Collection<GameData> games = gameDAO.listGames();
    for(GameData i : games){
      if(game.gameID() == i.gameID()){
        game = i;
      }
    }
    assertEquals(auth.username(), game.whiteUsername());
  }

  @Test
  @DisplayName("Join Game Black Tests")
  void joinGameBlack() throws DataAccessException{
    GameData game = gameDAO.createGame("join test");
    service.joinGame(auth.authToken(), new JoinData("BLACK", game.gameID()));
    Collection<GameData> games = gameDAO.listGames();
    for(GameData i : games){
      if(game.gameID() == i.gameID()){
        game = i;
      }
    }
    assertEquals(auth.username(), game.blackUsername());
  }

  @Test
  @DisplayName("Join Game Watch Test")
  void joinGameWatch()throws DataAccessException{
    GameData game = gameDAO.createGame("join test");
    service.joinGame(auth.authToken(), new JoinData(null, game.gameID()));
    Collection<GameData> games = gameDAO.listGames();
    for(GameData i : games){
      if(game.gameID() == i.gameID()){
        game = i;
      }
    }
    assertEquals(null, game.blackUsername());
  }

  @Test
  @DisplayName("Join Game Error Tests")
  void joinGameError() throws DataAccessException{
    GameData game = gameDAO.createGame("join test");
    service.joinGame(auth.authToken(), new JoinData("WHITE", game.gameID()));
    Collection<GameData> games = gameDAO.listGames();
    for(GameData i : games){
      if(game.gameID() == i.gameID()){
        game = i;
      }
    }
    GameData finalGame=game;
    assertThrows(DataAccessException.class, ()->{service.joinGame(auth.authToken(), new JoinData("WHITE", finalGame.gameID()));});
    assertThrows(DataAccessException.class, ()->{service.joinGame("auth", new JoinData("WHITE", finalGame.gameID()));});
    assertThrows(DataAccessException.class, ()->{    service.joinGame(auth.authToken(), new JoinData("WHITE", 1234));});
  }

}