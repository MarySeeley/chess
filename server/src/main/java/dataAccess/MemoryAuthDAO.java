package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
  final private Collection<AuthData> auth = new ArrayList<>();
  public AuthData createAuth(String username) throws DataAccessException{
    String token =UUID.randomUUID().toString();
    AuthData newAuth = new AuthData(token, username);
    auth.add(newAuth);
    return newAuth;
  }
  public void clearAuth() throws DataAccessException{
    auth.clear();
  }
}
