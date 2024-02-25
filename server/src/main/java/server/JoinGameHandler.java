package server;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;

public class JoinGameHandler extends GameHandler{
  public JoinGameHandler(GameDAO gameDAO, AuthDAO authDAO) {
    super(gameDAO, authDAO);
  }
}
