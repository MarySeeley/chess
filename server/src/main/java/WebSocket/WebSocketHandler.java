package WebSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataAccessTests.AuthDAO;
import dataAccessTests.DataAccessException;
import dataAccessTests.GameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Collection;

import static java.lang.String.join;


@WebSocket
public class WebSocketHandler {
  private Session session;
  private AuthDAO authDAO;
  private GameDAO gameDAO;
  private final ConnectionManager connections = new ConnectionManager();
  public WebSocketHandler(AuthDAO authDAO, GameDAO gameDAO){
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
//    Spark.webSocket("/connect", WebSocketHandler.class);
  }
  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    UserGameCommand command =new Gson().fromJson(message, UserGameCommand.class);
    // check for authToken
    var conn = getConnection(command.authToken, session);
//    Connection conn = null;
    if(conn != null) {
      switch (command.getCommandType()) {
        case JOIN_PLAYER -> join(conn, message, session);
        case JOIN_OBSERVER -> observe(conn, message);
        case MAKE_MOVE -> move(conn, message);
        case LEAVE -> leave(conn, message);
        case RESIGN -> resign(conn, message);
      }
//      System.out.println("done");
//      session.getRemote().sendString("WebSocket response: done");

    }
    else{

      Connection.sendError(session, "unknown user");
    }
  }
  @OnWebSocketError
  public void onError(Throwable e){
    e.printStackTrace();
  }
  @OnWebSocketClose
  public void onClose(Session session, int statusCode, String reason){
    System.out.println(reason);
  }
  private Connection getConnection(String authToken, Session session){
    try{
      authDAO.checkAuth(authToken);
    }catch(Exception e){
      return null;
    }
    return new Connection(authToken, session);
  }

  private void join(Connection conn, String message, Session session) throws IOException {
    try {
      JoinPlayer command=new Gson().fromJson(message, JoinPlayer.class);
      GameData game=getGame(command.getGameID());
      LoadGame msg = new LoadGame(game);
      String msgJson = new Gson().toJson(msg);

      String gamePlayer=null;
      String color = null;
      if(command.getPlayerColor()== ChessGame.TeamColor.WHITE){
        gamePlayer = game.whiteUsername();
        color = "white";
      }
      else if(command.getPlayerColor()== ChessGame.TeamColor.BLACK){
        gamePlayer = game.blackUsername();
        color = "black";
      }
      else{
        conn.sendError(conn.session, "Invalid player color");
        return;
      }
      String authUsername = authDAO.getUser(command.getAuthString());
      if(!authUsername.equals(gamePlayer)){
        conn.sendError(conn.session, "PlayerColor is already taken");
        return;
      }
      conn.send(msgJson);
      String broadcastMsg = gamePlayer + " has joined " + game.gameName() + " as the "+ color +" player";
      Notification notification = new Notification(broadcastMsg);
      connections.add(game.gameID(), conn, session);
      connections.broadcast(command.getAuthString(), notification, game.gameID());
    }catch(Exception e){
      e.printStackTrace();
      conn.sendError(conn.session, e.getMessage());
    }
  }
  private void resign(Connection conn, String message) {
    Resign command =new Gson().fromJson(message, Resign.class);

  }

  private void leave(Connection conn, String message) {
    Leave command =new Gson().fromJson(message, Leave.class);
  }

  private void move(Connection conn, String message) throws IOException {
    try {
      MakeMove command=new Gson().fromJson(message, MakeMove.class);
      GameData gameData =getGame(command.getGameID());
      ChessGame game = gameData.game();
      if(game.isGameOver()){
        conn.sendError(conn.session, "Game is over, can't make move");
        return;
      }
      Collection<ChessMove> valid = game.validMoves(command.move.getStartPosition());
      if(!valid.contains(command.move)){
        conn.sendError(conn.session, "Not a valid move");
      }
      String playerName = authDAO.getUser(command.getAuthString());
      if(playerName.equals(gameData.blackUsername())){
        if(!game.getTeamTurn().equals(ChessGame.TeamColor.BLACK)){
          conn.sendError(conn.session, "It's currently white's turn");
        }
        else{
          game.makeMove(command.move);
          String msg="Black has made " + command.move + " move";
          if(game.isInStalemate(ChessGame.TeamColor.WHITE)){
            msg = msg + ", Black has put White into a Stalemate, good game!";
            game.gameOver();
          }
          else if(game.isInCheckmate(ChessGame.TeamColor.WHITE)){
            msg = msg+", Black has put White into a Checkmate, good game!";
            game.gameOver();
          }
          gameDAO.updateGameBoard(gameData.gameID(), game);
          GameData newGame = getGame(command.getGameID());
          String load = new Gson().toJson(new LoadGame(newGame), LoadGame.class);
          conn.send(load);
          Notification broadcast = new Notification(msg);
          connections.broadcast(command.authToken, broadcast, command.getGameID());;
        }
      }
      else if(playerName.equals(gameData.whiteUsername())){
        if(!game.getTeamTurn().equals(ChessGame.TeamColor.WHITE)){
          conn.sendError(conn.session, "It's currently black's turn");
        }
        else{
          game.makeMove(command.move);
          String msg="White has made " + command.move + " move";
          if(game.isInStalemate(ChessGame.TeamColor.BLACK)){
            msg = msg + ", White has put Black into a Stalemate, good game!";
            game.gameOver();
          }
          else if(game.isInCheckmate(ChessGame.TeamColor.BLACK)){
            msg = msg + ", White has put Black into a Checkmate, good game!";
            game.gameOver();
          }
          gameDAO.updateGameBoard(gameData.gameID(), game);
          GameData newGame = getGame(command.getGameID());
          String load = new Gson().toJson(new LoadGame(newGame), LoadGame.class);
          conn.send(load);
          Notification broadcast = new Notification(msg);
          connections.broadcast(command.authToken, broadcast, command.getGameID());
        }
      }
      else{
        conn.sendError(conn.session, "Observer can not make move");
      }

    }catch(Exception e){
      conn.sendError(conn.session, e.getMessage());
    }
  }

  private void observe(Connection conn, String message) throws IOException {
    JoinObserver command =new Gson().fromJson(message, JoinObserver.class);
    try {
      GameData game=getGame(command.getGameID());
      LoadGame msg = new LoadGame(game);
      String msgJson = new Gson().toJson(msg);
      String color = null;
      conn.send(msgJson);
      String gamePlayer = authDAO.getUser(command.getAuthString());
      String broadcastMsg = gamePlayer + " is observing "+game.gameName();
      Notification notification = new Notification(broadcastMsg);
      connections.add(game.gameID(), conn, session);
      connections.broadcast(command.getAuthString(), notification, game.gameID());
    }catch(Exception e){
      conn.sendError(conn.session, e.getMessage());
    }
  }
  private GameData getGame(int gameID) throws DataAccessException {
    Collection<GameData> games = gameDAO.listGames();
    Boolean found = false;
    GameData game = null;
    for(GameData i : games){
      if(gameID==i.gameID()){
        game = i;
        found = true;
        break;
      }
    }
    if(!found){
      throw new DataAccessException(500, "Wrong game ID");
    }
    return game;
  }
}

