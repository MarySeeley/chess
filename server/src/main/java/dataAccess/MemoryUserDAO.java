package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
  final private Collection<UserData> users = new ArrayList<>();
  public UserData getUser(UserData user) throws DataAccessException{
    if (!users.contains(user)){
      return null;
    }
    return user;
  }

  public void createUser(UserData user)  throws DataAccessException{
    users.add(user);
  }

  public void clearUsers() throws DataAccessException{
    users.clear();
  }
}
