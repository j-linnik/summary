package totalizatorproject.dao.user;

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
import totalizatorproject.entity.Bet;
import totalizatorproject.entity.Horserace;
import totalizatorproject.entity.User;
import totalizatorproject.exceptions.TotalizatorDAOException;

public class UserPostgresqlDAO implements UserDAO {

    private static final String INSERT = "INSERT INTO totalizator_user (user_mail, user_balance) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE totalizator_user SET user_balance=? "
            + "WHERE user_mail=?";
    private static final String DELETE = "DELETE FROM totalizator_user WHERE user_mail=?";
    private static final String SELECT_ONE = "SELECT * FROM totalizator_user WHERE user_mail=?";
    private static final String SELECT_ALL = "SELECT * FROM totalizator_user ORDER BY user_mail";

    private static final String UPDATE_BETS = "UPDATE totalizator_bet SET bet_id=? race_id=?, bet_horse=?, bet_summ=?"
            + "WHERE user_mail=?";
    private static final String DELETE_BETS = "DELETE FROM totalizator_bet WHERE user_mail=?";
    private static final String SELECT_BETS = "SELECT * FROM totalizator_bet WHERE user_mail=?";

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
    public String addUser(User user) throws TotalizatorDAOException {
        String mail = "_";
        try {
            Connection conn = getConnection();
            try {
                String[] str = {"user_mail"};
                PreparedStatement ps = conn.prepareStatement(INSERT, str);
                ps.setString(1, user.getMail());
                ps.setInt(2, user.getBalance());
                ps.executeUpdate();
                ResultSet gk = ps.getGeneratedKeys();
                while (gk.next()) {
                    mail = gk.getString(1);
                }
                gk.close();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return mail;
    }

    @Override
    public void uppdateUser(String mail) throws TotalizatorDAOException {
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(UPDATE);
                ps.setInt(1, getUser(mail).getBalance());
                ps.setString(2, mail);
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
    public void deleteUser(String mail) throws TotalizatorDAOException {

        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(DELETE);
                ps.setString(1, mail);
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
    public User getUser(String mail) throws TotalizatorDAOException {
        User us = null;
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_ONE);
                ps.setString(1, mail);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    us = new User();
                    us.setMail(rs.getString("user_mail"));
                    us.setBalance(rs.getInt("user_balance"));
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
            if (us == null) {
                throw new TotalizatorDAOException("No user found for mail " + mail);
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return us;
    }

    @Override
    public List<User> getUsersList() throws TotalizatorDAOException {
        List<User> users = new LinkedList<>();
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setMail(rs.getString("user_mail"));
                    user.setBalance(rs.getInt("user_balanse"));
                    users.add(user);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            throw new TotalizatorDAOException(ex);
        }
        return users;
    }

    public void batchDeleteBets(Connection con, User user) throws SQLException {
        ArrayList<Bet> bets = user.getBets();
        con.setAutoCommit(false);
        PreparedStatement pst = con.prepareStatement(DELETE_BETS);
        for (Bet bet : bets) {
            pst.setString(1, user.getMail());
            pst.addBatch();
        }
        con.commit();
        pst.executeBatch();
        con.setAutoCommit(true);
    }

    public List<Bet> findBets(String mail) throws SQLException {
        List<Bet> bets = new ArrayList<>();
        try {
            Connection conn = getConnection();
            try {
                PreparedStatement ps = conn.prepareStatement(SELECT_BETS);
                ps.setString(1, mail);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Bet bet = new Bet();
                    bet.setBetId(rs.getLong("bet_id"));
                    bet.setRace(findHorserace(conn, rs.getLong("race_id")));
                    bet.setHorse(rs.getString("bet_horse"));
                    bet.setSumm(rs.getInt("bet_summ"));
                    bets.add(bet);
                }
                rs.close();
                ps.close();
            } finally {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return bets;
    }

    public Horserace findHorserace(Connection con, long raceId) throws SQLException {
        String SELECT_HORSERACE = "SELECT * FROM totalizator_horserace WHERE horserace_id=?";
        Horserace race = null;
        try {
            PreparedStatement pst = con.prepareStatement(SELECT_HORSERACE);
            pst.setLong(1, raceId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                race = new Horserace();
                race.setRaceId(rs.getLong("horserace_id"));
                race.setDate(rs.getString("horserace_date"));
                race.setWinner(rs.getString("horserace_winner"));
                race.setHorses((ArrayList<String>) findHorses(con, rs.getLong("race_id")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return race;
    }

    public List<String> findHorses(Connection con, long raceId) throws SQLException {
        String SELECT_HORSES = "SELECT * FROM horserace_horses WHERE horserace_id=?";
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
