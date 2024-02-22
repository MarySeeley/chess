package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
  final private Collection<UserData> users = new ArrayList<>();
  public UserData getUser(UserData user) throws DataAccessException{
    if (users.contains(user)){
      throw new DataAccessException(403, "Error: User already registered");
    }
    return null;
  }

  public void createUser(UserData user)  throws DataAccessException{
    users.add(user);
  }

  public void clearUsers() throws DataAccessException{
    users.clear();
  }
}
