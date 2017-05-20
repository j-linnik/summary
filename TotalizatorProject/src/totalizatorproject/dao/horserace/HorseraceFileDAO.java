package totalizatorproject.dao.horserace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import totalizatorproject.entity.Horserace;
import totalizatorproject.exceptions.TotalizatorDAOException;

public abstract class HorseraceFileDAO implements HorseraceDAO{
    List<Horserace> races = new ArrayList<Horserace>();

    @Override
    public long addHorserace(Horserace race)  throws TotalizatorDAOException{
        long raceId = generateHorseraceId();
        try{
            race.setRaceId(raceId);
            Horserace hr = (Horserace)race.clone();
            races.add(race);
            saveCollection();
        } catch (Exception ex) {
            throw new TotalizatorDAOException(ex);
        }
        System.out.println("add Horserace");
        return raceId;
    }

    @Override
    public void uppdateHorserace(Horserace race)  throws TotalizatorDAOException{
        if(races == null) {
            loadCollection();
        }
        Horserace result = getHorserace(race.getRaceId());
        result.setDate(race.getDate());
        result.setHorses(race.getHorses());
        result.setWinner(race.getWinner());
        System.out.println("uppdate Horserace");
        saveCollection();
    }

    @Override
    public void deleteHorserace(long raceId)  throws TotalizatorDAOException{
        if(races == null) {
            loadCollection();
        }
        for(Iterator<Horserace> it = races.iterator(); it.hasNext(); ){
            Horserace hr = it.next();
            if(hr.getRaceId() == raceId) {
                it.remove();
                break;
            }
        }
        System.out.println("delete Horserace");
        saveCollection();
    }
    
    @Override
    public Horserace getHorserace (long raceId) throws TotalizatorDAOException{
        if(races == null) {
            loadCollection();
        }
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
        if(races == null) {
            loadCollection();
        }
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
    
    protected abstract void saveCollection() throws TotalizatorDAOException ;
    protected abstract void loadCollection() throws TotalizatorDAOException ;
    
}
