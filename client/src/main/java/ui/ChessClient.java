package ui;

import exception.ResponseException;
import model.AuthData;
import model.GameData;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class ChessClient {
  Scanner scanner = new Scanner(System.in);
  private ServerFacade server;

  Boolean loggedIn = false;
  public ChessClient(String serverURL){
    server = new ServerFacade(serverURL);

  }
  public void run(){
    System.out.println("Welcome to Chess. Enter the number correlated to the action you want to take.");
    String result =(commandUI());
    while(result != null && !result.equals("quit")){
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
      return """
              1 Register - Create an account with a username, password, and email
              2 Login - Log in using an existing account with a username and password
              3 Quit - Finish playing chess
              4 Help - Explains commands
              """;
    }
    else{
      return """
              1 Create - Start a new game
              2 List - List all the games
              3 Join - Join a chess game with the game ID into an white or black player
              4 Observe - Watch a specified game using a game ID
              5 Logout - Logout when you are done
              6 Quit - Finish playing chess
              7 Help - Explains commands
              """;
    }
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
  public String login() throws IOException {
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
    GameData game = server.create(params[0]);
    int gameID = game.gameID();
//    int gameID = server.create(params[0]);
    System.out.println("Created the game "+game.gameName()+" with gameID: "+ gameID);
    return "created";
  }
  public String list() throws IOException {
    Collection<GameData> games = server.list();
    System.out.println("Here is your list of games:");
    System.out.println(games);
    return "listed";
  }
  public String join(){
    System.out.println("To join a game type the ID and what player color you want to be: <gameID> [WHITE|BLACK|<empty>]");
    printPrompt();
    String[] params = getInput();
    int gameID = Integer.parseInt(params[0]);
    server.join(gameID, params[1]);
    return null;
  }
  public String observe(){
    System.out.println("To observe a game type the ID: <gameID>");
    printPrompt();
    String[] params = getInput();
    int gameID = Integer.parseInt(params[0]);
    server.observe(gameID);
    return null;
  }
  public String logout(){
    System.out.println("Thanks for playing");
    server.logout();
    return null;
  }
  public void printPrompt(){
    System.out.print("\n" + ">>>");
  }

}
