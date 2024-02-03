package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMove {
  public final ChessPosition starting;
  public final ChessPiece piece;
  public ChessBoard board;
  public KnightMove(ChessBoard board, ChessPosition starting, ChessPiece piece){
    //Create an array of available moves for the pawn?
    this.starting = starting;
    this.piece = piece;
    this.board = board;
  }
  public Collection<ChessMove> allMoves() {
    int startRow=starting.getRow();
    int startCol=starting.getColumn();
    Collection<ChessMove> options=new ArrayList<>();
    int[] dirRow = {startRow-2,startRow-1, startRow+1, startRow+2};
    int[] dirColAbove = {startCol+1, startCol+2, startCol+2, startCol+1};
    int[] dirColBelow = {startCol-1, startCol-2, startCol-2, startCol-1};
    //Check moves going down and up by 2
    //moves going 1 to both sides
    for(int i = startRow-2; i <= startRow+3; i+=4){
      for(int j = startCol-1; j <= startCol+2; j+=2){
        if(i<1||i>8||j<1||j>8){
          continue;
        }
        ChessPosition current=new ChessPosition(i, j);
        ChessMove currentMove=new ChessMove(starting, current, null);
        if (board.getPiece(current) == null) {
          options.add(currentMove);
        } else if (board.getPiece(current).getTeamColor() == piece.getTeamColor()) {
          continue;
        } else if (board.getPiece(current) != null) {
          options.add(currentMove);
        }
      }
    }
    //Check moves going left and right by 2
    //moves going 1 up and down
    for(int i = startCol-2; i <= startCol+3; i+=4){
      for(int j = startRow-1; j<=startRow+2; j+=2){
        if(i<1||i>8||j<1||j>8){
          continue;
        }
        ChessPosition current=new ChessPosition(j, i);
        ChessMove currentMove=new ChessMove(starting, current, null);
        if (board.getPiece(current) == null) {
          options.add(currentMove);
        } else if (board.getPiece(current).getTeamColor() == piece.getTeamColor()) {
          continue;
        } else if (board.getPiece(current) != null){
          options.add(currentMove);
        }
      }
    }
    return options;
  }
}
