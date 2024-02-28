package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
public class PawnMove {
  public final ChessPosition starting;
  public final ChessPiece piece;
  public ChessBoard board;
  public final Collection<ChessPiece.PieceType> promotionType;
  public PawnMove(ChessBoard board, ChessPosition starting, ChessPiece piece){
    //Create an array of available moves for the pawn?
    this.starting = starting;
    this.piece = piece;
    this.board = board;
    this.promotionType = new ArrayList<>();
    promotionType.add(ChessPiece.PieceType.QUEEN);
    promotionType.add(ChessPiece.PieceType.ROOK);
    promotionType.add(ChessPiece.PieceType.KNIGHT);
    promotionType.add(ChessPiece.PieceType.BISHOP);
  }
  public Collection<ChessMove> allMoves(){
    int startRow = starting.getRow();
    int startCol = starting.getColumn();
    Collection<ChessMove> options=new ArrayList<>();
    //Array full of different promotion types

    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
      ChessPosition whiteForwardPos = new ChessPosition(startRow+1, startCol);
      if(board.getPiece(whiteForwardPos)==null){
        if(startRow+1 == 8) {
          for(ChessPiece.PieceType n : promotionType){
            options.add(new ChessMove(starting, whiteForwardPos, n));
          }
          ChessPosition rightDiagonalCapture=new ChessPosition(startRow + 1, startCol + 1);
          if (board.getPiece(rightDiagonalCapture) != null && board.getPiece(rightDiagonalCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
            for(ChessPiece.PieceType n : promotionType){
              options.add(new ChessMove(starting, rightDiagonalCapture, n));
            }
          }
          ChessPosition leftDiagonalCapture=new ChessPosition(startRow + 1, startCol - 1);
          if (board.getPiece(leftDiagonalCapture) != null && board.getPiece(leftDiagonalCapture).getTeamColor() == ChessGame.TeamColor.BLACK) {
            for(ChessPiece.PieceType n : promotionType){
              options.add(new ChessMove(starting, leftDiagonalCapture, n));
            }
          }
        }
        else {
          options.add(new ChessMove(starting, whiteForwardPos, null));
        }
        //If it's first move then add double move
        ChessPosition doubleMove = new ChessPosition(startRow+2, startCol);
        if(startRow == 2 &&  board.getPiece(doubleMove) == null){
          options.add(new ChessMove(starting, doubleMove, null));
        }
      }
      //Checking to capture sides (add promotion check here)
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
    HashSet<ChessMove> hashSetOptions = new HashSet<>(options);
    return hashSetOptions;
  }
}
