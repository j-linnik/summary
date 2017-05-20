package totalizatorproject.dao.bet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import totalizatorproject.entity.Bet;
import totalizatorproject.exceptions.TotalizatorDAOException;

public abstract class BetFileDAO implements BetDAO{
    
    List<Bet> bets = new ArrayList<Bet>();
    
    @Override
    public long addBet(Bet bet) throws TotalizatorDAOException {
            long betId = generateBetId();
        try{
            bet.setBetId(betId);
            Bet b = (Bet)bet.clone();
            bets.add(b);
            saveCollection();
        } catch (Exception ex) {
            throw new TotalizatorDAOException(ex);
        }
        System.out.println("add Bet");
        return betId;
    }

    @Override
    public void uppdateBet(Bet bet) throws TotalizatorDAOException {
        if(bets == null) {
            loadCollection();
        }
        Bet result = new Bet();
        result.setBetId(bet.getBetId());
        result.setRace(bet.getRace());
        result.setHorse(bet.getHorse());
        result.setSumm(bet.getSumm());
        saveCollection();
        System.out.println("uppdate Bet");

    }

    @Override
    public void sendBetResult(long betId) throws TotalizatorDAOException {
        if(bets == null) {
            loadCollection();
        }
        Bet b = getBet(betId);
        if ((b.getRace().getDate().compareTo(new Date())) > 0 ){
            System.out.println("Race is over!");
            System.out.println("Sendind result to " + b.getUser().getMail());
        } else {
            System.out.println("The race is not over yet. Bet is valid");
        }
    }

    @Override
    public void deleteBet(long betId) throws TotalizatorDAOException {
        if(bets == null) {
            loadCollection();
        }
        for (Iterator<Bet> it = bets.iterator(); it.hasNext();) {
            Bet found = it.next();
            if (found.getBetId() == betId ) {
                it.remove();
                System.out.println("delete bet " + found);
            }
        }
        saveCollection();
    }
    
    @Override
    public Bet getBet(long betId) throws TotalizatorDAOException{
        Bet result = null;
        for(Bet b: bets) {
            
            if(b.getBetId() == betId){
                result = (Bet) b.clone();
                break;
            }
        }
        return result;
    }
    
    private long generateBetId() {
        long betId = Math.round(Math.random() * 100000);
        boolean found = true;
        while (found) {
            found = false;
            for (Bet b : bets) {
                if (b.getBetId() == betId) {
                    found = true;
                    break;
                }
            }
            betId = Math.round(Math.random() * 10000);
        }
        return betId;
    }
    
    protected abstract void saveCollection() throws TotalizatorDAOException;
    protected abstract void loadCollection() throws TotalizatorDAOException;
}
