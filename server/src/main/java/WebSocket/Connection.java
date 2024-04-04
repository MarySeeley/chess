package WebSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.Error;

import java.io.IOException;

public class Connection {
  public String authToken;
  public Session session;
  public int gameID;

  public Connection(String authToken, Session session) {
    this.authToken = authToken;
    this.session = session;
  }
  public void setGameID(int gameID){
    this.gameID = gameID;
  }

  public static void sendError(Session session, String msg) throws IOException {
//    System.out.println(msg);
    String errormsg = "Error: "+msg;
    System.out.println(errormsg);
    Error errorMessage = new Error(errormsg);
    String errorJson = new Gson().toJson(errorMessage, Error.class);
    session.getRemote().sendString(errorJson);
  }
  public void send(String msg) throws IOException {

    session.getRemote().sendString(msg);
  }
}
