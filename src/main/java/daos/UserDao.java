package daos;

import model.User;

public interface UserDao {
    User getUser(String uname, String pass);
}
