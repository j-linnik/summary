package totalizatorproject.logic;

import totalizatorproject.dao.bet.BetDAO;
import totalizatorproject.dao.bet.BetDAOFactory;
import totalizatorproject.entity.Bet;
import totalizatorproject.exceptions.TotalizatorBusinessException;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class BetManager {

    BetDAO dao = BetDAOFactory.getBetDAO();
    
    public long addBet(Bet bet) throws TotalizatorBusinessException{
        try {
            return dao.addBet(bet);
        } catch (TotalizatorDAOException ex) {
            System.out.println("Error! BetManager addBet not working!");
           throw new TotalizatorBusinessException (ex); 
        }
    }
    
    public void uppdateBet(Bet bet){
        try {
            dao.uppdateBet(bet);
        } catch (TotalizatorDAOException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Error! BetManager checkBet not working!");
        }
    }

    public void sendBetResult(long betId){
        try {
            dao.sendBetResult(betId);
        } catch (TotalizatorDAOException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Error! BetManager sendBetResult not working!");
        }
    }

    public void deleteBet(long betId){
        try {
            dao.deleteBet(betId);
        } catch (TotalizatorDAOException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Error! BetManager deleteBet not working!");
        }
    }
    
}
