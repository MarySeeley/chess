package server;

import dataAccessTests.*;
import spark.Spark;
import userHandler.LogoutHandler;
import userHandler.RegisterHandler;

public class Server {

    public int run(int desiredPort){
        try {
          Spark.port(desiredPort);

          Spark.staticFiles.location("web");

          // Register your endpoints and handle exceptions here.
          Spark.init();
          // Instantiate DAOs

//        AuthDAO authDAO = new MemoryAuthDAO();
//        UserDAO userDAO = new MemoryUserDAO();
//        GameDAO gameDAO = new MemoryGameDAO();
          UserDAO userDAO;
          AuthDAO authDAO;
          GameDAO gameDAO;
          try {
            authDAO=new SQLAuthDAO();
            userDAO=new SQLUserDAO();
            gameDAO=new SQLGameDAO();
          } catch (Exception e) {
            System.out.println("SQL Exception");
            e.printStackTrace();
            throw new RuntimeException(e);
          }

          // Register
          Spark.post("/user", new RegisterHandler(userDAO, authDAO));
          Spark.delete("/db", new ClearHandler(userDAO, authDAO, gameDAO));
          Spark.post("/session", new LoginHandler(userDAO, authDAO));
          Spark.delete("/session", new LogoutHandler(userDAO, authDAO));
          Spark.get("/game", new ListGamesHandler(gameDAO, authDAO));
          Spark.post("/game", new CreateGameHandler(gameDAO, authDAO));
          Spark.put("/game", new JoinGameHandler(gameDAO, authDAO));
//        Spark.exception(Exception.class, new ExceptionHandler());
          Spark.awaitInitialization();
          return Spark.port();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
