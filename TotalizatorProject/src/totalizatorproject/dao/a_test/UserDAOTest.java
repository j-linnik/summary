package totalizatorproject.dao.a_test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import totalizatorproject.dao.user.UserDAO;
import totalizatorproject.entity.Bet;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class UserDAOTest implements UserDAO{
    
    private List<User> users = new ArrayList<User>();
    private List<Bet> bets = new ArrayList<Bet>();

    @Override
    public String addUser(User user)  throws TotalizatorDAOException{
        try {
            User us = (User)user.clone();
            users.add(us);
            System.out.println("add User;");
        } catch (Exception ex) {
            throw new TotalizatorDAOException(ex);
        }    
        return user.getMail();
    }

    @Override
    public User getUser(String mail)  throws TotalizatorDAOException{
        User found = null;
        User tmp = getTrueUser(mail);
        if(tmp != null){
            found = (User)tmp.clone();
        }
        System.out.println("get User;");
        return found;
    }
    
    public User getTrueUser (String mail) {
        User result = null;
        for(User us : users) {
            if(us.getMail().equals(mail)){
                result = us;
                break;
            }
        }
        return result;
    }

    @Override
    public void uppdateUser(String mail)  throws TotalizatorDAOException{
        User found = getUser(mail);
        found.setMail(mail);
        found.setBalance(getUser(mail).getBalance());
        found.setBets(getUser(mail).getBets());
        System.out.println("uppdate User");
    }

    @Override
    public void deleteUser(String mail)  throws TotalizatorDAOException{
        for(Iterator<User> it = users.iterator(); it.hasNext(); ){
            User user = it.next();
            if(user.getMail().equals(mail)){
                it.remove();
                break;
            }
        }
        System.out.println("delete User " + mail);
    }    

    @Override
    public List<User> getUsersList() throws TotalizatorDAOException {
        List<User> result = new ArrayList<User>();
        for(User us : users) {
            result.add((User)us.clone());
        }
        return result;
    }
} 