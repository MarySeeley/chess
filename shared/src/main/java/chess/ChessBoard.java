package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        this.squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return this.squares[position.getRow()-1][position.getColumn()-1];
    }

    @Override
    public String toString() {
        String print = "";
         for(ChessPiece[] i : squares){
             print += Arrays.toString(i)+"\n";
         }
        return "ChessBoard{" + "squares=" + print + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that=(ChessBoard) o;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessGame.TeamColor color;
        for(int i = 1; i <= 8; i+=7){

            if(i <= 2){
                color = ChessGame.TeamColor.WHITE;
            }
            else{
                color = ChessGame.TeamColor.BLACK;
            }
            addPiece(new ChessPosition(i,1), new ChessPiece(color, ChessPiece.PieceType.ROOK));
            addPiece(new ChessPosition(i,2), new ChessPiece(color, ChessPiece.PieceType.KNIGHT));
            addPiece(new ChessPosition(i,3), new ChessPiece(color, ChessPiece.PieceType.BISHOP));
            addPiece(new ChessPosition(i,4), new ChessPiece(color, ChessPiece.PieceType.QUEEN));
            addPiece(new ChessPosition(i,5), new ChessPiece(color, ChessPiece.PieceType.KING));
            addPiece(new ChessPosition(i,6), new ChessPiece(color, ChessPiece.PieceType.BISHOP));
            addPiece(new ChessPosition(i,7), new ChessPiece(color, ChessPiece.PieceType.KNIGHT));
            addPiece(new ChessPosition(i,8), new ChessPiece(color, ChessPiece.PieceType.ROOK));
        }
        for(int i = 2; i <= 7; i+=5){
            if(i == 2){
                color = ChessGame.TeamColor.WHITE;
            }
            else{
                color = ChessGame.TeamColor.BLACK;
            }
            for(int j = 1; j <= 8; j++){
                addPiece(new ChessPosition(i,j), new ChessPiece(color, ChessPiece.PieceType.PAWN));
            }
        }
        for(int i = 3; i <=6; i++){
            for(int j = i; j<=8; j++){
                addPiece(new ChessPosition(i,j), null);
            }
        }
    }

}
