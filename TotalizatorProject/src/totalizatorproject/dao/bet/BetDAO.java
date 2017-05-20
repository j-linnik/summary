package totalizatorproject.dao.bet;

import totalizatorproject.entity.Bet;
import totalizatorproject.exceptions.TotalizatorDAOException;

public interface BetDAO {
   public long addBet(Bet bet) throws TotalizatorDAOException;
   public void uppdateBet(Bet bet) throws TotalizatorDAOException;
   public void sendBetResult(long betId) throws TotalizatorDAOException;
   public void deleteBet(long betId) throws TotalizatorDAOException;
   public Bet getBet(long betId) throws TotalizatorDAOException;
}
