package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand{
  int gameID;
  public ChessMove move;
  public MakeMove(int gameID, ChessMove move, String authToken) {
    super(authToken);
    this.gameID = gameID;
    this.move = move;
    this.commandType = CommandType.MAKE_MOVE;
  }
  public int getGameID(){
    return this.gameID;
  }
}
