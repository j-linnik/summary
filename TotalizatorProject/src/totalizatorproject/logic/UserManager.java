package totalizatorproject.logic;

import java.util.ArrayList;
import java.util.List;
import totalizatorproject.dao.user.UserDAO;
import totalizatorproject.dao.user.UserDAOFactory;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorBusinessException;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class UserManager {

    private UserDAO dao = UserDAOFactory.GetUserDAO();
    private List<User> users = new ArrayList<User>();

    public String addUser(User user) throws TotalizatorBusinessException {
        try {
            return dao.addUser(user);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! UserManager addUser not working!");
            throw new TotalizatorBusinessException(ex);
        }
    }

    public User getUser(String mail) {
        try {
            return dao.getUser(mail);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! UserManager getUser not working!");
        }
        return null;
    }

    public void uppdateUser(String mail) {
        try {
            dao.uppdateUser(mail);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! UserManager uppdateUser not working!");
            
            ex.printStackTrace(System.out);
        }
    }

    public void deleteUser(String mail) {
        try {
            dao.deleteUser(mail);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! UserManager deleteUser not working!");
        }
    }

    public List<User> getUsersList() {
        return users;
    }
}
