package totalizatorproject.dao.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorDAOException;

public abstract class UserFileDAO implements UserDAO{
    protected List<User> users;

    @Override
    public String addUser(User user)  throws TotalizatorDAOException{
        if(users == null) {
            loadCollection();
        }
        try {
            User us = (User)user.clone();
            users.add(us);
            saveCollection();
        } catch (Exception ex) {
            throw new TotalizatorDAOException(ex);
        }    
        System.out.println("add User");
        return user.getMail();
    }

    @Override
    public User getUser(String mail)  throws TotalizatorDAOException{
        if(users == null) {
            loadCollection();
        }        
        User found = null;
        User tmp = getTrueUser(mail);
        if(tmp != null){
            found = (User)tmp.clone();
        }
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
        saveCollection();
        System.out.println("uppdate User");
    }

    @Override
    public void deleteUser(String mail)  throws TotalizatorDAOException{
        if(users == null) {
            loadCollection();
        }
        for(Iterator<User> it = users.iterator(); it.hasNext(); ){
            User user = it.next();
            if(user.getMail().equals(mail)){
                it.remove();
                break;
            }
        }
        saveCollection();
        System.out.println("delete User");
    }    

    @Override
    public List<User> getUsersList() throws TotalizatorDAOException {
        if(users == null) {
            loadCollection();
        }
        List<User> result = new ArrayList<User>();
        for(User us : users) {
            result.add((User)us.clone());
        }
        return result;
    }
    
    abstract protected void saveCollection() throws TotalizatorDAOException;
    abstract protected void loadCollection() throws TotalizatorDAOException;
}
