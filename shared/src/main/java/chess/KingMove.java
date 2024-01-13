package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMove {
  public final ChessPosition starting;
  public final ChessPiece piece;
  public ChessBoard board;
  public KingMove(ChessBoard board, ChessPosition starting, ChessPiece piece){
    //Create an array of available moves for the pawn?
    this.starting = starting;
    this.piece = piece;
    this.board = board;
  }
  public Collection<ChessMove> allMoves() {
    int startRow=starting.getRow();
    int startCol=starting.getColumn();
    Collection<ChessMove> options=new ArrayList<>();
    int[] directionRow = {startRow-1, startRow, startRow+1};
    int[] directionCol = {startCol-1, startCol, startCol+1};
    for(int i : directionRow){
      for(int j : directionCol){
        if(i == startRow && j == startCol){
          continue;
        }
        if(i > 8 || i<1 || j>8 || j<1){
          break;
        }
        ChessPosition current = new ChessPosition(i, j);
        ChessMove currentMove = new ChessMove(starting, current, null);
        if(board.getPiece(current) == null){
          options.add(currentMove);
        }
        else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
          break;
        }
        else if(board.getPiece(current) != null){
          options.add(currentMove);
          break;
        }
      }
    }
    return options;
  }
}
