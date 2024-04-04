package ui;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.net.URI;

public class WebSocketClient extends Endpoint {
  private Session session;
  private WebSocketCommunicator observer = new WebSocketCommunicator();
  public WebSocketClient(){
    try {
      URI uri=new URI("ws://localhost:8080/connect");
      WebSocketContainer container=ContainerProvider.getWebSocketContainer();
      this.session=container.connectToServer(this, uri);
    }catch(Exception e){
      e.printStackTrace();
    }
    this.session.addMessageHandler(new MessageHandler.Whole<String>(){

      @Override
      public void onMessage(String s) {
        try{
          ServerMessage message = new Gson().fromJson(s,ServerMessage.class);
          observer.notify(message, s);
        }catch(Exception ex){
          System.out.println("error client");
          ex.printStackTrace();
          Error error = new Error(ex.getMessage());
          observer.notify(new ServerMessage(ServerMessage.ServerMessageType.ERROR), new Gson().toJson(error));
        }
      }
    });
  }
  public  void send(String msg){
    try {
      // use this for all, join sends auth token
      this.session.getBasicRemote().sendText(msg);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {

  }

}
