package totalizatorproject.dao.horserace;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import totalizatorproject.entity.Horserace;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class HorseraceFileSerialDAO extends HorseraceFileDAO{

    private static final String FILE_NAME = "races.bin";
    
    @Override
    protected void saveCollection() throws TotalizatorDAOException {
    try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(races);
            } finally {
                fos.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    protected void loadCollection() throws TotalizatorDAOException {
        races = new ArrayList<Horserace>();
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                races = (List<Horserace>)ois.readObject();
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