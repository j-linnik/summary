package totalizatorproject.dao.horserace;

import totalizator.configurations.TotalizatorSettings;
import totalizatorproject.dao.a_test.HorseraceDAOTest;
import totalizatorproject.logic.DAOAnnotation;

@DAOAnnotation(daoName = "totalizatorproject.dao.fotest.HorseraceDAOTest")
public class HorseraceDAOFactory {

    public static HorseraceDAO getHorseraceDAO() {
        HorseraceDAO dao = null;
        String className = TotalizatorSettings.getHorseraceDOAClassName();

        if (className == null) {
            DAOAnnotation daoAnn = HorseraceDAOFactory.class.getAnnotation(DAOAnnotation.class);
            if (daoAnn != null) {
                className = daoAnn.daoName();
                System.out.println("RACE_FROM_ANN");
            }
        }
        if (className != null) {
            System.out.println("RACE_FROM_FILE");
            try {
                Class cd = Class.forName(className);
                dao = (HorseraceDAO) cd.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            dao = new HorseraceDAOTest();
        }
        return dao;
    }
}
