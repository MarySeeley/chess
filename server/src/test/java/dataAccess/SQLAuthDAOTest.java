package dataAccess;

import exception.ResponseException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import serverTests.UserService;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDAOTest {
  private static AuthDAO authDAO;
  private static String username;
  private static String password;
  private static String email;
  private static UserData user;
//  private static AuthData auth;
  private static String authToken;
  @BeforeEach
  void setUp() throws ResponseException, DataAccessException {
    authDAO = new SQLAuthDAO();
    username = "username";
    password = "password";
    email = "name@mail.com";
    authToken = "authToken";
    user = new UserData(username, password, email);
    authDAO.clearAuth();
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    authDAO.clearAuth();
  }

  @Test
  @Order(1)
  void createAuthWorks() throws DataAccessException {
    AuthData newAuth = authDAO.createAuth(username);
    assertTrue(newAuth.authToken() instanceof String);
  }
  @Test
  @Order(3)
  void createAuthFails(){
    assertThrows(DataAccessException.class, ()->{authDAO.createAuth(null);});
  }

  @Test
  @Order(2)
  void clearAuth() throws DataAccessException {
    AuthData newAuth = authDAO.createAuth(username);
    authDAO.clearAuth();
    assertThrows(DataAccessException.class, ()->{authDAO.checkAuth(newAuth.authToken());});
  }

  @Test
  @Order(6)
  void deleteAuthWorks() throws DataAccessException {
    AuthData newAuth = authDAO.createAuth(username);
    authDAO.deleteAuth(newAuth.authToken());
    assertThrows(DataAccessException.class, ()->{authDAO.getUser(newAuth.authToken());});
  }

  @Test
  @Order(7)
  void deleteAuthFails(){
    assertThrows(DataAccessException.class, ()->{authDAO.deleteAuth(null);});
  }

  @Test
  @Order(8)
  void checkAuthWorks() throws DataAccessException {
    AuthData auth = authDAO.createAuth(username);
    authDAO.checkAuth(auth.authToken());
  }
  @Test
  @Order(9)
  void checkAuthFails(){
    assertThrows(DataAccessException.class, ()->{authDAO.checkAuth(username);});
  }

  @Test
  @Order(4)
  void getUserWorks() throws DataAccessException {
    AuthData auth = authDAO.createAuth(username);
    assertEquals(username, authDAO.getUser(auth.authToken()));
  }

  @Test
  @Order(5)
  void getUserFails(){
    assertThrows(DataAccessException.class, ()->{authDAO.getUser(username);});
  }
}