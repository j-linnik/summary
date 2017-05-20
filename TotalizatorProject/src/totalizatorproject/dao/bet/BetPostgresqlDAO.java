package totalizatorproject.dao.bet;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import totalizatorproject.entity.Bet;
import totalizatorproject.entity.Horserace;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class BetPostgresqlDAO implements BetDAO{
    
    
    private static final String INSERT = "INSERT INTO totalizator_bet (user_mail, race_id, bet_horse, bet_summ) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE totalizator_bet SET user_mail=?, race_id=?, bet_horse=?, bet_summ=? "
            + "WHERE bet_id=?";
    private static final String DELETE = "DELETE FROM totalizator_bet WHERE bet_id=?";
    private static final String SELECT_ONE = "SELECT * FROM totalizator_bet WHERE bet_id=?";
    private static final String SELECT_ALL = "SELECT * FROM totalizator_bet ORDER BY bet_id";
    private static final String SELECT_USER = "SELECT * FROM totalizator_user WHERE user_mail=?";
    private static final String SELECT_HORSERACE = "SELECT * FROM totalizator_horserace WHERE horserace_id=?";
    
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
        try{
            return DriverManager.getConnection(url, login, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public long addBet(Bet bet) throws TotalizatorDAOException {
        long betId = 0;
        try {
            Connection conn = getConnection();
            try {
                String[] str = {"bet_id"};
                PreparedStatement ps = conn.prepareStatement(INSERT, str);
                ps.setString(1, bet.getUser().getMail());
                ps.setLong(2, bet.getRace().getRaceId());
                ps.setString(3, bet.getHorse());
                ps.setInt(4, bet.getSumm());
                ps.executeUpdate();
                ResultSet gk = ps.getGeneratedKeys();
                while (gk.next()) {
                    betId = gk.getLong(1);
                }
                gk.close();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return betId;
    }

    @Override
    public void uppdateBet(Bet bet) throws TotalizatorDAOException {
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(UPDATE);
                ps.setString(1, bet.getUser().getMail());
                ps.setLong(2, bet.getRace().getRaceId());
                ps.setString(3, bet.getHorse());
                ps.setInt(4, bet.getSumm());
                ps.setLong(5, bet.getBetId());
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
    public void sendBetResult(long betId) throws TotalizatorDAOException {
        Bet bet = getBet(betId);
        Date date = new Date();
        if(bet.getRace().getDate().compareTo(date) <= 0){
            System.out.println("Send bet result to: " + bet.getUser().getMail());
        } else {
            System.out.println("The race with ID " + bet.getRace().getRaceId() + " is not over yet. Bet is valid.");
        }
    }

    @Override
    public void deleteBet(long betId) throws TotalizatorDAOException {
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(DELETE);
                ps.setLong(1, betId);
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
    public Bet getBet(long betId) throws TotalizatorDAOException {
        Bet bet = null;
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_ONE);
                ps.setLong(1, betId);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    bet = new Bet();
                    bet.setBetId(rs.getLong("bet_id"));
                    bet.setUser(findUser(rs.getString("user_mail")));
                    bet.setRace(findHorserace(rs.getLong("race_id")));
                    bet.setHorse(rs.getString("bet_horse"));
                    bet.setSumm(rs.getInt("bet_summ"));
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
            if(bet == null) {
                throw new TotalizatorDAOException("No bet with ID " + betId);
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return bet;
    }
    
    public List<Bet> getBets() throws TotalizatorDAOException {
        return null;
    }
    
    public User findUser(String mail) throws TotalizatorDAOException {
        User user = null;
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_USER);
                ps.setString(1, mail);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    user = new User();
                    user.setMail(rs.getString("user_mail"));
                    user.setBalance(rs.getInt("user_balance"));
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
            if(user == null) {
                throw new TotalizatorDAOException("Error! Bet is invalid! No user with mail " + mail);
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return user;
    }
    
    public Horserace findHorserace(long raceId)  throws TotalizatorDAOException {
        Horserace race = null;
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_HORSERACE);
                ps.setLong(1, raceId);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    race = new Horserace();
                    race.setRaceId(rs.getLong("horserace_id"));
                    race.setDate(rs.getString("horserace_date"));
                    race.setWinner(rs.getString("horserace_winner"));
                            //здесь нужно релизовать добавление списка лошадей из другой таблицы
                            //возможно стоит написать отдельный метод getHorsesDB(???) - отличная идея!
                            //отвратная идея.
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
            if(race == null) {
                throw new TotalizatorDAOException("Error! Bet is invalid! No Horserace with ID " + raceId);
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return race;
    }
}
