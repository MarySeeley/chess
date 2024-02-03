package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor color;
    private ChessBoard squares = new ChessBoard();
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.color;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.color = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
        //Set start to null
        //Set end to piece or promotion piece

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessPiece.PieceType> promotion = new ArrayList<>();
        promotion.add(ChessPiece.PieceType.KNIGHT);
        promotion.add(ChessPiece.PieceType.BISHOP);
        promotion.add(ChessPiece.PieceType.ROOK);
        promotion.add(ChessPiece.PieceType.QUEEN);
        ChessPosition kingPos = findKing(teamColor);
        for(int row = 1; row <= 8; row++) {
            for (int col=1; col <= 8; col++) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = squares.getPiece(pos);
                if (piece != null && piece.getTeamColor() != teamColor){
                    Collection<ChessMove> moves= piece.pieceMoves(squares, pos);
                    if(moves.contains(new ChessMove(pos, kingPos, null))){
                        return true;
                    }
                    if(piece.getPieceType()==ChessPiece.PieceType.PAWN) {
                        for (ChessPiece.PieceType type : promotion) {
                            if (moves.contains(new ChessMove(pos, kingPos, type))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public ChessPosition findKing(TeamColor teamColor){
        for(int row = 1; row <= 8; row++){
            for(int col = 1; col <=8; col ++){
                ChessPosition pos = new ChessPosition(row,col);
                ChessPiece piece = squares.getPiece(pos);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                    return pos;
                }
            }
        }
        return null;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessPiece.PieceType> promotion = new ArrayList<>();
        promotion.add(ChessPiece.PieceType.KNIGHT);
        promotion.add(ChessPiece.PieceType.BISHOP);
        promotion.add(ChessPiece.PieceType.ROOK);
        promotion.add(ChessPiece.PieceType.QUEEN);
        if(isInCheck(teamColor)){
            ChessPosition kingPos = findKing(teamColor);
            ChessPiece king = squares.getPiece(kingPos);
            Collection<ChessMove> kingMoves = king.pieceMoves(squares, kingPos);
            for(int row = 1; row <= 8; row++) {
                for (int col=1; col <= 8; col++) {
                    ChessPosition pos=new ChessPosition(row, col);
                    ChessPiece piece=squares.getPiece(pos);
                    if(piece != null && piece.getTeamColor() != teamColor){
                        Collection<ChessMove> moves = piece.pieceMoves(squares, pos);
                        for(ChessMove move: moves){
                            if(moves.contains(new ChessMove(pos, move.getEndPosition(), null))){
                                return true;
                            }
                            if(piece.getPieceType()==ChessPiece.PieceType.PAWN) {
                                for (ChessPiece.PieceType type : promotion) {
                                    if (moves.contains(new ChessMove(pos, move.getEndPosition(), type))) {
                                        return true;
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.squares = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.squares;
    }
}
