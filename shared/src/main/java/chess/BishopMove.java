package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class BishopMove {
  public final ChessPosition starting;
  public final ChessPiece piece;
  public ChessBoard board;
  public BishopMove(ChessBoard board, ChessPosition starting, ChessPiece piece){
    //Create an array of available moves for the pawn?
    this.starting = starting;
    this.piece = piece;
    this.board = board;
  }
  public Collection<ChessMove> allMoves() {
    int startRow=starting.getRow();
    int startCol=starting.getColumn();
    Collection<ChessMove> options = new ArrayList<>();
    //Checking up and to the right of bishop
    for(int i = startRow+1, j = startCol+1; i<=8 && j<=8 && i>=1 && j>=1; j++, i++){
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
    //Checking up and to the left
    for(int i = startRow+1, j = startCol-1; i<=8 && j<=8 && i>=1 && j>=1; j--, i++){
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
    //Checking down and to the left
    for(int i = startRow-1, j = startCol-1; i<=8 && j<=8 && i>=1 && j>=1; j--, i--){
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
    //Checking down and to the right
    for(int i = startRow-1, j = startCol+1; i<=8 && j<=8 && i>=1 && j>=1; j++, i--){
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
    return options;
  }
}
