package totalizatorproject.dao.a_test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import totalizatorproject.dao.bet.BetDAO;
import totalizatorproject.entity.Bet;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class BetDAOTest implements BetDAO {

    List<Bet> bets = new ArrayList<Bet>();
    
    @Override
    public long addBet(Bet bet) throws TotalizatorDAOException {
            long betId = generateBetId();
        try{
            bet.setBetId(betId);
            Bet b = (Bet)bet.clone();
            bets.add(b);
        } catch (Exception ex) {
            throw new TotalizatorDAOException(ex);
        }
        System.out.println("add Bet");
        return betId;
    }

    @Override
    public void uppdateBet(Bet bet) throws TotalizatorDAOException {
        Bet result = getBet(bet.getBetId());
        result.setRace(bet.getRace());
        result.setHorse(bet.getHorse());
        result.setSumm(bet.getSumm());
        System.out.println("uppdate Bet");

    }

    @Override
    public void sendBetResult(long betId) throws TotalizatorDAOException {
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
        for (Iterator<Bet> it = bets.iterator(); it.hasNext();) {
            Bet found = it.next();
            if (found.getBetId() == betId ) {
                it.remove();
                System.out.println("delete bet " + found);
            }
        }
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
}
