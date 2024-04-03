package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {
  int gameID;
  ChessGame.TeamColor playerColor;
  public JoinPlayer(int gameID, ChessGame.TeamColor playerColor, String authToken){
    super(authToken);
    this.gameID = gameID;
    this.playerColor = playerColor;
    this.commandType = CommandType.JOIN_PLAYER;

  }
  public int getGameID(){
    return this.gameID;
  }
  public ChessGame.TeamColor getPlayerColor(){
    return this.playerColor;
  }

}
