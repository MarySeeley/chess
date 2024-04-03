package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{
  int gameID;
  public Resign(int gameID, String authToken) {
    super(authToken);
    this.gameID = gameID;
    this.commandType = CommandType.RESIGN;
  }
}
