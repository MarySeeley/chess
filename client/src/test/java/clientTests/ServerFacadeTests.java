package clientTests;

import exception.ResponseException;
import model.AuthData;
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
    public void loginWorked() throws IOException {
        AuthData auth = facade.login("user", "password");

    }

}
