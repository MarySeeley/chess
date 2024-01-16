package chess;

import java.util.ArrayList;
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
    Collection<ChessMove> options=new ArrayList<>();
    Collection<ChessPiece.PieceType> promotionType = new ArrayList<>();
    promotionType.add(ChessPiece.PieceType.QUEEN);
    promotionType.add(ChessPiece.PieceType.ROOK);
    promotionType.add(ChessPiece.PieceType.KNIGHT);
    promotionType.add(ChessPiece.PieceType.BISHOP);
    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
      ChessPosition whiteForwardPos = new ChessPosition(startRow+1, startCol);
      if(board.getPiece(whiteForwardPos)==null){
        if(startRow+1 == 8) {
          for(ChessPiece.PieceType n : promotionType){
            options.add(new ChessMove(starting, whiteForwardPos, n));
          }
          //loop through each piece as an option to replace???
//          options.add(new ChessMove(starting, whiteForwardPos, ChessPiece.PieceType.KNIGHT));
//          options.add(new ChessMove(starting, whiteForwardPos, ChessPiece.PieceType.QUEEN));
//          options.add(new ChessMove(starting, whiteForwardPos, ChessPiece.PieceType.ROOK));
//          options.add(new ChessMove(starting, whiteForwardPos, ChessPiece.PieceType.BISHOP));
          ChessPosition rightDiagonalCapture=new ChessPosition(startRow + 1, startCol + 1);
          if (board.getPiece(rightDiagonalCapture) != null && board.getPiece(rightDiagonalCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
            for(ChessPiece.PieceType n : promotionType){
              options.add(new ChessMove(starting, rightDiagonalCapture, n));
            }
//            options.add(new ChessMove(starting, rightDiagonalCapture, ChessPiece.PieceType.KNIGHT));
//            options.add(new ChessMove(starting, rightDiagonalCapture, ChessPiece.PieceType.QUEEN));
//            options.add(new ChessMove(starting, rightDiagonalCapture, ChessPiece.PieceType.ROOK));
//            options.add(new ChessMove(starting, rightDiagonalCapture, ChessPiece.PieceType.BISHOP));
          }
          ChessPosition leftDiagonalCapture=new ChessPosition(startRow + 1, startCol - 1);
          if (board.getPiece(leftDiagonalCapture) != null && board.getPiece(leftDiagonalCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
            for(ChessPiece.PieceType n : promotionType){
              options.add(new ChessMove(starting, leftDiagonalCapture, n));
            }
//            options.add(new ChessMove(starting, leftDiagonalCapture, ChessPiece.PieceType.KNIGHT));
//            options.add(new ChessMove(starting, leftDiagonalCapture, ChessPiece.PieceType.QUEEN));
//            options.add(new ChessMove(starting, leftDiagonalCapture, ChessPiece.PieceType.ROOK));
//            options.add(new ChessMove(starting, leftDiagonalCapture, ChessPiece.PieceType.BISHOP));
          }
        }
        else {
          options.add(new ChessMove(starting, whiteForwardPos, null));
        }
        ChessPosition doubleMove = new ChessPosition(startRow+2, startCol);
        if(startRow == 2 &&  board.getPiece(doubleMove) == null){
          options.add(new ChessMove(starting, doubleMove, null));
        }
      }
      ChessPosition rightDiagonal = new ChessPosition(startRow+1, startCol+1);
      if(startRow+1 <=8 && startCol+1 <=8&& startRow+1 !=8){
        if(board.getPiece(rightDiagonal) != null && board.getPiece(rightDiagonal).getTeamColor() == ChessGame.TeamColor.BLACK){
          options.add(new ChessMove(starting, rightDiagonal, null));
        }
      }
      ChessPosition leftDiagonal = new ChessPosition(startRow+1, startCol-1);
      if(startRow+1 <=8 && startCol-1 >=1 && startRow+1 !=8){
        if(board.getPiece(leftDiagonal) != null && board.getPiece(leftDiagonal).getTeamColor() == ChessGame.TeamColor.BLACK){
          options.add(new ChessMove(starting, leftDiagonal, null));
        }
      }


    }
    else{
      ChessPosition blackForwardPos = new ChessPosition(startRow-1, startCol);
      if(board.getPiece(blackForwardPos)==null){
        if(startRow-1 == 1) {
          for (ChessPiece.PieceType n : promotionType) {
            options.add(new ChessMove(starting, blackForwardPos, n));
          }
          //loop through each piece as an option to replace???
//          options.add(new ChessMove(starting, blackForwardPos, ChessPiece.PieceType.ROOK));
//          options.add(new ChessMove(starting, blackForwardPos, ChessPiece.PieceType.QUEEN));
//          options.add(new ChessMove(starting, blackForwardPos, ChessPiece.PieceType.BISHOP));
//          options.add(new ChessMove(starting, blackForwardPos, ChessPiece.PieceType.KNIGHT));
          ChessPosition rightDiagonal=new ChessPosition(startRow - 1, startCol + 1);
          ChessPosition leftDiagonal=new ChessPosition(startRow - 1, startCol - 1);
          if (board.getPiece(rightDiagonal) != null && board.getPiece(rightDiagonal).getTeamColor() == ChessGame.TeamColor.WHITE) {
            for (ChessPiece.PieceType n : promotionType) {
              options.add(new ChessMove(starting, rightDiagonal, n));
            }
          }
          else if (board.getPiece(leftDiagonal) != null && board.getPiece(leftDiagonal).getTeamColor() == ChessGame.TeamColor.WHITE) {
            for (ChessPiece.PieceType n : promotionType) {
              options.add(new ChessMove(starting, leftDiagonal, n));
            }
          }
        }
        else{
          options.add(new ChessMove(starting, blackForwardPos, null));
        }
        ChessPosition doubleMove = new ChessPosition(startRow-2, startCol);
        if(startRow == 7 && board.getPiece(doubleMove) == null){
          options.add(new ChessMove(starting, doubleMove, null));
        }
      }
      ChessPosition rightDiagonal = new ChessPosition(startRow-1, startCol+1);
      if(startRow-1 <=8 && startCol+1 <=8 && startRow-1 != 1) {
        if (board.getPiece(rightDiagonal) != null && board.getPiece(rightDiagonal).getTeamColor() == ChessGame.TeamColor.WHITE) {
          options.add(new ChessMove(starting, rightDiagonal, null));
        }
      }
      ChessPosition leftDiagonal = new ChessPosition(startRow-1, startCol-1);
      if(startRow-1 >=1 && startCol-1 >=1 && startRow-1 != 1){
        if(board.getPiece(leftDiagonal) != null && board.getPiece(leftDiagonal).getTeamColor() == ChessGame.TeamColor.WHITE){
          options.add(new ChessMove(starting, leftDiagonal, null));
        }
      }
    }
    return options;
  }
}
