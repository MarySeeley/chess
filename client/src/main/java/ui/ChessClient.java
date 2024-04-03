package ui;

import chess.ChessGame;
import exception.ResponseException;
import model.AuthData;
import model.CreateGameData;
import model.GameData;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ChessClient {
  Scanner scanner = new Scanner(System.in);
  private ServerFacade server;

  Boolean loggedIn = false;
  Boolean gamePlay = false;
  Boolean isWhite = null;
  GameData currentGame;
  public ChessClient(String serverURL){
    server = new ServerFacade(serverURL);

  }
  public void run(){
    System.out.println("Welcome to Chess. Enter the number correlated to the action you want to take.");
    String result =(commandUI());
    while(result != null && !result.equals("quit")){
      System.out.println("\n");
      System.out.println(commandUI());
      printPrompt();
      String line = scanner.nextLine();
      try{
        result = eval(line);

      }catch(Throwable e){
        System.out.println(e.toString());
      }
    }
    System.out.println("Thank you for playing!");
  }

  private String commandUI() {
    if(!loggedIn){
      return """
              1 Register
              2 Login
              3 Quit
              4 Help
              """;
    }
    else if(gamePlay){
      System.out.println("""
              1 Redraw Chess Board 
              2 Leave
              3 Make Move
              4 Resign
              5 Highlight Legal Moves
              """);
    }
    else{
      return """
              1 Create
              2 List
              3 Join
              4 Observe
              5 Logout
              6 Quit
              7 Help
              """;
    }
    return null;
  }

  public String eval(String input){
    try {
      int tokens=Integer.parseInt(input);
      if(!loggedIn) {
        return switch (tokens) {
          case 1 -> register();
          case 2 -> login();
          case 3 -> "quit";
          default -> help();
        };
      }
      else if(gamePlay){
        return switch(tokens){
          case 1 -> redraw();
          case 2 -> leave();
          case 3 -> move();
          case 4 -> resign();
          case 5 -> highlight();
          default -> help();
        };
      }
      else{
         return switch(tokens){
          case 1 -> create();
          case 2 -> list();
          case 3 -> join();
          case 4 -> observe();
          case 5 -> logout();
          case 6 -> "quit";
          default -> help();
        };
      }
    }catch(NumberFormatException e){
      System.out.println("It seems you didn't type a number. Please type the number that correlates with the action you wish to take.");
      System.out.println(help());
      return "try again";
    } catch (ResponseException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String help(){
    if(!loggedIn){
      System.out.println( """
              1 Register - Create an account with a username, password, and email
              2 Login - Log in using an existing account with a username and password
              3 Quit - Finish playing chess
              4 Help - Explains commands
              """);
    }
    else if(gamePlay){
      System.out.println("""
              1 Redraw Chess Board - Redraws the chess board
              2 Leave - Leave the game and return to the main menu (can rejoin later)
              3 Make Move - Choose a piece and move them to a new spot
              4 Resign - Forfeit the game, other player will win. (You will still need to leave the game)
              5 Highlight Legal Moves - Choose a piece to view legal moves
              """);
    }
    else{
      System.out.println( """
              1 Create - Start a new game
              2 List - List all the games
              3 Join - Join a chess game with the game ID into an white or black player
              4 Observe - Watch a specified game using a game ID
              5 Logout - Logout when you are done
              6 Quit - Finish playing chess
              7 Help - Explains commands
              """);
    }
    return "help";
  }
  public String[] getInput(){
    String line = scanner.nextLine();
    var tokens = line.toLowerCase().split(" ");
    return Arrays.copyOfRange(tokens, 0, tokens.length);
  }
  public String register() throws ResponseException {
    System.out.println("To register please type: <username> <password> <email>");
    printPrompt();
    String[] params = getInput();
    try {
      AuthData auth = server.register(params[0], params[1], params[2]);
      loggedIn = true;
      System.out.println("You have successfully registered!"+"\n");
    }catch(ResponseException e){
      System.out.println(e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "registered";
  }
  public String login() throws IOException, ResponseException {
    System.out.println("To login type: <username> <password>");
    printPrompt();
    String[] params = getInput();
    AuthData auth = server.login(params[0], params[1]);
    System.out.println("You have successfully logged in!"+"\n");
    loggedIn = true;
    return "logged in";
  }
  public String create() throws IOException {
    System.out.println("To create a game enter the games name: <gameName>");
    printPrompt();
    String[] params = getInput();
    CreateGameData game = server.create(params[0]);
    int gameID = game.gameID();
    game.game().squares.resetBoard();
    System.out.println("Created a game with gameID: "+ gameID);
    return "created";
  }
  public String list() throws IOException {
    List<GameData> games = server.list().stream().toList();
    System.out.println("Here is your list of games:");
    for(int i = 1; i <= games.toArray().length; i++){
      GameData game = games.get(i-1);
      System.out.println(i +" Name:"+game.gameName()+"  White Player:"+game.whiteUsername()+"  Black Player:"+ game.blackUsername());
    }
    return "listed";
  }
  public String join() throws IOException {
    System.out.println("To join a game type the ID and what player color you want to be: <gameID> [WHITE|BLACK|<empty>]");
    printPrompt();
    String[] params = getInput();
    int gameNum = Integer.parseInt(params[0]);
    String playerColor = (String)params[1];
    List<GameData> games = server.list().stream().toList();
    GameData game = games.get(gameNum-1);
    int gameID = game.gameID();
    server.join(gameID, params[1]);
    System.out.println("You have joined the game as the " + playerColor+" player!");
    currentGame = game;
    gamePlay = true;
    if(playerColor.equals("WHITE")){
      isWhite = true;
    }
    else if(playerColor.equals("BLACK")){
      isWhite = false;
    }
    else{
      isWhite = true;
    }
    ChessBoardUI.main(game.game(), isWhite);
    return "joined";
  }
  public String observe() throws IOException {
    System.out.println("To observe a game type the ID: <gameID>");
    printPrompt();
    String[] params = getInput();
    int gameNum = Integer.parseInt(params[0]);
    List<GameData> games = server.list().stream().toList();
    GameData game = games.get(gameNum-1);
    int gameID = game.gameID();
    server.observe(gameID);
    System.out.println("You have joined the game as an observer!");
    gamePlay = true;
    isWhite = true;
    currentGame = game;
    ChessBoardUI.main(game.game(), true);
    return "observed";
  }
  public String logout() throws IOException {
    System.out.println("Your logged out! Thanks for playing");
    server.logout();
    loggedIn = false;
    return "logged out";
  }
  public void printPrompt(){
    System.out.print("\n" + ">>>");
  }
  public String redraw(){
    ChessBoardUI.main(currentGame.game(), isWhite);
    return "redraw";
  }
  public String leave(){
    gamePlay = false;
    isWhite = null;
    currentGame = null;
    return "leave";
  }
  public String move(){
    return "move";
  }
  public String resign(){
    return "resign";
  }
  public String highlight(){
    System.out.println("To choose a piece to look at its moves select its position: <a8>");
    printPrompt();
    String[] params = getInput();
    int gameNum = Integer.parseInt(params[0]);
    return "highlight";
  }
}
