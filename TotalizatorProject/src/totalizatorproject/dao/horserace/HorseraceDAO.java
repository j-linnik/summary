package totalizatorproject.dao.horserace;

import java.util.List;
import totalizatorproject.entity.Horserace;
import totalizatorproject.exceptions.TotalizatorDAOException;

public interface HorseraceDAO {
    public long addHorserace(Horserace race) throws TotalizatorDAOException;
    public void uppdateHorserace(Horserace race) throws TotalizatorDAOException;
    public void deleteHorserace(long raceId) throws TotalizatorDAOException;
    public Horserace getHorserace(long raceId) throws TotalizatorDAOException;
    public List<Horserace> getRaces () throws TotalizatorDAOException;
}
