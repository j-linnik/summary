package totalizatorproject.dao.bet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import totalizatorproject.entity.Bet;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class BetFileSerialDAO extends BetFileDAO{

    private static final String FILE_NAME = "bets.bin";
    
    @Override
    protected void saveCollection() throws TotalizatorDAOException{
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(bets);
            } finally {
                fos.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    protected void loadCollection() throws TotalizatorDAOException{
         bets = new ArrayList<Bet>();
        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                bets = (List<Bet>)ois.readObject();
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
