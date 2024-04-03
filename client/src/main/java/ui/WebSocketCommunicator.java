package ui;

import model.GameData;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;

public class WebSocketCommunicator{
  public void joinGame(){

  }
  public void joinObserve(){

  }
  public void makeMove(){

  }
  public void leave(){

  }
  public void resign(){

  }

  public void displayNotification(String message){

  }
  public void displayError(String message){

  }
  public void loadGame(GameData game){

  }
  public void notify (ServerMessage message){
    switch (message.getServerMessageType()){
      case NOTIFICATION -> displayNotification(((Notification)message).getMessage());
      case ERROR -> displayError(((Error) message).getMessage());
      case LOAD_GAME -> loadGame(((LoadGame) message).getGame());
    }
  }

}
