package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class BishopMove {
  private final ChessPiece piece;
  private final ChessPosition starting;
  private final ChessGame.TeamColor color;
  private final int startRow;
  private final int startCol;
  private ChessBoard board;
  public BishopMove(ChessPiece piece, ChessPosition starting, ChessGame.TeamColor color, ChessBoard board){
    this.piece = piece;
    this.starting = starting;
    this.color = color;
    this.startRow = starting.getRow();
    this.startCol = starting.getColumn();
    this.board = board;
  }
  public Collection<ChessMove> allMoves(){
    Collection<ChessMove> options = new ArrayList<>();
    // up right
    for(int i = startRow+1, j = startCol+1; i<=8 && i>=1 && j<=8 && j>=1; i++, j++){
      ChessPosition newPos = new ChessPosition(i, j);
      if(board.getPiece(newPos) == null){
        options.add(new ChessMove(starting, newPos, null));
      }
      else if(board.getPiece(newPos).getTeamColor() != color){
        options.add(new ChessMove(starting, newPos, null));
        break;
      }
      else{
        break;
      }
    }
    //up left
    for(int i = startRow+1, j = startCol-1; i<=8 && i>=1 && j<=8 && j>=1; i++, j--){
      ChessPosition newPos = new ChessPosition(i, j);
      if(board.getPiece(newPos) == null){
        options.add(new ChessMove(starting, newPos, null));
      }
      else if(board.getPiece(newPos).getTeamColor() != color){
        options.add(new ChessMove(starting, newPos, null));
        break;
      }
      else{
        break;
      }
    }
    //down right
    for(int i = startRow-1, j = startCol+1; i<=8 && i>=1 && j<=8 && j>=1; i--, j++){
      ChessPosition newPos = new ChessPosition(i, j);
      if(board.getPiece(newPos) == null){
        options.add(new ChessMove(starting, newPos, null));
      }
      else if(board.getPiece(newPos).getTeamColor() != color){
        options.add(new ChessMove(starting, newPos, null));
        break;
      }
      else{
        break;
      }
    }
    //down left
    for(int i = startRow-1, j = startCol-1; i<=8 && i>=1 && j<=8 && j>=1; i--, j--){
      ChessPosition newPos = new ChessPosition(i, j);
      if(board.getPiece(newPos) == null){
        options.add(new ChessMove(starting, newPos, null));
      }
      else if(board.getPiece(newPos).getTeamColor() != color){
        options.add(new ChessMove(starting, newPos, null));
        break;
      }
      else{
        break;
      }
    }
    return options;
  }
}

