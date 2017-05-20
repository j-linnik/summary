package totalizatorproject.dao.bet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import totalizatorproject.entity.Bet;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class BetFileCommaDAO extends BetFileDAO{

    private static final String FILE_NAME = "bet.txt";
    
    @Override
    protected void saveCollection() throws TotalizatorDAOException {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            try {
                BufferedWriter bw = new BufferedWriter(fw);
                for(Bet b : bets) {
                    bw.write(/*b.getUser().getMail() + "," + b.getRace().getRaceId()+ "," + */ //как достать эти сущности в строках 47, 48? 
                            b.getBetId() + "," + b.getHorse() + "," + b.getSumm() 
                            + System.lineSeparator());
                }
                bw.close();
            } finally {
               fw.close();
            }
        }catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    protected void loadCollection() throws TotalizatorDAOException {
        bets = new ArrayList<>();
        try{
            FileReader fr = new FileReader(FILE_NAME);
            try{
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while((line = br.readLine()) != null) {
                    String [] params = line.split("\\s*,\\s*");
                    Bet bet = new Bet();
                    //bet.setUser(params[0]);
                    //bet.setRace(params[1]);
                    bet.setBetId(Long.parseLong(params[0]));
                    bet.setHorse(params[1]);
                    bet.setSumm(Integer.parseInt(params[2])); 
                    
                    bets.add(bet);
                }
                br.close();
            } finally {
                fr.close();
            }
        } catch (IOException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }
    
}
