package dataAccessTests;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class SQLGameDAO implements GameDAO{
  public SQLGameDAO() throws DataAccessException, ResponseException {
    configureDatabase();
  }
  public static final String[] createStatements = {
          """
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` int AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),       
              `gameName` varchar(256) NOT NULL,
              `chessGame` blob,                   
              PRIMARY KEY (`gameID`)
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
  public void clearGames() throws DataAccessException {
    try(var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("DELETE FROM game")) {
        preparedStatement.executeUpdate();
      }
    }
    catch(SQLException e){
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT * FROM game")) {
        try (var rs=preparedStatement.executeQuery()) {
          Collection<GameData> games = new ArrayList<>();
          while (rs.next()) {
            int gameID = rs.getInt("gameID");
            String white = rs.getString("whiteUsername");
            String black = rs.getString("blackUsername");
            String gameName = rs.getString("gameName");
            String gameJson = rs.getString("chessGame");
            Gson gson = new Gson();
            ChessGame game = gson.fromJson(gameJson, ChessGame.class);
            GameData newGame = new GameData(gameID, white, black, gameName, game);
            games.add(newGame);
          }
        return games;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public GameData createGame(String gameName, ChessGame chessGame) throws DataAccessException {
    try(var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("INSERT INTO game (whiteUsername, blackUsername, gameName, chessGame)  VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

        Gson gson = new Gson();
        String jsonGame = gson.toJson(chessGame);
        preparedStatement.setString(1, null);
        preparedStatement.setString(2, null);
        preparedStatement.setString(3, gameName);
        preparedStatement.setString(4, jsonGame);


        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        Integer ID = null;
        if (resultSet.next()) {
          ID = resultSet.getInt(1);
        }

        return new GameData(ID, null, null, gameName, chessGame);
      }
    }
    catch(SQLException e){
      e.printStackTrace();
      throw new DataAccessException(500,e.getMessage());
    }
  }

  @Override
  public void checkColor(String playerColor, int gameID) throws DataAccessException {

    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername FROM game WHERE gameID=?")) {
        preparedStatement.setInt(1, gameID);
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            if(playerColor.equals("WHITE")){
              var white = rs.getString("whiteUsername");
              if(white != null){
                throw new DataAccessException(403, "Error: white player already taken");
              }
            }
            else if(playerColor.equals("BLACK")){
              var black = rs.getString("blackUsername");
              if(black != null){
                throw new DataAccessException(403, "Error: black player already taken");
              }
            }
          }
          else{
            throw new DataAccessException(400, "Error: Wrong gameID");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  public void updateGame(int gameID, String clientColor, String username) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      if(clientColor.equals("WHITE")) {
        try (var preparedStatement=conn.prepareStatement("UPDATE game SET whiteUsername=? WHERE gameID=?")) {
          preparedStatement.setString(1, username);
          preparedStatement.setInt(2, gameID);

          preparedStatement.executeUpdate();
        }
      }
      else if(clientColor.equals("BLACK")){
        try (var preparedStatement=conn.prepareStatement("UPDATE game SET blackUsername=? WHERE gameID=?")) {
          preparedStatement.setString(1, username);
          preparedStatement.setInt(2, gameID);

          preparedStatement.executeUpdate();
        }
      }
      else{
        throw new DataAccessException(500, "Error: invalid clientColor");
      }
    }catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException(500, "Error: SQL error");
    }
  }
  public void updateGameBoard(int gameID, ChessGame game) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("UPDATE game SET chessGame=? WHERE gameID=?")) {
        Gson gson=new Gson();
        String jsonGame=gson.toJson(game);
        preparedStatement.setString(1, jsonGame);
        preparedStatement.setInt(2, gameID);

        preparedStatement.executeUpdate();
      }
    }catch(SQLException e){
      throw new DataAccessException(500, "Error: SQL error");
    }
  }

  @Override
  public void checkGame(int gameID) throws DataAccessException {
    try (var conn = DatabaseManager.getConnection()) {
      try (var preparedStatement=conn.prepareStatement("SELECT gameID FROM game WHERE gameID=?")) {
        preparedStatement.setInt(1, gameID);
        try (var rs=preparedStatement.executeQuery()) {
          if (rs.next()) {
            return;
          }
          else{
            throw new DataAccessException(400, "Error: Wrong gameID");
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
