package totalizatorproject.dao.horserace;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import totalizatorproject.entity.Horserace;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class HorseracePostgresqlDAO implements HorseraceDAO {

    private static final String INSERT = "INSERT INTO totalizator_horserace (horserace_date, horserace_winner) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE totalizator_horserace SET horserace_date=?, horserace_winner=?"
            + "WHERE horserace_id=?";
    private static final String DELETE = "DELETE FROM totalizator_horserace WHERE horserace_id=?";
    private static final String SELECT_ONE = "SELECT * FROM totalizator_horserace WHERE horserace_id=?";
    private static final String SELECT_ALL = "SELECT * FROM totalizator_horserace ORDER BY horserace_id";

    private static final String INSERT_HORSES = "INSERT INTO horserace_horses (horserace_id, horse_name) VALUES (?, ?)";
    private static final String UPDATE_HORSES = "UPDATE horserace_horses SET horse_name=? WHERE horserace_id=?";
    private static final String DELETE_HORSES = "DELETE FROM horserace_horses WHERE horserace_id=?";
    private static final String SELECT_HORSES = "SELECT * FROM horserace_horses WHERE horserace_id=?";

    private static final String PROP_NAME = "database.properties";
    private static String url;
    private static String login;
    private static String password;

    static {
        try {
            Properties pro = new Properties();
            pro.load(new FileReader(PROP_NAME));
            url = pro.getProperty("dao.postgres.url");
            login = pro.getProperty("dao.postgres.login");
            password = pro.getProperty("dao.postgres.password");
            Class.forName(pro.getProperty("dao.postgresql.driver"));
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public long addHorserace(Horserace race) throws TotalizatorDAOException {
        long raceId = -1;
        try {
            Connection conn = getConnection();
            try {
                String[] str = {"horserace_id"};
                PreparedStatement ps = conn.prepareStatement(INSERT, str);
                ps.setString(1, (String) (race.getDate() + ""));
                ps.setString(2, race.getWinner());                  
                ps.executeUpdate();
                ResultSet gk = ps.getGeneratedKeys();
                while (gk.next()) {
                    raceId = gk.getLong(1);
                    race.setRaceId(raceId);
                    batchAddHorses(conn, race);                                 //batchHorses
                }
                gk.close();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return raceId;
    }

    @Override
    public void uppdateHorserace(Horserace race) throws TotalizatorDAOException {
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(UPDATE);
                ps.setString(1, race.getDate() + "");           
                ps.setString(2, race.getWinner());
                ps.setLong(3, race.getRaceId());
                batchUpdateHorses(conn, race);                                  //batchHorses
                ps.executeUpdate();                                  
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    public void deleteHorserace(long raceId) throws TotalizatorDAOException {
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(DELETE);
                ps.setLong(1, raceId);
                deleteHorses(conn, getHorserace(raceId));                  //batchHorses
                ps.executeUpdate();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
    }

    @Override
    public Horserace getHorserace(long raceId) throws TotalizatorDAOException {
        Horserace race = null;
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_ONE);
                ps.setLong(1, raceId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    race = new Horserace();
                    race.setRaceId(rs.getLong("horserace_id"));
                    race.setDate(rs.getString("horserace_date"));
                    race.setWinner(rs.getString("horserace_winner"));
                    race.setHorses((ArrayList<String>) findHorses(race.getRaceId()));
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
            if (race == null) {
                throw new TotalizatorDAOException("No Horserace with ID " + raceId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            throw new TotalizatorDAOException(ex);
        }
        return race;
    }

    @Override
    public List<Horserace> getRaces() throws TotalizatorDAOException {
        List<Horserace> races = new LinkedList<>();
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Horserace race = new Horserace();
                    race.setRaceId(rs.getLong("horserace_id"));
                    race.setDate(rs.getDate("horserace_date"));
                    race.setWinner(rs.getString("horserace_winner"));
                    race.setHorses((ArrayList<String>) findHorses(race.getRaceId()));
                    races.add(race);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return races;
    }

    public void batchAddHorses(Connection con, Horserace race) throws SQLException { 
        ArrayList<String> horses = race.getHorses();
        con.setAutoCommit(false);
        PreparedStatement pst = con.prepareStatement(INSERT_HORSES);
            for (String horse : horses) {
                pst.setLong(1, race.getRaceId());
                pst.setString(2, horse);
                pst.addBatch();
            }
        
        con.commit();
        pst.executeBatch();
        con.setAutoCommit(true);
    }
    
    public void batchUpdateHorses(Connection con, Horserace race) throws SQLException { 
        ArrayList<String> horses = race.getHorses();
        con.setAutoCommit(false);
        PreparedStatement pst = con.prepareStatement(UPDATE_HORSES);
            for (String horse : horses) {
                pst.setString(1, horse);
                pst.setLong(2, race.getRaceId());
                pst.addBatch();
            }
        con.commit();
        pst.executeBatch();
        con.setAutoCommit(true);
    }
    
    public void deleteHorses(Connection con, Horserace race) throws SQLException { 
        ArrayList<String> horses = race.getHorses();
        try{
            PreparedStatement pst = con.prepareStatement(DELETE_HORSES);
            pst.setLong(1, race.getRaceId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        
    }
    
    public List<String> findHorses(long raceId) throws SQLException {            //получение списков лошадей из totalizator_horses)
        List<String> horses = new ArrayList<>();
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_HORSES);
                ps.setLong(1, raceId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String horse = rs.getString("horse_name");
                    horses.add(horse);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return horses;
    }
}
