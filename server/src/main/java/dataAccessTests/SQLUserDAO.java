package dataAccessTests;

import exception.ResponseException;
import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{
  public SQLUserDAO() throws DataAccessException, ResponseException {
    configureDatabase();
  }
  public static final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,             
              PRIMARY KEY (`username`)
            ) 
            """};
  public static void configureDatabase() throws ResponseException, DataAccessException {
    DatabaseManager.createDatabase();
    try (var conn = DatabaseManager.getConnection()) {
      for (var statement : createStatements) {
        try (var preparedStatement = conn.prepareStatement(statement)) {
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException | DataAccessException ex) {
      throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
    }
  }
  @Override
  public UserData checkUser(UserData user) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT username FROM user WHERE username=?")) {
        preparedStatement.setString(1, user.username());
        try (var rs=preparedStatement.executeQuery()) {
          if (!rs.next()) {
            return null;
          }
          else{
            throw new DataAccessException(403,"Error: User already registered");
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public UserData getUser(String username) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT username, password, email FROM user WHERE username=?")) {
        preparedStatement.setString(1, username);
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            String usernameSQL = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            return new UserData(username, password, email);
          }
          else{
            throw new DataAccessException(401, "Error: Wrong username");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void createUser(UserData user) throws DataAccessException {
    try(var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("INSERT INTO user (username, password, email) VALUES (?,?,?)")) {
        preparedStatement.setString(1, user.username());
        preparedStatement.setString(2, user.password());
        preparedStatement.setString(3, user.email());
        preparedStatement.executeUpdate();
      }
    }
    catch(SQLException e){
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public void clearUsers() throws DataAccessException {
    try(var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("DELETE FROM user")) {
        preparedStatement.executeUpdate();
      }
    }
    catch(SQLException e){
      throw new DataAccessException(500,e.getMessage());
    }
  }
}
