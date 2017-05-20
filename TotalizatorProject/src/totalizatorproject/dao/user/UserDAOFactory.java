package totalizatorproject.dao.user;

import totalizator.configurations.TotalizatorSettings;
import totalizatorproject.dao.a_test.UserDAOTest;
import totalizatorproject.logic.DAOAnnotation;

@DAOAnnotation(daoName = "totalizatorproject.dao.a_test.UserFileSerialDAO")
public class UserDAOFactory {

    public static UserDAO GetUserDAO() {
        UserDAO dao = null;

        String className = TotalizatorSettings.getUserDAOClassName();

        if (className == null) {
            DAOAnnotation daoAnn = UserDAOFactory.class.getAnnotation(DAOAnnotation.class);
            if (daoAnn != null) {
                className = daoAnn.daoName();
                System.out.println("USER_FROM_ANN");
            }
        }

        if (className != null) {
            System.out.println("USER_FROM_FILE");
            try {
                Class cd = Class.forName(className);
                dao = (UserDAO) cd.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            dao = new UserDAOTest();
        }
        return dao;
    }
}