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
    private static final String EMPTY = "   ";

    private static Random rand = new Random();

    private static ChessBoard board;
    public static void main(ChessGame game, Boolean isWhite, Boolean highlight, String position) {
      var out=new PrintStream(System.out, true, StandardCharsets.UTF_8);

      board=game.squares;
      out.print(ERASE_SCREEN);

      if(highlight && !isWhite){
        String[][] squares1=makeBoard(false);
        highlightBoard(squares1, false, position);
      }
      else if(!isWhite) {
        System.out.print("\u001B[0m");
        System.out.println(header(false));
        String[][] squares1=makeBoard(false);
        printBoard(squares1, false);
        System.out.println(header(false));
      }
      else {
        System.out.print("\u001B[0m");
        System.out.println(header(true));
        String[][] squares=makeBoard(true);
        printBoard(squares, true);
        System.out.println(header(true));
      }
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
          squares[row][0] = sidesTrue[row];

        for(int col = 1; col<9; col++){

          ChessPiece piece=board.getPiece(new ChessPosition(row+1, col));
          String pieceString = playerString(piece);
          squares[row][col] = pieceString;
        }
          squares[row][9] = sidesTrue[row];

      }
      return squares;
    }
    private static void printBoard(String[][] squares, Boolean reverse){
      Boolean startWithBlack = true;
      int startRow = 0;
      int endRow = 8;
      int startCol = 0;
      int endCol = 10;
      int increment = 1;


      if(reverse){
        startRow = 7;
        endRow = -1;
        startCol = 9;
        endCol = -1;
        increment = -1;
      }
      for(int row = startRow; row !=endRow; row+=increment){
        for(int col = startCol; col!=endCol; col+=increment){
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
    private static int intCol(char charCol){
      int posCol = 10;
      switch(charCol){
        case 'a':
          posCol = 8;
        case 'b':
          posCol = 7;
        case 'c':
          posCol = 6;
        case 'd':
          posCol = 5;
        case 'e':
          posCol = 4;
        case 'f':
          posCol = 3;
        case 'g':
          posCol = 2;
        case 'h':
          posCol = 1;
      };
      return posCol;
    }

    private static int reverseIntCol(char charCol){
      int posCol = 10;
      switch(charCol){
        case 'a':
          posCol = 1;
        case 'b':
          posCol = 2;
        case 'c':
          posCol = 3;
        case 'd':
          posCol = 4;
        case 'e':
          posCol = 5;
        case 'f':
          posCol = 6;
        case 'g':
          posCol = 7;
        case 'h':
          posCol = 8;
      };
      return posCol;
    }
    private static void highlightBoard(String[][] squares, Boolean reverse, String position){
      // might need to use a switch to reverse it
      int posRow = position.charAt(0);
      char charCol = position.charAt(1);
      int posCol=0;

      Boolean startWithBlack = true;
      int startRow = 0;
      int endRow = 8;
      int startCol = 0;
      int endCol = 10;
      int increment = 1;

      posCol = intCol(charCol);



      if(reverse){
        posCol = reverseIntCol(charCol);
        startRow = 7;
        endRow = -1;
        startCol = 9;
        endCol = -1;
        increment = -1;

      }
      for(int row = startRow; row !=endRow; row+=increment){
        for(int col = startCol; col!=endCol; col+=increment){
          String piece = squares[row][col];
          boolean highlight = false;
          boolean highlightMoves = false;
          if(row==posRow-1 && col == posCol){
            highlight = true;
          }
          boolean isBlack = (startWithBlack && (row + col) % 2 == 0) || (!startWithBlack && (row + col) % 2 != 0);
          if(col>=1 && col<=8){
            String background;
            String textColor;
            if(highlight){
              background = SET_BG_COLOR_YELLOW;
            }
            else if(isBlack){
              if(highlightMoves){
                background =SET_BG_COLOR_DARK_GREEN;
              }
              else {
                background=SET_BG_COLOR_WHITE;
              }
            }
            else{
              if(highlightMoves){
                background = SET_BG_COLOR_GREEN;
              }
              else {
                background=SET_BG_COLOR_BLACK;
              }
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
  }


