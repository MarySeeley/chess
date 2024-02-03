package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMove {
  public final ChessPosition starting;
  public final ChessPiece piece;
  public ChessBoard board;
  public RookMove(ChessBoard board, ChessPosition starting, ChessPiece piece){
    //Create an array of available moves for the pawn?
    this.starting = starting;
    this.piece = piece;
    this.board = board;
  }
  public Collection<ChessMove> allMoves() {
    int startRow=starting.getRow();
    int startCol=starting.getColumn();
    Collection<ChessMove> options=new ArrayList<>();
    //Checking forward open moves
    for(int i = startRow+1; i<=8 && i>=1; i++){
      ChessPosition current = new ChessPosition(i, startCol);
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
    //Checking back open moves
    for(int i = startRow-1; i<=8 && i>=1; i--){
      ChessPosition current = new ChessPosition(i, startCol);
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
    //Check right moves
    for(int i = startCol+1; i<=8 && i>=1; i++){
      ChessPosition current = new ChessPosition(startRow, i);
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
    //Checking left moves
    for(int i = startCol-1; i<=8 && i>=1; i--){
      ChessPosition current = new ChessPosition(startRow, i);
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
