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

  public void deleteAuth(String authToken) throws DataAccessException{
    for(AuthData i : auth){
      if(i.authToken().equals(authToken)){
        auth.remove(i);
        return;
      }
    }
    throw new DataAccessException(401, "Error: no matching auth token");
  }

  public void checkAuth(String authToken) throws DataAccessException{
    for(AuthData i : auth){
      if(i.authToken().equals(authToken)){
        return;
      }
    }
    throw new DataAccessException(401, "Error: not authorized");
  }
}
