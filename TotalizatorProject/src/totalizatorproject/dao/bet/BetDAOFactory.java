package totalizatorproject.dao.bet;

import totalizator.configurations.TotalizatorSettings;
import totalizatorproject.dao.a_test.BetDAOTest;
import totalizatorproject.logic.DAOAnnotation;

@DAOAnnotation(daoName = "totalizatorproject.dao.fotest.BetDAOTest")
public class BetDAOFactory {

    public static BetDAO getBetDAO() {
        BetDAO dao = null;
        String className = TotalizatorSettings.getBetDAOClassName();
        
        if(className ==null) {
            DAOAnnotation daoAnn = BetDAOFactory.class.getAnnotation(DAOAnnotation.class);
            if (daoAnn != null) {
                className = daoAnn.daoName();
                System.out.println("BET_FROM_ANN");
            }
        }
        if (className != null) {
            System.out.println("BET_FROM_FILE");
            try {
                Class cd = Class.forName(className);
                dao = (BetDAO) cd.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            dao = new BetDAOTest();
        }
        return dao;
    }
}