package totalizatorproject.logic;

import totalizatorproject.dao.horserace.HorseraceDAO;
import totalizatorproject.dao.horserace.HorseraceDAOFactory;
import totalizatorproject.entity.Horserace;
import totalizatorproject.exceptions.TotalizatorBusinessException;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class HorseraceManager {
    HorseraceDAO dao = HorseraceDAOFactory.getHorseraceDAO();
    
    public long addHorserace(Horserace race) throws TotalizatorBusinessException{
        try {
            return dao.addHorserace(race);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! HorseraceManager addHorserace not working!");
            throw new TotalizatorBusinessException(ex);
        }
    }
    public void uppdateHorserace(Horserace race){
        try {
            dao.uppdateHorserace(race);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! HorseraceManager uppdateHorserace not working!");
            ex.printStackTrace(System.out);
        }
    }
    public void deleteHorserace(long raceId){
        try {
            dao.deleteHorserace(raceId);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! HorseraceManager deleteHorserace not working!");
            ex.printStackTrace(System.out);
        }
    }
}
