package chess;

import java.util.ArrayList;
import java.util.Collection;

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
