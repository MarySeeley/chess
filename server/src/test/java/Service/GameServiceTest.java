package Service;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.JoinData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
  @DisplayName("Join Game Worked Tests")
  void joinGameWorked() {

  }

  @Test
  @DisplayName("Join Game Error Tests")
  void joinGameError(){
    assertThrows(DataAccessException.class, ()->{service.joinGame("auth", new JoinData(null, 1234));});
  }
}