import chess.*;
import server.Server;

public class Main {
    public static void main(String[] args) {
        try{
            var port = 8080;
            var server = new Server();

            if (args.length >= 1) {
                port = Integer.parseInt(args[0]);
            }
            port = server.run(port);
            System.out.printf("Server started on port %d%n", port);
        }
        catch(Throwable e){
            System.out.printf("Unable to start server: %d%n", e.getMessage());
        }
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
    }
}