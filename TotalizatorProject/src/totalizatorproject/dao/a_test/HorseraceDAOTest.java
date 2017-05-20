package totalizatorproject.dao.a_test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import totalizatorproject.dao.horserace.HorseraceDAO;
import totalizatorproject.entity.Horserace;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class HorseraceDAOTest implements HorseraceDAO{
    private List<Horserace> races = new ArrayList<Horserace>();

    @Override
    public long addHorserace(Horserace race)  throws TotalizatorDAOException{
        long raceId = generateHorseraceId();
        try{
            race.setRaceId(raceId);
            Horserace hr = (Horserace)race.clone();
            races.add(hr);
        } catch (Exception ex) {
            throw new TotalizatorDAOException(ex);
        }
        System.out.println("add Horserace");
        return raceId;
    }

    @Override
    public void uppdateHorserace(Horserace race)  throws TotalizatorDAOException{
        Horserace result = getHorserace(race.getRaceId());
        result.setDate(race.getDate());
        result.setHorses(race.getHorses());
        result.setWinner(race.getWinner());
        System.out.println("uppdate Horserace");
    }

    @Override
    public void deleteHorserace(long raceId)  throws TotalizatorDAOException{
        for(Iterator<Horserace> it = races.iterator(); it.hasNext(); ){
            Horserace hr = it.next();
            if(hr.getRaceId() == raceId) {
                it.remove();
                break;
            }
        }
        System.out.println("delete Horserace");
    }
    
    @Override
    public Horserace getHorserace (long raceId) throws TotalizatorDAOException{
        Horserace result = null;
        for(Horserace hr : races) {
            if(hr.getRaceId() == raceId) {
                result = (Horserace) hr.clone();
                break;
            }
        }
        return result;
    }
    
    @Override
    public List<Horserace> getRaces() throws TotalizatorDAOException{
        return races;
    }
    
    private long generateHorseraceId() {
        long raceId = Math.round(Math.random() * 10000);
        boolean found = true;
        while (found) {
            found = false;
            for (Horserace r : races) {
                if (r.getRaceId() == raceId) {
                    found = true;
                    break;
                }
            }
            raceId = Math.round(Math.random() * 10000);
        }
        return raceId;
    }
}
