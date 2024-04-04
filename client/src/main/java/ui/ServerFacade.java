package ui;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import exception.ResponseException;
import model.*;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collection;

public class ServerFacade {
  private final String serverUrl;
  public final ClientCommunicator clientComm;
  public AuthData auth=null;
  public int gameID = -1;

  public final WebSocketClient webSocket;

  public ServerFacade(String serverURL, NotificationHandler notificationHandler){
    this.serverUrl=serverURL;
    this.clientComm=new ClientCommunicator();
    webSocket = new WebSocketClient(notificationHandler);
  }

  public AuthData register(String username, String password, String email) throws ResponseException, IOException {
    UserData user=new UserData(username, password, email);
    var path="/user";
    String temp=serverUrl + path;

    auth=clientComm.post(temp, user, AuthData.class, null, null);
    if(auth.authToken()==null){
      System.out.println("You can't re-register a user");
      throw new ResponseException(403, "Can't re-register");
    }
    return auth;
  }

  public AuthData login(String username, String password) throws IOException, ResponseException {
    UserData user=new UserData(username, password, null);
    String temp=serverUrl + "/session";
    auth=clientComm.post(temp, user, AuthData.class, null, null);
    if(auth.authToken()== null){
      System.out.println("This user is not registered");
      throw new ResponseException(401, "Unauthorized");
    }
    return auth;
  }

  public CreateGameData create(String gameName) throws IOException {
    GameData game=new GameData(0, null, null, gameName, null);
    String temp=serverUrl + "/game";
    return clientComm.post(temp, game, CreateGameData.class, "Authorization", auth.authToken());
  }

  public Collection<GameData> list() throws IOException {
    String temp=serverUrl + "/game";
    var list=clientComm.get(temp, ListData.class, "Authorization", auth.authToken());
    Collection<GameData> games = (Collection<GameData>) list.games();
    return games;
  }

  public void join(int gameID, String player, ChessGame.TeamColor color) throws IOException {
    String temp=serverUrl + "/game";
    player=player.toUpperCase();
    JoinData join=new JoinData(player, gameID);
    clientComm.put(temp, join, "Authorization", auth.authToken());
    JoinPlayer joinPlayer = new JoinPlayer(gameID, color, auth.authToken());
    webSocket.send(new Gson().toJson(joinPlayer));
    this.gameID = gameID;
  }

  public void observe(int gameID) throws IOException {
    String temp=serverUrl + "/game";
    JoinData observe=new JoinData(null, gameID);
    clientComm.put(temp, observe, "Authorization", auth.authToken());
    JoinObserver joinObserver = new JoinObserver(gameID, auth.authToken());
    webSocket.send(new Gson().toJson(joinObserver));
    this.gameID = gameID;
  }

  public void logout() throws IOException {
    String temp=serverUrl + "/session";
    clientComm.delete(temp, "Authorization", auth.authToken());
    auth=null;
  }

  public void leave(){
    Leave leave = new Leave(gameID, auth.authToken());
    webSocket.send(new Gson().toJson(leave));
    this.gameID = -1;
  }

  public void makeMove(ChessMove chessMove){
    MakeMove makeMove = new MakeMove(gameID, chessMove, auth.authToken());
    webSocket.send(new Gson().toJson(makeMove));
  }
  public void resign(){
    Resign resign = new Resign(gameID, auth.authToken());
    webSocket.send(new Gson().toJson(resign));
  }

}
