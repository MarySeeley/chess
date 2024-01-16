package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "ChessPiece{" + "pieceColor=" + pieceColor + ", type=" + type + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that=(ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> moves=null;
        switch (piece.type){
            case KING:
                KingMove king = new KingMove(board, myPosition, piece);
                moves = king.allMoves();
                break;
            case QUEEN:
                QueenMove queen = new QueenMove(board, myPosition, piece);
                moves = queen.allMoves();
                break;
            case BISHOP:
                BishopMove bishop = new BishopMove(board, myPosition, piece);
                moves = bishop.allMoves();
                break;
            case KNIGHT:
                KnightMove knight = new KnightMove(board, myPosition, piece);
                moves = knight.allMoves();
                break;
            case ROOK:
                RookMove rook = new RookMove(board, myPosition, piece);
                moves = rook.allMoves();
                break;
            case PAWN:
                PawnMove pawn = new PawnMove(board, myPosition,piece);
                moves = pawn.allMoves();
                break;
            default:
                System.out.println("invalid moves");
        }
        // Returns a collection of how this piece can move
        return moves;
    }
}
