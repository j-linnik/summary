package totalizatorproject.dao.user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class UserFileCommaDAO extends UserFileDAO{

    private static final String FILE_NAME = "users.txt";
    
    @Override
    protected void saveCollection() throws TotalizatorDAOException {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            try {
                BufferedWriter bw = new BufferedWriter(fw);
                for(User us : users) {
                    bw.write(us.getMail() + "," + us.getBalance()+
                             System.lineSeparator());
                }
                bw.close();
            } finally {
                fw.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    protected void loadCollection() throws TotalizatorDAOException {
        users = new ArrayList<>();
        try{
            FileReader fr = new FileReader(FILE_NAME);
            try {
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while((line = br.readLine()) != null) {
                    String [] params = line.split("\\s*,\\s*");
                    User us = new User();
                    us.setMail(params[0]);
                    us.setBalance(Integer.parseInt(params[1]));
                    
                    users.add(us);
                }
            } finally {
                fr.close();
            }
        } catch(IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }
}
