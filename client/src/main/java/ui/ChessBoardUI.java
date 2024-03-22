package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Character.isJavaIdentifierPart;
import static java.lang.Character.isUpperCase;
import static ui.EscapeSequences.*;

  public class ChessBoardUI {
    public static final String WHITE_KING = " K ";
    public static final String WHITE_QUEEN = " Q ";
    public static final String WHITE_BISHOP = " B ";
    public static final String WHITE_KNIGHT = " N ";
    public static final String WHITE_ROOK = " R ";
    public static final String WHITE_PAWN = " P ";
    public static final String BLACK_KING = " k ";
    public static final String BLACK_QUEEN = " q ";
    public static final String BLACK_BISHOP = " b ";
    public static final String BLACK_KNIGHT = " n ";
    public static final String BLACK_ROOK = " r ";
    public static final String BLACK_PAWN = " p ";
//    public static final String EMPTY = "\u2003 ";
    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 0;
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";
    private static Random rand = new Random();

    private static ChessBoard board;
    public static void main(String[] args) {
      var out=new PrintStream(System.out, true, StandardCharsets.UTF_8);
      board=new ChessBoard();
      board.resetBoard();
      out.print(ERASE_SCREEN);
      System.out.print("\u001B[0m");
      System.out.println(header(true));
      String[][] squares=makeBoard(true);
      printBoard(squares);
      System.out.println(header(true));

      System.out.print("\u001B[0m");
      System.out.println(header(false));
      String[][] squares1=makeBoard(false);
      printBoard(squares1);
      System.out.println(header(false));
//      drawHeaders(out);
//
//      drawTicTacToeBoard(out);
//      drawHeaders(out);

      out.print(SET_BG_COLOR_BLACK);
      out.print(SET_TEXT_COLOR_WHITE);
    }
    private static String header(Boolean reversal){
      String header = "";
      String[] headersTrue = { EMPTY," a "," b "," c "," d "," e "," f "," g "," h ",EMPTY };
      String[] headersFalse = { EMPTY," h "," g "," f "," e "," d "," c "," b "," a ",EMPTY };
      if(reversal) {
        for (int i=0; i < 10; i++) {
          header+=headersTrue[i];
        }
      }
      else{
        for (int i=0; i < 10; i++) {
          header+=headersFalse[i];
        }
      }
      return header;
    }

    private static String[][] makeBoard(Boolean reversal){
      String[][] squares = new String[8][10];
      String[] sidesTrue = {" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "};
      String[] sidesFalse = {" 8 ", " 7 ", " 6 ", " 5 ", " 4 ", " 3 ", " 2 ", " 1 "};
      for(int row = 0; row<8; row++){
        if(reversal){
          squares[row][0] = sidesTrue[row];
        }
        else{
          squares[row][0] = sidesFalse[row];
        }
        for(int col = 1; col<9; col++){

          ChessPiece piece=board.getPiece(new ChessPosition(row+1, col));
          String pieceString = playerString(piece);
          squares[row][col] = pieceString;
        }
        if(reversal){
          squares[row][9] = sidesTrue[row];
        }
        else {
          squares[row][9]=sidesFalse[row];
        }
      }
      return squares;
    }
    private static void printBoard(String[][] squares){
      Boolean startWithBlack = false;
      for(int row = 0; row <8; row++){
        for(int col = 0; col<10; col++){
          String piece = squares[row][col];
          boolean isBlack = (startWithBlack && (row + col) % 2 == 0) || (!startWithBlack && (row + col) % 2 != 0);
          if(col>=1 && col<=8){
            String background;
            String textColor;
            if(isBlack){
              background = SET_BG_COLOR_WHITE;
            }
            else{
              background = SET_BG_COLOR_BLACK;
            }
            if(isUpperCase(piece.charAt(1))){
              textColor = SET_TEXT_COLOR_BLUE;
            }
            else{
              textColor = SET_TEXT_COLOR_RED;
            }
            String print = background + textColor + piece + "\u001B[0m";
            System.out.print(print);
          }
          else{
            System.out.print(piece);
          }
        }
        System.out.println();
      }
    }
    private static String playerString(ChessPiece piece) {
      if (piece == null) {
        return EMPTY;
      } else if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
        if (piece.getPieceType().equals(ChessPiece.PieceType.PAWN)) {
          return WHITE_PAWN;
        } else if (piece.getPieceType().equals(ChessPiece.PieceType.ROOK)) {
          return WHITE_ROOK;
        } else if (piece.getPieceType().equals(ChessPiece.PieceType.KNIGHT)) {
          return WHITE_KNIGHT;
        } else if (piece.getPieceType().equals(ChessPiece.PieceType.BISHOP)) {
          return WHITE_BISHOP;
        } else if (piece.getPieceType().equals(ChessPiece.PieceType.KING)) {
          return WHITE_KING;

        } else if (piece.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
          return WHITE_QUEEN;


        }
      } else {
        if (piece.getPieceType().equals(ChessPiece.PieceType.PAWN)) {
          return BLACK_PAWN;

        } else if (piece.getPieceType().equals(ChessPiece.PieceType.ROOK)) {
          return BLACK_ROOK;

        } else if (piece.getPieceType().equals(ChessPiece.PieceType.KNIGHT)) {
          return BLACK_KNIGHT;

        } else if (piece.getPieceType().equals(ChessPiece.PieceType.BISHOP)) {
          return BLACK_BISHOP;

        } else if (piece.getPieceType().equals(ChessPiece.PieceType.KING)) {
          return BLACK_KING;
        } else if (piece.getPieceType().equals(ChessPiece.PieceType.QUEEN)) {
          return BLACK_QUEEN;

        }
      }
      return EMPTY;
    }

    private static void drawHeaders(PrintStream out) {

      setBlack(out);

      String[] headers = { EMPTY," a "," b "," c "," d "," e "," f "," g "," h ",EMPTY };
      for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
        drawHeader(out, headers[boardCol]);

        if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
          out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
        }
      }

      out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
      int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
      int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

      out.print(EMPTY.repeat(prefixLength));
      printHeaderText(out, headerText);
      out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
      out.print(SET_BG_COLOR_BLACK);
      out.print(SET_TEXT_COLOR_GREEN);

      out.print(player);

      setBlack(out);
    }
    private static void drawChessBoard(PrintStream out){

    }

    private static void drawTicTacToeBoard(PrintStream out) {

      for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
        for(int boardCol = 0; boardCol <BOARD_SIZE_IN_SQUARES; ++boardCol){
          if(boardCol >=1 &&boardCol<=8 && boardRow>=1 && boardRow<=8) {
            ChessPiece piece=board.getPiece(new ChessPosition(boardRow, boardCol));
            printPlayer(out, piece);
          }
        }
        out.println();

//        drawRowOfSquares(out);

//        if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//          drawVerticalLine(out);
////          setBlack(out);
//        }
      }
    }

    private static void drawRowOfSquares(PrintStream out) {

      for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
          setWhite(out);

          if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
            int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
            int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

            out.print(EMPTY.repeat(prefixLength));
            if(boardCol >=1 &&boardCol<=8 && squareRow>=1 && squareRow<=8) {
              ChessPiece piece=board.getPiece(new ChessPosition(squareRow, boardCol));
              printPlayer(out, piece);
            }
//            printPlayer(out, rand.nextBoolean() ? X : O);
            out.print(EMPTY.repeat(suffixLength));
          }
          else {
            out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
          }

          if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
            // Draw right line
            setRed(out);
            out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
          }

          setBlack(out);
        }

        out.println();
      }
    }
    private static void printPlayer(PrintStream out, ChessPiece piece) {
      out.print(SET_BG_COLOR_WHITE);
      if(piece == null){
        out.print(EMPTY);
      }
      else if(piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)){
        if(piece.getPieceType().equals(ChessPiece.PieceType.PAWN)){
          out.print(SET_TEXT_COLOR_RED);
          out.print(WHITE_PAWN);
        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.ROOK)){
          out.print(SET_TEXT_COLOR_RED);
          out.print(WHITE_ROOK);
        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.KNIGHT)){
          out.print(SET_TEXT_COLOR_RED);
          out.print(WHITE_KNIGHT);
        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.BISHOP)){
          out.print(SET_TEXT_COLOR_RED);
          out.print(WHITE_BISHOP);
        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.KING)){
          out.print(SET_TEXT_COLOR_RED);
          out.print(WHITE_KING);

        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.QUEEN)){
          out.print(SET_TEXT_COLOR_RED);
          out.print(WHITE_QUEEN);


        }
      }
      else{
        if(piece.getPieceType().equals(ChessPiece.PieceType.PAWN)){
          out.print(SET_TEXT_COLOR_BLUE);
          out.print(BLACK_PAWN);

        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.ROOK)){
          out.print(SET_TEXT_COLOR_BLUE);
          out.print(BLACK_ROOK);

        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.KNIGHT)){
          out.print(SET_TEXT_COLOR_BLUE);
          out.print(BLACK_KNIGHT);

        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.BISHOP)){
          out.print(SET_TEXT_COLOR_BLUE);
          out.print(BLACK_BISHOP);

        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.KING)){
          out.print(SET_TEXT_COLOR_BLUE);
          out.print(BLACK_KING);
        }
        else if(piece.getPieceType().equals(ChessPiece.PieceType.QUEEN)){
          out.print(SET_TEXT_COLOR_BLUE);
          out.print(BLACK_QUEEN);

        }
      }
//      out.print(SET_BG_COLOR_WHITE);
//      out.print(SET_TEXT_COLOR_BLUE);

//      out.print(piece);

      setWhite(out);
    }

    private static void drawVerticalLine(PrintStream out) {

      int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
              (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;

      for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
        setRed(out);
        out.print(EMPTY.repeat(boardSizeInSpaces));

        setBlack(out);
        out.println();
      }
    }

    private static void setWhite(PrintStream out) {
      out.print(SET_BG_COLOR_WHITE);
      out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
      out.print(SET_BG_COLOR_RED);
      out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
      out.print(SET_BG_COLOR_BLACK);
      out.print(SET_TEXT_COLOR_BLACK);
    }


  }


