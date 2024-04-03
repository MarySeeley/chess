package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
  int gameID;
  public Leave(int gameID, String authToken) {
    super(authToken);
    this.gameID = gameID;
    this.commandType = CommandType.LEAVE;
  }
}
