package chess;

import java.util.Collection;

public class PawnMove {
  public final ChessPosition starting;
  public final ChessPiece piece;
  public ChessBoard board;
  public PawnMove(ChessBoard board, ChessPosition starting, ChessPiece piece){
    //Create an array of available moves for the pawn?
    this.starting = starting;
    this.piece = piece;
    this.board = board;
  }
  public Collection<ChessMove> allMoves(){
    int startRow = starting.getRow();
    int startCol = starting.getColumn();
    Collection<ChessMove> options=null;
    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
      ChessPosition whiteForwardPos = new ChessPosition(startRow+1, startCol);
      if(board.getPiece(whiteForwardPos)==null){
        if(startRow+1 == 8){
          //loop through each piece as an option to replace???
          ChessMove whiteFowardMove = new ChessMove(starting, whiteForwardPos, ChessPiece.PieceType.QUEEN);
          options.add(whiteFowardMove);
        }
        options.add(null);
      }
    }
    else{

    }
    return null;
  }
}
