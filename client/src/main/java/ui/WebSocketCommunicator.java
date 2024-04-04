package ui;

import com.google.gson.Gson;
import model.GameData;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

public class WebSocketCommunicator{
  public void displayNotification(String jsonMessage){
    Notification notification = new Gson().fromJson(jsonMessage, Notification.class);
    System.out.println(notification.getMessage());
  }
  public void displayError(String jsonMessage){
    Error error = new Gson().fromJson(jsonMessage, Error.class);
    System.out.println(error.getMessage());
  }
  public void loadGame(String jsonMessage){
    LoadGame loadGame = new Gson().fromJson(jsonMessage, LoadGame.class);
    GameData game = loadGame.getGame();
    ChessBoardUI.main(game.game(), true, null, null);
  }
  public void notify (ServerMessage message, String jsonMessage){
    switch (message.getServerMessageType()){
      case NOTIFICATION -> displayNotification(jsonMessage);
      case ERROR -> displayError(jsonMessage);
      case LOAD_GAME -> loadGame(jsonMessage);
    }
  }

}
