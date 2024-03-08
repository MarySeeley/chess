package dataAccess;

import exception.ResponseException;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import serverTests.UserService;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {
  private static UserDAO userDAO;
  private static String username;
  private static String password;
  private static String email;
  private static UserData user;
  @BeforeEach
  void setUp() throws ResponseException, DataAccessException {
    userDAO = new SQLUserDAO();
    username = "username";
    password = "password";
    email = "name@mail.com";
    user = new UserData(username, password, email);
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    userDAO.clearUsers();
  }

  @Test
  @Order(6)
  void checkUserWorks() throws DataAccessException {
    assertNull(userDAO.checkUser(user));
  }

  @Test
  @Order(7)
  void checkUserFails() throws DataAccessException {
    userDAO.createUser(user);
    assertThrows(DataAccessException.class, ()->{userDAO.checkUser(user);});
  }

  @Test
  @Order(2)
  void getUserWorks() throws DataAccessException {
    userDAO.createUser(user);
    assertEquals(user, userDAO.getUser(user.username()));
  }
  @Test
  @Order(3)
  void getUserErrors(){
    assertThrows(DataAccessException.class,()->{userDAO.getUser(user.username());});
  }

  @Test
  @Order(4)
  void createUserWorks() throws DataAccessException {
    userDAO.createUser(user);
    assertEquals(user, userDAO.getUser(user.username()));
  }
  @Test
  @Order(5)
  void createUserFails() throws DataAccessException{
    assertThrows(DataAccessException.class, ()->{userDAO.createUser(new UserData(null, null, null));});
  }
  @Test
  @Order(1)
  void clearUsers() throws DataAccessException {
    userDAO.createUser(user);
    userDAO.clearUsers();
    assertThrows(DataAccessException.class, ()->{userDAO.getUser(user.username());} );
  }
}