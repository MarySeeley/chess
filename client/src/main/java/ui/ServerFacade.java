package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
  private final String serverUrl;
  private final ClientCommunicator clientComm;
  public ServerFacade(String serverURL){
    this.serverUrl = serverURL;
    this.clientComm = new ClientCommunicator();
  }
  public AuthData register(String username, String password, String email) throws ResponseException, IOException {
    UserData user = new UserData(username, password, email);
    var path = "/user";
    String temp = serverUrl + path;
    return clientComm.post(temp, user, AuthData.class);
  }
  public AuthData login(String username, String password) throws IOException {
    UserData user = new UserData(username, password, null);
    String temp = serverUrl + "/session";
    return clientComm.post(temp, user, AuthData.class);
  }
  public int create(String gameName) throws IOException {
    GameData game = new GameData(0,null,null,gameName, null);
    String temp = serverUrl + "/game";
    return clientComm.post(temp, game, int.class);
  }
  public void list(){

  }
  public void join(int gameID, String player){

  }
  public void observe(int gameID){

  }
  public void logout(){

  }

  private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
    try {
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);
      writeBody(request, http);
      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (Exception ex) {
      System.out.println("Make Request error");
      ex.printStackTrace();
      throw new ResponseException(500, ex.getMessage());
    }
  }


  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      System.out.println(reqData);
      try (OutputStream reqBody = http.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
  }

  private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
    var status = http.getResponseCode();
    if (!isSuccessful(status)) {
      throw new ResponseException(status, "failure: " + status);
    }
  }

  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          response = new Gson().fromJson(reader, responseClass);
        }
      }
    }
    return response;
  }


  private boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
}
