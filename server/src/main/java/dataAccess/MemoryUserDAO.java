package dataAccess;

import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDAO implements UserDAO{
  final private Collection<UserData> users = new ArrayList<>();
  public UserData checkUser(UserData user) throws DataAccessException{
//    if (users.contains(user)){
//      throw new DataAccessException(403, "Error: User already registered");
//    }
    for(UserData i : users){
      String checkUser = i.username();
      if(checkUser.equals(user.username())){
        throw new DataAccessException(403, "Error: User already registered");
      }
    }
    return null;
  }
  public UserData getUser(String username) throws DataAccessException{
    for(UserData i : users){
      String checkUser = i.username();
      if(checkUser.equals(username)){
        return i;
      }
    }
    throw new DataAccessException(401, "Error: not an existing user");
  }

  public void createUser(UserData user)  throws DataAccessException{
    users.add(user);
  }

  public void clearUsers() throws DataAccessException{
    users.clear();
  }
  public Collection<UserData> getUsers(){
    return users;
  }
}
