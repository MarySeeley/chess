package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor color;
    public ChessBoard squares = new ChessBoard();
    Boolean over;
    public static ChessGame createNewGame(){
        ChessGame game = new ChessGame();
        game.color = TeamColor.WHITE;
        game.over = false;
        game.squares.resetBoard();
        return game;
    }
    public ChessGame() {

    }
    public ChessGame copy(){
        ChessGame cop = new ChessGame();
        cop.color = this.color;
        cop.squares = squares.copy();
        return cop;
    }

    public boolean isGameOver(){
        return this.over;
    }
    public void gameOver(){
        this.over = true;
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
        ChessPiece piece = squares.getPiece(startPosition);
        if(piece == null){
            return null;
        }
        Collection<ChessMove> moves = piece.pieceMoves(squares, startPosition);
        Collection<ChessMove> actualMoves = new ArrayList<>();
        for(ChessMove move: moves){
            ChessGame copyGame = this.copy();
            ChessBoard copyBoard = copyGame.squares;
            copyBoard.addPiece(move.getEndPosition(), piece);
            copyBoard.addPiece(startPosition, null);
            if(!copyGame.isInCheck(piece.getTeamColor())){
                actualMoves.add(move);
            }
        }

        HashSet<ChessMove> hashMoves = new HashSet<>(actualMoves);
        return hashMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = squares.getPiece(move.getStartPosition());
        if(piece == null){
            throw new InvalidMoveException("There is no piece in the starting position");
        }
        if(piece.getTeamColor() != color){
            throw new InvalidMoveException("Not teams turn");
        }
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if(!moves.contains(move)){
            throw new InvalidMoveException("Not a valid move");
        }
        if(move.getPromotionPiece() != null ){
            squares.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.promotion));
            squares.addPiece(move.getStartPosition(), null);
        }
        else{
            squares.addPiece(move.getEndPosition(), piece);
            squares.addPiece(move.getStartPosition(), null);
        }
        if(piece.getTeamColor() == TeamColor.BLACK){
            setTeamTurn(TeamColor.WHITE);
        }
        else{
            setTeamTurn(TeamColor.BLACK);
        }
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
        if(teamColor != getTeamTurn()){
            return false;
        }
        for(int row = 1; row <= 8; row++) {
            for (int col=1; col <= 8; col++) {
                ChessPiece piece = squares.getPiece(new ChessPosition(row, col));
                if(piece != null && piece.getTeamColor() == teamColor){
                    Collection<ChessMove> moves = validMoves(new ChessPosition(row, col));
                    if(!moves.isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
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
