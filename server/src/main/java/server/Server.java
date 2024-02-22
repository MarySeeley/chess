package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.AuthData;
import model.UserData;
import spark.Spark;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.init();
        // Instantiate DAOs
        AuthDAO authDAO = new MemoryAuthDAO();
        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();

        // Register
        Spark.post("/user", new RegisterHandler(userDAO, authDAO));
        Spark.delete("/db", new ClearHandler(userDAO, authDAO, gameDAO));
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }


}
