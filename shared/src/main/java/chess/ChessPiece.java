package chess;

import java.util.ArrayList;
import java.util.Collection;

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
            case QUEEN:
                System.out.println("queen moves");
                break;
            case BISHOP:
                BishopMove bishop = new BishopMove(board, myPosition, piece);
                moves = bishop.allMoves();
                break;
            case KNIGHT:
                System.out.println("knight moves");
                break;
            case ROOK:
                RookMove rook = new RookMove(board, myPosition, piece);
                moves = rook.allMoves();
                break;
            case PAWN:
                System.out.println("pawn moves");
                break;
            default:
                System.out.println("invalid moves");
        }
        // Returns a collection of how this piece can move
        return moves;
    }
}
