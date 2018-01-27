package com.example.carlosroldan.merlinminerapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Carlos Roldan on 09/07/2017.
 */

public class DataBase {
    private String userEmail, userGPU, currency = "", message = "", language = "", pass = "",
            time, money, paymentType, speed, address, user, bug;
    private String event, DGBpriceUSD, DGBpriceGBP, DGBpriceEUR, version;
    private ArrayList<Integer> listID, listIDOff;
    private ArrayList<String> listNames, listNamesOff, peopleReadyToPay;
    private int eur1 = 0, eur2 = 0, eur3 = 0, dec1 = 0, dec2 = 0, dec3 = 0, dec4 = 0, id, usersAmount;
    private int x, w, d, h, m, s;
    private boolean anyMessage, anyUpdate, merlinAvailable, eventAvailable, readyPayment, online, anyBug, readed;
    private Double DGBdiff;
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String ip = "79.152.184.21";
    private String DB_URL = "jdbc:mysql://" + ip + "/database";
    private String USER = "root";
    private String PASS = "c4rl0s";


    public DataBase() {
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://" + "79.152.184.21" + "/database";
        USER = "root";
        PASS = "c4rl0s";
        listNames = new ArrayList<>();
        listID = new ArrayList<>();
        listNamesOff = new ArrayList<>();
        listIDOff = new ArrayList<>();

    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getBug() {
        return bug;
    }

    public void setBug(String bug) {
        this.bug = bug;
    }

    public boolean isOnline() {
        return online;
    }

    public String getIp() {
        return ip;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isAnyBug() {
        return anyBug;
    }

    public void setAnyBug(boolean anyBug) {
        this.anyBug = anyBug;
    }

    public String getTime() {
        return time;
    }

    public String getMoney() {
        return money;
    }

    public ArrayList<Integer> getListID() {
        return listID;
    }

    public void load(String Email) throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            usersAmount = 0;
            sql = "select * from tableta WHERE email = '" + Email + "'"; //arreglar para que sea cualquier correo
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usersAmount++;
                setId(rs.getInt("ID"));
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

    public int getEur1() {
        return eur1;
    }

    public int getEur2() {
        return eur2;
    }

    public int getEur3() {
        return eur3;
    }

    public int getDec1() {
        return dec1;
    }

    public int getDec2() {
        return dec2;
    }

    public int getDec3() {
        return dec3;
    }

    public int getDec4() {
        return dec4;
    }

    public void load() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            usersAmount = 0;
            sql = "select * from tableta"; //arreglar para que sea cualquier correo
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usersAmount++;
                setId(rs.getInt("ID"));
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
                setUserGPU(rs.getString("nameGPU"));
                setReadyPayment(rs.getBoolean("readyPayment"));
                setPaymentType(rs.getString("paymentType"));
                setAddress(rs.getString("address"));
                setAnyBug(rs.getBoolean("anyBug"));
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

    public void loadCommon() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            usersAmount = 0;
            sql = "select * from common";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                setDGBpriceUSD(rs.getString("DGBpriceUSD"));
                setDGBpriceEUR(rs.getString("DGBprice"));
                setDGBpriceGBP(rs.getString("DGBpriceGBP"));
                setVersion(rs.getString("version"));
                setDGBdiff(rs.getDouble("DGBdiff"));
                setEvent(rs.getString("event"));
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
            usersAmount = 0;
            sql = "select * from tableta WHERE " + whatItIs + " = '" + YesorNo + "'"; //arreglar para que sea cualquier correo
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usersAmount++;
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
                setAnyBug(rs.getBoolean("anyBug"));
                setBug(rs.getString("bug"));
                setReaded(rs.getBoolean("readed"));
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

    public void messageReceived() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            usersAmount = 0;
            sql = "select * from tableta WHERE anyMessage = 'true', readed = 'false'"; //arreglar para que sea cualquier correo
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usersAmount++;
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
                setAnyBug(rs.getBoolean("anyBug"));
                setBug(rs.getString("bug"));
                setReaded(rs.getBoolean("readed"));
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

    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
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

    public void usersOnline() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "Select * From tableta where online = 'true'";
            ResultSet rst;
            rst = stmt.executeQuery(sql);
            while (rst.next()) {
                listNames.add(rst.getString("user"));
                listID.add(rst.getInt("ID"));
            }
            rst.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
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

    public void checkMerlinNOTAvailability() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "select * from common WHERE merlinAvailable = 'false'"; //arreglar para que sea cualquier correo
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


    public boolean isMerlinAvailable() {
        return merlinAvailable;
    }

    public void setMerlinAvailable(boolean merlinAvailable) {
        this.merlinAvailable = merlinAvailable;
    }

    public boolean isEventAvailable() {
        return eventAvailable;
    }

    public void setEventAvailable(boolean eventAvailable) {
        this.eventAvailable = eventAvailable;
    }

    public Double getDGBdiff() {
        return DGBdiff;
    }

    public void setDGBdiff(Double DGBdiff) {
        this.DGBdiff = DGBdiff;
    }

    public void updateDGBdiff() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`common` SET `DGBdiff` = '" + DGBdiff + "'");

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

    public void updateAnyBug() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET `anyBug` = '" + isAnyBug() + "' WHERE ID =" +getId());

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

    public void updateReaded() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET `readed` = '" + isReaded() + "'");

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

    public void add1Euro() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET `eur3` = " + String.valueOf(getD() + 1) +"WHERE ID = " + getId());

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

    public void deleteAccount() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM `database`.`tableta` WHERE `tableta`.`ID` =" + getId());

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

    public void updateMerlinAvailable() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`common` SET `merlinAvailable` = '" + isMerlinAvailable() + "'");

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

    public void updateEvent() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`common` SET `event` = '" + event + "'");

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

    public void updateEventAvailable() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`common` SET `eventAvailable` = '" + eventAvailable + "'");

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

    public void updateReadyPayment(String id) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET `readyPayment` = '" + readyPayment + "' WHERE ID =" + "'" + id + "'");

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

    public String getDGBpriceEUR() {
        return DGBpriceEUR;
    }

    public void setDGBpriceEUR(String DGBpriceEUR) {
        this.DGBpriceEUR = DGBpriceEUR;
    }

    public void usersOffline() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "Select * From tableta where online = 'false'";
            ResultSet rst;
            rst = stmt.executeQuery(sql);
            while (rst.next()) {
                listNamesOff.add(rst.getString("user"));
                listIDOff.add(rst.getInt("ID"));
            }
            rst.close();
            stmt.close();
            conn.close();

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public void removeListId() {
        listID.clear();
    }

    public void removeListIdOff() {
        listIDOff.clear();
    }

    public void removeListNames() {
        listNames.clear();
    }

    public void removeListNamesOff() {
        listNamesOff.clear();
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public Boolean isPaymentTrue() {
        boolean valid = false;

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "Select * From tableta where readyPayment = 'true'";
            ResultSet rst;
            rst = stmt.executeQuery(sql);
            while (rst.next()) {
                peopleReadyToPay.add(rst.getString("user"));
                valid = true;
            }
            rst.close();
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
        return valid;
    }

    public ArrayList<String> getListNames() {
        return listNames;
    }

    public int getAmountofNames() {
        return listNames.size();
    }

    public int getAmountNamesOFF() {
        return listNamesOff.size();
    }

    public ArrayList<Integer> getListOff() {
        return listIDOff;
    }

    public ArrayList<String> getListNamesOff() {
        return listNamesOff;
    }

    public boolean isAnyUpdate() {
        return anyUpdate;
    }

    public void setAnyUpdate(boolean anyUpdate) {
        this.anyUpdate = anyUpdate;
    }

    public String getDGBpriceUSD() {
        return DGBpriceUSD;
    }

    public void setDGBpriceUSD(String DGBpriceUSD) {
        this.DGBpriceUSD = DGBpriceUSD;
    }

    public String getDGBpriceGBP() {
        return DGBpriceGBP;
    }

    public void setDGBpriceGBP(String DGBpriceGBP) {
        this.DGBpriceGBP = DGBpriceGBP;
    }

    public void updateDGBpriceUSD() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "UPDATE `database`.`common` SET `DGBpriceUSD` = '" + Double.valueOf(getDGBpriceUSD()) + "'";
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

    public void updateDGBpriceGBP() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "UPDATE `database`.`common` SET `DGBpriceGBP` = '" + Double.valueOf(getDGBpriceGBP()) + "'";
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

    public void updateDGBpriceEUR() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "UPDATE `database`.`common` SET `DGBprice` = '" + Double.valueOf(getDGBpriceEUR()) + "'";
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

    public void updateAnyUpdate() {
        String update = "false";
        if (isAnyUpdate() == true) {
            update = "true";
        }
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "UPDATE `database`.`common` SET `update` = '" + update + "'";
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

    public void getInfo(String id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql;
            sql = "select * From tableta WHERE ID = '" + id + "'";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
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
                setUserGPU(rs.getString("nameGPU"));
                setBug(rs.getString("bug"));
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

    public void sendMessage(String messageContent, String anyMessage, int id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE `database`.`tableta` SET `message` = '" + messageContent + "'" +
                    ", `anyMessage` = '" + anyMessage + "' WHERE `tableta`.`ID` =" + id);

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

    public static void main(String[] args) {
        DataBase dataBase = new DataBase();
        try {
            dataBase.usersOnline();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getD() {
        return d;
    }

    public int getM() {
        return m;
    }

    public int getS() {
        return s;
    }

    public boolean isAnyMessage() {
        return anyMessage;
    }

    //----------SETTERS------------
    public void setAnyMessage(boolean anyMessage) {
        this.anyMessage = anyMessage;
    }
}


