package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import model.*;

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
  private AuthData auth=null;

  public ServerFacade(String serverURL) {
    this.serverUrl=serverURL;
    this.clientComm=new ClientCommunicator();
  }

  public AuthData register(String username, String password, String email) throws ResponseException, IOException {
    UserData user=new UserData(username, password, email);
    var path="/user";
    String temp=serverUrl + path;

    auth=clientComm.post(temp, user, AuthData.class, null, null);
    return auth;
  }

  public AuthData login(String username, String password) throws IOException {
    UserData user=new UserData(username, password, null);
    String temp=serverUrl + "/session";
    auth=clientComm.post(temp, user, AuthData.class, null, null);
    return auth;
  }

  public GameData create(String gameName) throws IOException {
    GameData game=new GameData(0, null, null, gameName, null);
    String temp=serverUrl + "/game";
    return clientComm.post(temp, game, GameData.class, "Authorization", auth.authToken());
  }

  public Collection<GameData> list() throws IOException {
    String temp=serverUrl + "/game";
    record listGames(Collection<GameData> gameList) {
    }
    var list=clientComm.get(temp, ListData.class, "Authorization", auth.authToken());
    return list.games();
  }

  public void join(int gameID, String player) throws IOException {
    String temp=serverUrl + "/game";
    player=player.toUpperCase();
    JoinData join=new JoinData(player, gameID);
    System.out.println(join);
    clientComm.put(temp, join, "Authorization", auth.authToken());
  }

  public void observe(int gameID) throws IOException {
    String temp=serverUrl + "/game";
    JoinData observe=new JoinData(null, gameID);
    clientComm.put(temp, observe, "Authorization", auth.authToken());
  }

  public void logout() throws IOException {
    String temp=serverUrl + "/session";
    clientComm.delete(temp, "Authorization", auth.authToken());
    auth=null;
  }
}
