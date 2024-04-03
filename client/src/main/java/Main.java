import chess.*;
import com.google.gson.Gson;
import ui.ChessClient;
import ui.WebSocketClient;
import webSocketMessages.userCommands.Leave;

import java.util.Scanner;
//import Server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
//        var port = 8080;
//        var server = new Server();
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        var ws = new WebSocketClient();
        ;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message you want to echo");
        Leave msg = new Leave(1, "auth");
        String jsonmsg = new Gson().toJson(msg, Leave.class);
        ws.send(jsonmsg);
        Thread.sleep(10000);
//        new ChessClient(serverUrl).run();
    }
}