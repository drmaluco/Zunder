package com.Mining.app.Money;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Carlos Roldan on 09/07/2017.
 */

public class DataBase {
    private String userEmail, userGPU, currency = "", message = "", language = "", pass = "",
            time, money, paymentType, address, user, bug, magia, winnerPass, version,
            firstWinner, secondWinner, thirdWinner, fourWinner, fifthWinner, prizeText,
            promotionCode;

    private int eur1 = 0, eur2 = 0, eur3 = 0, dec1 = 0, dec2 = 0, dec3 = 0, dec4 = 0, id,
            helios, winner, ticket, heliosReceived, lotteryRound, prizeType,  x, w, d, h, m, s;

    private boolean anyMessage, merlinAvailable, readyPayment, online, anyWinner, anyTicket,
            firstTimeLottery, firstTimeStore;

    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String ip = "62.43.194.93";
    private String DB_URL = "jdbc:mysql://" + ip + "/database";
    private String USER = "root";
    private String PASS = "c4rl0s";


    public DataBase() {
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://" + "62.43.194.93" + "/database";
        USER = "root";
        PASS = "c4rl0s";

    }

    public String getPrizeText() {
        return prizeText;
    }

    public void setPrizeText(String prizeText) {
        this.prizeText = prizeText;
    }

    public int getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(int prizeType) {
        this.prizeType = prizeType;
    }

    public String getWinnerPass() {
        return winnerPass;
    }

    public void setWinnerPass(String winnerPass) {
        this.winnerPass = winnerPass;
    }

    public boolean isFirstTimeStore() {
        return firstTimeStore;
    }

    public void setFirstTimeStore(boolean firstTimeStore) {
        this.firstTimeStore = firstTimeStore;
    }

    public int getHeliosReceived() {
        return heliosReceived;
    }

    public void setHeliosReceived(int heliosReceived) {
        this.heliosReceived = heliosReceived;
    }

    public boolean isFirstTimeLottery() {
        return firstTimeLottery;
    }

    public void setFirstTimeLottery(boolean firstTimeLottery) {
        this.firstTimeLottery = firstTimeLottery;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public boolean isAnyWinner() {
        return anyWinner;
    }

    public void setAnyWinner(boolean anyWinner) {
        this.anyWinner = anyWinner;
    }

    public int getHelios() {
        return helios;
    }

    public void setHelios(int helios) {
        this.helios = helios;
    }

    public String getMagia() {
        return magia;
    }

    public void setMagia(String magia) {
        this.magia = magia;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setBug(String bug) {
        this.bug = bug;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getTime() {
        return time;
    }

    public String getMoney() {
        return money;
    }

    public void load() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "select * from tableta WHERE email = '" + getUserEmail() + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                setId(rs.getInt("ID"));
                setUser(rs.getString("user"));
                setPass(rs.getString("pass"));
                setMagia(rs.getString("magic"));
                setUserEmail(rs.getString("email"));
                setEur1(rs.getInt("eur1"));
                setEur2(rs.getInt("eur2"));
                setEur3(rs.getInt("eur3"));
                setDec1(rs.getInt("dec1"));
                setDec2(rs.getInt("dec2"));
                setDec3(rs.getInt("dec3"));
                setDec4(rs.getInt("dec4"));
                setX(rs.getInt("month"));
                setW(rs.getInt("week"));
                setD(rs.getInt("day"));
                setH(rs.getInt("hour"));
                setM(rs.getInt("minute"));
                setS(rs.getInt("second"));
                setLanguage(rs.getString("language"));
                setCurrency(rs.getString("currency"));
                setMessage(rs.getString("message"));
                setAnyMessage(rs.getBoolean("anyMessage"));
                setOnline(rs.getBoolean("online"));
                setUserGPU(rs.getString("nameGPU"));
                setHelios(rs.getInt("helios"));
                setTicket(rs.getInt("ticket"));
                setHeliosReceived(rs.getInt("heliosReceived"));
                setFirstTimeLottery(rs.getBoolean("firstTimeLottery"));
                setFirstTimeStore(rs.getBoolean("firstTimeStore"));

            }
            rs.close();
            stmt.close();
            conn.close();

            money = String.valueOf(eur1)
                    + String.valueOf(eur2) + String.valueOf(eur3) + "." + String.valueOf(dec1) + String.valueOf(dec2) + String.valueOf
                    (dec3) + String.valueOf(dec4);
            time = (x <= 9 ? "" : "") + x + " Meses " + (w <= 3 ? "" : "") + w + " Semanas " + (d <= 6 ? "0" : "") + d + " Dias      " +
                    (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s;
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void addUser(String helios) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "INSERT INTO `database`.`tableta` (`ID`, `user`, `pass`, `email`, `magic`, " +
                    "`eur1`, `eur2`, `eur3`, `dec1`, `dec2`, `dec3`, `dec4`, `month`, `week`, " +
                    "`day`, `hour`, `minute`, `second`, `language`, `currency`, `online`, " +
                    "`message`, `anyMessage`, `nameGPU`, `readyPayment`, `paymentType`, `address`" +
                    ", `quantity`, `anyBug`, `bug`, `readed`, `autoLogin`, `helios`, `Mining`, " +
                    "`ticket`, `anyTicket`, `heliosReceived`, `firstTimeLottery`, `firstTimeStore`" +
                    ") VALUES (NULL, '"+getUser()+"', '"+getPass()+"', '"+getUserEmail()+"', '', '0', '0', '0', '0', '0'," +
                    " '0', '0', '0', '0', '0', '0', '0', '0', '', '', '', '', '', '', '', '', ''," +
                    " '', '', '', '', '', '"+helios+"', '', '0', '', '0', 'true', 'true');";
            stmt.executeUpdate(sql);


            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public int getEur1() {
        return eur1;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public void loadCommon() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "select * from common";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                setWinner(rs.getInt("winner"));
                setAnyWinner(rs.getBoolean("anyWinner"));
                setWinnerPass(rs.getString("winnerPass"));
                setLotteryRound(rs.getInt("lotteryRound"));
                setFirstWinner(rs.getString("firstWinner"));
                setSecondWinner(rs.getString("secondWinner"));
                setThirdWinner(rs.getString("thirdWinner"));
                setFourWinner(rs.getString("fourWinner"));
                setFifthWinner(rs.getString("fifthWinner"));
                setPrizeText(rs.getString("prizeText"));
                setPrizeType(rs.getInt("prizeType"));
                setVersion(rs.getString("version"));
                setPromotionCode(rs.getString("promotion"));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public int getLotteryRound() {
        return lotteryRound;
    }

    public void setLotteryRound(int lotteryRound) {
        this.lotteryRound = lotteryRound;
    }

    public String getFirstWinner() {
        return firstWinner;
    }

    public void setFirstWinner(String firstWinner) {
        this.firstWinner = firstWinner;
    }

    public String getSecondWinner() {
        return secondWinner;
    }

    public void setSecondWinner(String secondWinner) {
        this.secondWinner = secondWinner;
    }

    public String getThirdWinner() {
        return thirdWinner;
    }

    public void setThirdWinner(String thirdWinner) {
        this.thirdWinner = thirdWinner;
    }

    public String getFourWinner() {
        return fourWinner;
    }

    public void setFourWinner(String fourWinner) {
        this.fourWinner = fourWinner;
    }

    public String getFifthWinner() {
        return fifthWinner;
    }

    public void setFifthWinner(String fifthWinner) {
        this.fifthWinner = fifthWinner;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void doLoad(String whatItIs, String YesorNo) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "select * from tableta WHERE " + whatItIs + " = '" + YesorNo + "'"; //arreglar para que sea cualquier correo
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                setId(rs.getInt("ID"));
                setUser(rs.getString("user"));
                setPass(rs.getString("pass"));
                setUserEmail(rs.getString("email"));
                setEur1(rs.getInt("eur1"));
                setEur2(rs.getInt("eur2"));
                setEur3(rs.getInt("eur3"));
                setDec1(rs.getInt("dec1"));
                setDec2(rs.getInt("dec2"));
                setDec3(rs.getInt("dec3"));
                setDec4(rs.getInt("dec4"));
                setX(rs.getInt("month"));
                setW(rs.getInt("week"));
                setD(rs.getInt("day"));
                setH(rs.getInt("hour"));
                setM(rs.getInt("minute"));
                setS(rs.getInt("second"));
                setLanguage(rs.getString("language"));
                setCurrency(rs.getString("currency"));
                setMessage(rs.getString("message"));
                setAnyMessage(rs.getBoolean("anyMessage"));
                setAddress(rs.getString("address"));
                setPaymentType(rs.getString("paymentType"));
                setReadyPayment(rs.getBoolean("readyPayment"));
                setAddress(rs.getString("address"));
                setBug(rs.getString("bug"));
            }
            rs.close();
            stmt.close();
            conn.close();

            money = String.valueOf(eur1)
                    + String.valueOf(eur2) + String.valueOf(eur3) + "." + String.valueOf(dec1) + String.valueOf(dec2) + String.valueOf
                    (dec3) + String.valueOf(dec4);
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void update() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET " +
                    "`user` = '" + getUser() + "'" +
                    ", `pass` = '" + getPass() + "'" +
                    ", `email` = '" + getUserEmail() + "'" +
                    ", `magic` = '" + getMagia() +"'" +
                    ", `eur1`" + " = '" + getEur1() + "'" +
                    ", `eur2` = '" + eur2 + "'" +
                    ", `eur3` = '" + eur3 + "'" +
                    ", `dec1` = '" + dec1 + "'" +
                    ", `dec2` = '" + dec2 + "'" +
                    ", `dec3` = '" + dec3 + "'" +
                    ", `dec4` = '" + dec4 + "'" +
                    ", `month` = '" + x + "'" +
                    ", `week` = '" + w + "'" +
                    ", `day` = '" + d + "'" +
                    ", `hour` = '" + h + "'" +
                    ", `minute` = '" + m + "'" +
                    ", `second` = '" + s + "'" +
                    ", `language` = '" + getLanguage() + "'" +
                    ", `currency` = '" + getCurrency() + "'" +
                    ", `nameGPU` = '" + getUserGPU() + "'" +
                    ", `helios` = '" + getHelios() + "'" +
                    ", `anyTicket` = '" + isAnyTicket()  + "'" +
                    ", `ticket` = '" + getTicket() + "'" +
                    ", `readyPayment` = '" + isReadyPayment()+"'" +
                    ", `address` = '" + getAddress() +"'" +
                    ", `firstTimeLottery` = '" + isFirstTimeLottery() + "'" +
                    ", `heliosReceived` = '" + getHeliosReceived() +"'" +
                    ", `firstTimeStore` = '" + isFirstTimeStore() +"'" +
                    " WHERE `tableta`.`ID` =" + id);

            stmt.close();
            st.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    }

    public void updateCommon() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`common` SET " +
                    "  `firstWinner` = '" + getFirstWinner() + "'" +
                    ", `secondWinner` = '" + getSecondWinner() + "'" +
                    ", `thirdWinner` = '" + getThirdWinner() + "'" +
                    ", `fourWinner` = '" + getFourWinner() +"'" +
                    ", `fifthWinner`" + " = '" + getFifthWinner() + "'" +
                    ", `lotteryRound` = '" + getLotteryRound() + "'" +
                    ", `winner` = '" + getWinner() + "'" +
                    ", `anyWinner` = '" + isAnyWinner() + "'" +
                    " WHERE `tableta`.`ID` =" + id);

            stmt.close();
            st.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    }

    public boolean isAnyTicket() {
        return anyTicket;
    }

    public void setAnyTicket(boolean anyTicket) {
        this.anyTicket = anyTicket;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isReadyPayment() {
        return readyPayment;
    }

    public void setReadyPayment(boolean readyPayment) {
        this.readyPayment = readyPayment;
    }

    public void checkMerlinAvailability() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "select * from common WHERE merlinAvailable = 'true'"; //arreglar para que sea cualquier correo
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                setMerlinAvailable(rs.getBoolean("merlinAvailable"));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isMerlinAvailable() {
        return merlinAvailable;
    }

    public void setMerlinAvailable(boolean merlinAvailable) {
        this.merlinAvailable = merlinAvailable;
    }

    public void updatePaymentType() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET `paymentType` = '" + getPaymentType() + "' WHERE ID =" + "'" + getId() + "'");

            st.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    }

    public void updateMoney0() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` " +
                    "SET `eur1` = '0'," +
                    "`eur2` = '0', " +
                    "`eur3` = '0', " +
                    "`dec1` = '0', " +
                    "`dec2` = '0', " +
                    "`dec3` = '0' ," +
                    "`dec4` = '0'," +
                    " WHERE ID =" + "'" + getId() + "'");

            st.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    }

    public void updateAddress() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET `address` = '" + getAddress() + "' WHERE ID =" + "'" + getId() + "'");

            st.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }
    }

    public String getUserGPU() {
        return userGPU;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMessage() {
        return message;
    }

    public String getLanguage() {
        return language;
    }

    public String getPass() {
        return pass;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserGPU(String userGPU) {
        this.userGPU = userGPU;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEur1(int eur1) {
        this.eur1 = eur1;
    }

    public void setEur2(int eur2) {
        this.eur2 = eur2;
    }

    public void setEur3(int eur3) {
        this.eur3 = eur3;
    }

    public void setDec1(int dec1) {
        this.dec1 = dec1;
    }

    public void setDec2(int dec2) {
        this.dec2 = dec2;
    }

    public void setDec3(int dec3) {
        this.dec3 = dec3;
    }

    public void setDec4(int dec4) {
        this.dec4 = dec4;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setD(int d) {
        this.d = d;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getId() {
        return id;
    }
    public boolean isAnyMessage() {
        return anyMessage;
    }

    //----------SETTERS------------
    public void setAnyMessage(boolean anyMessage) {
        this.anyMessage = anyMessage;
    }
}


