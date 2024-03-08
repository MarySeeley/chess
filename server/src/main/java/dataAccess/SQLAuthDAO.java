package dataAccess;

import exception.ResponseException;
import model.AuthData;

import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {
  public SQLAuthDAO() throws DataAccessException, ResponseException {
    configureDatabase();
  }
  public static final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  auth (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
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
  public AuthData createAuth(String username) throws DataAccessException {
    try(var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?,?)")) {
        String token=UUID.randomUUID().toString();
        preparedStatement.setString(1, token);
        preparedStatement.setString(2, username);
        preparedStatement.executeUpdate();
        AuthData newAuth=new AuthData(token, username);
        return newAuth;
      }
    }
    catch(SQLException e){
      e.printStackTrace();
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public void clearAuth() throws DataAccessException {
    try(var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("DELETE FROM auth")) {
        preparedStatement.executeUpdate();
      }
    }
    catch(SQLException e){
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public void deleteAuth(String authToken) throws DataAccessException {
    try(var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("DELETE FROM auth WHERE authToken=?")) {
        preparedStatement.setString(1, authToken);
        preparedStatement.executeUpdate();
      }
    }
    catch(SQLException e){
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public void checkAuth(String authToken) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT authToken FROM auth WHERE authToken=?")) {
        preparedStatement.setString(1, authToken);
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            return;
          }
          else{
            throw new DataAccessException(401, "Error: Wrong username");
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getUser(String authToken) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE authToken=?")) {
        preparedStatement.setString(1, authToken);
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            return rs.getString("username");
          }
          else{
            throw new DataAccessException(400, "Error: no username");
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
