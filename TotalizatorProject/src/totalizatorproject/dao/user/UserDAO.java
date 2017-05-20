package totalizatorproject.dao.user;

import java.util.List;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorDAOException;

public interface UserDAO {
    public String addUser(User user) throws TotalizatorDAOException;
    public User getUser(String mail) throws TotalizatorDAOException;
    public void uppdateUser(String mail) throws TotalizatorDAOException;
    public void deleteUser(String mail) throws TotalizatorDAOException;
    public List<User> getUsersList() throws TotalizatorDAOException;
}
