package chess;

import java.util.ArrayList;
import java.util.Collection;

//public class QueenMove {
//  public final ChessPosition starting;
//  public final ChessPiece piece;
//  public ChessBoard board;
public class QueenMove {
  private final ChessPiece piece;
  private final ChessBoard board;
  private final ChessPiece.PieceType type;
  private final ChessPosition starting;
  private final ChessGame.TeamColor color;
  public QueenMove(ChessPiece piece, ChessBoard board, ChessPosition position){
    this.piece = piece;
    this.board = board;
    this.type = piece.getPieceType();
    this.starting = position;
    this.color = piece.getTeamColor();
  }
  public Collection<ChessMove> allMoves(){
    RookMove rook = new RookMove(board, starting, piece);
    BishopMove bishop = new BishopMove(piece, starting, piece.getTeamColor(), board);
    Collection<ChessMove> options = rook.allMoves();
    options.addAll(bishop.allMoves());
    return options;
  }
}
//  public QueenMove(ChessBoard board, ChessPosition starting, ChessPiece piece){
//    //Create an array of available moves for the pawn?
//    this.starting = starting;
//    this.piece = piece;
//    this.board = board;
//  }
//  public Collection<ChessMove> allMoves() {
//    int startRow=starting.getRow();
//    int startCol=starting.getColumn();
//    Collection<ChessMove> options=new ArrayList<>();
//    for(int i = startRow+1; i<=8 && i>=1; i++){
//      ChessPosition current = new ChessPosition(i, startCol);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    for(int i = startRow-1; i<=8 && i>=1; i--){
//      ChessPosition current = new ChessPosition(i, startCol);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    for(int i = startCol+1; i<=8 && i>=1; i++){
//      ChessPosition current = new ChessPosition(startRow, i);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    for(int i = startCol-1; i<=8 && i>=1; i--){
//      ChessPosition current = new ChessPosition(startRow, i);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    for(int i = startRow+1, j = startCol+1; i<=8 && j<=8 && i>=1 && j>=1; j++, i++){
//      ChessPosition current = new ChessPosition(i, j);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    for(int i = startRow+1, j = startCol-1; i<=8 && j<=8 && i>=1 && j>=1; j--, i++){
//      ChessPosition current = new ChessPosition(i, j);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    for(int i = startRow-1, j = startCol-1; i<=8 && j<=8 && i>=1 && j>=1; j--, i--){
//      ChessPosition current = new ChessPosition(i, j);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    for(int i = startRow-1, j = startCol+1; i<=8 && j<=8 && i>=1 && j>=1; j++, i--){
//      ChessPosition current = new ChessPosition(i, j);
//      ChessMove currentMove = new ChessMove(starting, current, null);
//      if(board.getPiece(current) == null){
//        options.add(currentMove);
//      }
//      else if(board.getPiece(current).getTeamColor() == piece.getTeamColor()){
//        break;
//      }
//      else if(board.getPiece(current) != null){
//        options.add(currentMove);
//        break;
//      }
//    }
//    return options;
//  }
//}
