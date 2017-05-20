package totalizatorproject.dao.user;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class UserFileSerialDAO extends UserFileDAO {

    private static final String FILE_NAME = "users.bin";

    @Override
    protected void saveCollection() throws TotalizatorDAOException {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(users);
            } finally {
                fos.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    protected void loadCollection() throws TotalizatorDAOException {
        users = new ArrayList<User>();
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = (List<User>)ois.readObject();
            } catch (ClassNotFoundException ex) {
                throw new TotalizatorDAOException(ex);
            } finally {
                fis.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

}
