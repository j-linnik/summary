package totalizator.configurations;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TotalizatorSettings {

    private static final String FILE_NAME = "totalizator.properties";

    
    
    public static String USER_CLASS_NAME = "user.class.name";
    public static String HORSERACE_CLASS_NAME = "horserace.class.name";
    public static String BET_CLASS_NAME = "bet.class.name";

    public static String FILE_NAME_COMMA = "dao.file.comma";
    
    private static Properties proper = new Properties();
    
    static {
        try {
            proper.load(new FileReader(FILE_NAME)); 
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static String getProperty(String name){
        return proper.getProperty(name);
    }
    
    public static String getUserDAOClassName() {
        return proper.getProperty(USER_CLASS_NAME);
    }
    public static String getHorseraceDOAClassName() {
        return proper.getProperty(HORSERACE_CLASS_NAME);
    }
    public static String getBetDAOClassName() {
        return proper.getProperty(BET_CLASS_NAME);
    }
}