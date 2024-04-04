package webSocketMessages.serverMessages;

import chess.ChessGame;
import model.GameData;

public class LoadGame extends ServerMessage{
  private GameData game;
  public LoadGame(GameData game){
    super(ServerMessage.ServerMessageType.LOAD_GAME);
    this.game = game;
  }
  public GameData getGame(){
    return game;
  }
}
