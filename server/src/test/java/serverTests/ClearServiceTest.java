package serverTests;

import dataAccessTests.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
  private static UserDAO userDAO;
  private static AuthDAO authDAO;
  private static GameDAO gameDAO;
  private static ClearService service;
  @BeforeEach
  void setUp(){
    userDAO = new MemoryUserDAO();
    authDAO = new MemoryAuthDAO();
    gameDAO = new MemoryGameDAO();
    service = new ClearService(userDAO, authDAO, gameDAO);
  }

  @Test
  @DisplayName("Clear DB Test")
  void clearDB() throws DataAccessException{
    userDAO.createUser(new UserData("user", "password", "email"));
    authDAO.createAuth("username");
    gameDAO.createGame("gameName");
    service.clearDB();
    Collection<GameData> games = gameDAO.listGames();
    assertEquals(games, new ArrayList<>());
  }
}