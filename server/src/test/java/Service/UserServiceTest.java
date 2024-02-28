package Service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
  private static UserService service;
  private static UserDAO userDAO;
  private static AuthDAO authDAO;
  private static String username;
  private static String password;
  private static String email;
  private static UserData user;

  @BeforeEach
  void setUp() {
    userDAO = new MemoryUserDAO();
    authDAO = new MemoryAuthDAO();
    service = new UserService(userDAO, authDAO);
    username = "username";
    password = "password";
    email = "name@mail.com";
    user = new UserData(username, password, email);

  }

  @Test
  @DisplayName("Register Worked Test")
  void registerWorks() throws DataAccessException {
    AuthData registerResult = service.register(user);
    assertInstanceOf(String.class, registerResult.authToken());
    assertEquals(username, registerResult.username());
  }

  @Test
  @DisplayName("Register Error Test")
  void registerErrorTest() throws DataAccessException{
    UserData badRegister = new UserData(null, null, null);
    assertThrows(DataAccessException.class, ()->{service.register(badRegister);});
    UserData reRegister = new UserData(username, password, email);
    service.register(user);
    assertThrows(DataAccessException.class, ()->{service.register(reRegister);});
  }

  @Test
  @DisplayName("Login Worked Test")
  void loginWorked() throws DataAccessException{
    service.register(user);
    AuthData login = service.login(user);
    assertInstanceOf(String.class, login.authToken());
    assertEquals(username, login.username());
  }

  @Test
  @DisplayName("Login Error Test")
  void loginErrors() throws DataAccessException{
    assertThrows(DataAccessException.class, ()->{service.login(user);});
  }

  @Test
  @DisplayName("Logout Worked Test")
  void logoutWorked() throws DataAccessException{
    AuthData authLogout = service.register(user);
    service.logout(authLogout.authToken());
    assertFalse(authDAO.getAuthList().contains(authLogout));
  }

  @Test
  @DisplayName("Logout Error Test")
  void logoutError(){
    assertThrows(DataAccessException.class, ()->{service.logout("auth");});
  }
}