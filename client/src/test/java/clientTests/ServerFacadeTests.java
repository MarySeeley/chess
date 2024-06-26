package clientTests;

import chess.ChessGame;
import exception.ResponseException;
import model.AuthData;
import model.CreateGameData;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ClientCommunicator;
import ui.ServerFacade;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static String url;

    @BeforeAll
    public static void init() throws IOException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        url = "http://localhost:" + port;
//        facade = new ServerFacade(url);
        facade.clientComm.delete(url + "/db", null,null);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clear() {
        try {
            facade.clientComm.delete(url + "/db", null, null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    @Test
    public void registerWorked() throws ResponseException, IOException {
        String user = "user";
        AuthData auth = facade.register(user, "password", "email");
        Assertions.assertEquals(user, auth.username());
        Assertions.assertInstanceOf(String.class, auth.authToken());
    }

    @Test
    public void registerFails() throws ResponseException, IOException {
        String user = "user";
        AuthData auth1 = facade.register(user, "password", "email");
        Assertions.assertThrows(ResponseException.class, ()->{facade.register(user, "password", "email");});
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
    public void loginFails() throws IOException, ResponseException {
        String user = "user";
        String password = "password";
        Assertions.assertThrows(ResponseException.class,()->{AuthData auth = facade.login(user, password);});

    }
    @Test
    public void createWorked() throws IOException, ResponseException {
        facade.register("user", "password", "email");
        CreateGameData game = facade.create("gameName");
        Assertions.assertInstanceOf(CreateGameData.class, game);

    }

    @Test
    public void createFails() throws IOException, ResponseException {
//        facade.create(null);
        facade.register("user", "password", "email");
        facade.logout();
        Assertions.assertThrows(NullPointerException.class, ()->{facade.create(null);});

    }

    @Test
    public void listWorked() throws IOException, ResponseException {
        AuthData auth = facade.register("user", "password", "email");
        System.out.println(auth);

        facade.create("gameName");
        facade.create("gameName");
        CreateGameData game = facade.create("gameName");
        System.out.println(game);
        Collection<GameData> games = facade.list();
        System.out.println(games);
        assertInstanceOf(Collection.class, games);
    }

    @Test
    public void listFails() throws IOException, ResponseException {
        facade.register("user", "password", "email");
        facade.logout();
        Assertions.assertThrows(NullPointerException.class, ()->{facade.list();});
    }

    @Test
    public void joinWorked() throws ResponseException, IOException {
        facade.register("user", "password", "email");
        CreateGameData gameID = facade.create("gameName");
        facade.join(gameID.gameID(), "black", ChessGame.TeamColor.BLACK);
    }

    @Test
    public void joinFailed() throws ResponseException, IOException {
        facade.register("user", "password", "email");
        CreateGameData gameID = facade.create("gameName");
        facade.join(gameID.gameID(), "black", ChessGame.TeamColor.BLACK);
        Assertions.assertThrows(IOException.class, ()->{facade.join(gameID.gameID(), "black", ChessGame.TeamColor.BLACK);});

    }

    @Test
    public void observeWorked() throws ResponseException, IOException {
        facade.register("user", "password", "email");
        CreateGameData gameID = facade.create("gameName");
        facade.observe(gameID.gameID());
    }
    @Test
    public void observeFails() throws IOException, ResponseException {
        facade.register("user", "password", "email");
        facade.logout();
        Assertions.assertThrows(NullPointerException.class, ()->{facade.observe(-1);});
    }
    @Test
    public void logoutWorked() throws ResponseException, IOException {
        facade.register("user", "password", "email");
        facade.logout();
        Assertions.assertNull(facade.auth);
    }

    @Test
    public void logoutFails() throws ResponseException, IOException {
        Assertions.assertThrows(IOException.class, ()->{facade.logout();});
    }

}
