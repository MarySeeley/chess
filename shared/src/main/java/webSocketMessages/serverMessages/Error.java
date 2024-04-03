package webSocketMessages.serverMessages;

public class Error extends ServerMessage {
  private String errorMessage;
  public Error(String message){
    super(ServerMessage.ServerMessageType.ERROR);
    this.errorMessage = message;
  }
  public String getMessage(){
    return errorMessage;
  }
}
