package WebSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.Notification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<Integer, Collection<Connection>> connections = new ConcurrentHashMap<>();
  public void add(int gameID, Connection connection, Session session) {
    if(connections.get(gameID) != null){
      connections.get(gameID).add(connection);
    }
    else{
      Collection<Connection> start = new ArrayList<>();
      start.add(connection);
      connections.put(gameID, start);
    }
  }

  public void remove(int gameID, String authToken) {
    Collection<Connection> list = connections.get(gameID);
    for(Connection i: list){
      if(i.authToken.equals(authToken)){
        list.remove(i);
        break;
      }
    }
  }

  public void broadcast(String excludeAuthToken, Notification notification, int gameID) throws IOException {
    String notificationJson = new Gson().toJson(notification, Notification.class);
    for (var c : connections.get(gameID)) {
      if (c.session.isOpen()) {
        if (!c.authToken.equals(excludeAuthToken)) {
          c.send(notificationJson);
        }
      }
    }

  }

  public void broadcastGame(String loadJson, int gameID) throws IOException{
    for (var c : connections.get(gameID)) {
      if (c.session.isOpen()) {
        c.send(loadJson);

      }
    }
  }

}
