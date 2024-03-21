package clientTests;

import exception.ResponseException;
import model.AuthData;
import model.CreateGameData;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ClientCommunicator;
import ui.ServerFacade;

import java.io.IOException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() throws IOException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String url = "http://localhost:" + port;
        facade = new ServerFacade(url);
        facade.clientComm.delete(url + "/db", null,null);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerWorked() throws ResponseException, IOException {
        String user = "user";
        AuthData auth = facade.register(user, "password", "email");
        Assertions.assertEquals(user, auth.username());
    }

    @Test
    public void loginWorked() throws IOException, ResponseException {
        String user = "user";
        String password = "password";
        facade.register(user, password, "email");
        AuthData auth = facade.login(user, password);
        System.out.println(auth);
        Assertions.assertEquals(user, auth.username());
    }

    @Test
    public void createWorked() throws IOException, ResponseException {
        facade.register("user", "password", "email");
        CreateGameData game = facade.create("gameName");
        Assertions.assertInstanceOf(CreateGameData.class, game);

    }
    @Test
    public void joinWorked() throws ResponseException, IOException {
        facade.register("user", "password", "email");
        CreateGameData gameID = facade.create("gameName");
        facade.join(gameID.gameID(), "black");
    }

    @Test
    public void observeWorked() throws ResponseException, IOException {
        facade.register("user", "password", "email");
        CreateGameData gameID = facade.create("gameName");
        facade.observe(gameID.gameID());
    }
    @Test
    public void logoutWorked() throws ResponseException, IOException {
        facade.register("user", "password", "email");
        facade.logout()
    }

}
