package server;

import entities.*;
import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(String[] data) {

        String insert = "INSERT INTO " + Constants.USERS_TABLE + "(" + Constants.USERS_FIRSTNAME + "," +
                Constants.USERS_LASTNAME + "," + Constants.USERS_USERNAME + "," + Constants.USERS_PASSWORD + "," + Constants.USERS_ROLE
                + ")" + "VALUES(?,?,?,?,?)";


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            for (int i = 1; i <= data.length; i++) {
                prSt.setString(i, data[i - 1]);
            }
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Constants.USERS_TABLE + " WHERE " + Constants.USERS_USERNAME +
                "=? AND " + Constants.USERS_PASSWORD + "=? AND " + Constants.USERS_ROLE + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getRole());

            resultSet = prSt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String loginUser(String loginText, String loginPassword, String role) {

        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        user.setRole(role);

        ResultSet resultSet = dbHandler.getUser(user);

        int counter = 0;
        try {
            while (resultSet.next()) {
                counter++;
            }
            if (counter >= 1) {
                return "success";
            } else {
                return "unsuccess";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.toString();
    }

    public String orderSouvenirs(String id, String amount) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resultSet = dbHandler.OrderSouvenir(id, amount);
        int counter = 0;
        try {
            while (resultSet.next()) {
                counter++;
            }
            if (counter >= 1) {
                return "ordered";
            } else {
                return "notordered";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "FAIL";
    }

    public String orderPresents(String id, String amount) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resultSet = dbHandler.OrderPresent(id, amount);
        int counter = 0;
        try {
            while (resultSet.next()) {
                counter++;
            }
            if (counter >= 1) {
                return "orderedPresent";
            } else {
                return "notorderedPresent";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "FAIL";
    }

    public String orderKanc(String id, String amount) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resultSet = dbHandler.OrderKanc(id, amount);
        int counter = 0;
        try {
            while (resultSet.next()) {
                counter++;
            }
            if (counter >= 1) {
                return "orderedKanc";
            } else {
                return "notorderedKanc";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "FAIL";
    }

    public void MinusAmount(String id, String amount) {
        String change = " UPDATE " + " `souvenirs` SET kolichestvo = kolichestvo - " + amount + " WHERE name = '" + id + "'" +
                " AND kolichestvo - " + amount + " >= 0 ";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String delete = " DELETE FROM " + " `souvenirs` WHERE kolichestvo  = 0 ";
        try {
            PreparedStatement prSt1 = getDbConnection().prepareStatement(delete);
            prSt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void MinusAmountPresent(String id, String amount) {
        String change = " UPDATE " + " `presents` SET kolichestvo = kolichestvo - " + amount + " WHERE name = '" + id + "'" +
                " AND kolichestvo - " + amount + " >= 0 ";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String delete = " DELETE FROM " + " `presents` WHERE kolichestvo  = 0 ";
        try {
            PreparedStatement prSt1 = getDbConnection().prepareStatement(delete);
            prSt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void MinusAmountKanc(String id, String amount) {
        String change = " UPDATE " + " `kanctovari` SET kolichestvo = kolichestvo - " + amount + " WHERE name = '" + id + "'" +
                " AND kolichestvo - " + amount + " >= 0 ";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String delete = " DELETE FROM " + " `kanctovari` WHERE kolichestvo  = 0 ";
        try {
            PreparedStatement prSt1 = getDbConnection().prepareStatement(delete);
            prSt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet OrderSouvenir(String id, String amount) {
        String select = "SELECT * FROM `souvenirs` WHERE name = '" +
                id + "'" + " AND kolichestvo >= " + amount + " AND kolichestvo - " + amount + " >= 0 ";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        AddToOrders(id, amount);
        MinusAmount(id, amount);
        return Info;
    }

    public ResultSet OrderPresent(String id, String amount) {
        String select = "SELECT * FROM `presents` WHERE name = '" +
                id + "'" + " AND kolichestvo >= " + amount + " AND kolichestvo - " + amount + " >= 0 ";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        AddToOrdersPresent(id, amount);
        MinusAmountPresent(id, amount);
        return Info;
    }

    public ResultSet OrderKanc(String id, String amount) {
        String select = "SELECT * FROM `kanctovari` WHERE name = '" +
                id + "'" + " AND kolichestvo >= " + amount + " AND kolichestvo - " + amount + " >= 0 ";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        AddToOrdersKanc(id, amount);
        MinusAmountKanc(id, amount);
        return Info;
    }

    private void AddToOrders(String id, String amount) {
        String insert = "INSERT INTO " + Constants.SOUVENIRS_ORDER_TABLE + " (" + Constants.SOUVENIRS_ORDER_ID + "," + Constants.SOUVENIRS_ORDER_NAME + "," + Constants.SOUVENIRS_ORDER_BASICCOST + "," +
                Constants.SOUVENIRS_ORDER_SELLINGCOST + "," + Constants.SOUVENIRS_ORDER_NDS + "," + Constants.SOUVENIRS_ORDER_KOLICHESCTVO + ","
                + Constants.SOUVENIRS_ORDER_MATERIAL + ") " + "SELECT * FROM `souvenirs` WHERE name = '" +
                id + "'" + " AND kolichestvo >= " + amount + " AND kolichestvo - " + amount + " >= 0";
        String change = "UPDATE" + " `souvenirs_order` SET order_kolichestvo = '" + amount + "' WHERE order_name = '" + id + "'" + " AND order_kolichestvo >= " + amount + " AND order_kolichestvo - " + amount + " >= 0";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            PreparedStatement prSt1 = getDbConnection().prepareStatement(change);
            prSt.executeUpdate();
            prSt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void AddToOrdersPresent(String id, String amount){

            String insert = "INSERT INTO " + Constants.PRESENTS_ORDER_TABLE + " (" + Constants.PRESENTS_ORDER_ID + "," +
                    Constants.PRESENTS_ORDER_NAME + "," + Constants.PRESENTS_ORDER_BASICCOST + "," +
                    Constants.PRESENTS_ORDER_SELLINGCOST + "," + Constants.PRESENTS_ORDER_NDS + "," + Constants.PRESENTS_ORDER_KOLICHESCTVO + ","
                    + Constants.PRESENTS_ORDER_HOLIDAY + ") " + "SELECT * FROM `presents` WHERE name = '" +
                    id + "'" + " AND kolichestvo >= " + amount + " AND kolichestvo - " + amount + " >= 0";
            String change = "UPDATE" + " `presents_order` SET order_kolichestvo = '" + amount + "' WHERE order_name = '" + id + "'" + " AND order_kolichestvo >= " + amount + " AND order_kolichestvo - " + amount + " >= 0";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                PreparedStatement prSt1 = getDbConnection().prepareStatement(change);
                prSt.executeUpdate();
                prSt1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    private void AddToOrdersKanc(String id, String amount){

        String insert = "INSERT INTO " + Constants.KANCTOVARI_ORDER_TABLE + " (" + Constants.KANCTOVARI_ORDER_ID + "," +
                Constants.KANCTOVARI_ORDER_NAME + "," + Constants.KANCTOVARI_ORDER_BASICCOST + "," +
                Constants.KANCTOVARI_ORDER_SELLINGCOST + "," + Constants.KANCTOVARI_ORDER_NDS + "," + Constants.KANCTOVARI_ORDER_KOLICHESCTVO + ","
                + Constants.KANCTOVARI_ORDER_PROIZVODITEL + ") " + "SELECT * FROM `kanctovari` WHERE name = '" +
                id + "'" + " AND kolichestvo >= " + amount + " AND kolichestvo - " + amount + " >= 0";
        String change = "UPDATE" + " `kanctovari_order` SET order_kolichestvo = '" + amount + "' WHERE order_name = '" + id + "'" + " AND order_kolichestvo >= " + amount + " AND order_kolichestvo - " + amount + " >= 0";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            PreparedStatement prSt1 = getDbConnection().prepareStatement(change);
            prSt.executeUpdate();
            prSt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String tableUpdateUsers() {

        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.UpdateTableUsers();

        String message = "";
        try {
            while (result.next()) {
                message += result.getString(1) + "|";
                message += result.getString(2) + "|";
                message += result.getString(3) + "|";
                message += result.getString(4) + "|";
                message += result.getString(5) + "|";
                message += result.getString(6) + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public ResultSet UpdateTableUsers() {
        String update = "SELECT * FROM magaziner.users";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }

    public ResultSet UpdateTableSouvenirs() {
        String update = "SELECT * FROM magaziner.souvenirs";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }

    public ResultSet UpdateTablePresents() {
        String update = "SELECT * FROM magaziner.presents";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }
    public ResultSet UpdateTableKanc() {
        String update = "SELECT * FROM magaziner.kanctovari";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }
    public ResultSet UpdateTableSouvenirsOrder() {

        String update = "SELECT * FROM magaziner.souvenirs_order";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }

    public ResultSet UpdateTablePresentsOrder() {

        String update = "SELECT * FROM magaziner.presents_order";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }

    public ResultSet UpdateTableKancOrder() {

        String update = "SELECT * FROM magaziner.kanctovari_order";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }

    public void changeInfoUsers(String[] data) {

        final DatabaseHandler dbHandler = new DatabaseHandler();

        dbHandler.ChangeUsersName(data[0], data[1]);
        dbHandler.ChangeUsersSurname(data[0], data[2]);
        dbHandler.ChangeUsersLogin(data[0], data[3]);
        dbHandler.ChangeUsersPassword(data[0], data[4]);

    }

    public void ChangeUsersName(String id, String name) {
        String change = "UPDATE" + " `users` SET firstname =? WHERE " + Constants.USERS_ID + "  = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, name);
            prSt.setString(2, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ChangeUsersSurname(String id, String surname) {
        String change = "UPDATE" + " `users` SET lastname =? WHERE " + Constants.USERS_ID + "  = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, surname);
            prSt.setString(2, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ChangeUsersLogin(String id, String login) {
        String change = "UPDATE" + " `users` SET username =? WHERE " + Constants.USERS_ID + "  = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, login);
            prSt.setString(2, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ChangeUsersPassword(String id, String password) {
        String change = "UPDATE" + " `users` SET password =? WHERE " + Constants.USERS_ID + "  = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(change);
            prSt.setString(1, password);
            prSt.setString(2, id);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteInfoUsers(String[] data) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.DeleteUser(data[0]);
    }

    public void deleteInfoSouvenirs(String[] data) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.DeleteSouvenir(data[0]);
    }

    public void deleteInfoPresents(String[] data) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.DeletePresent(data[0]);
    }

    public void deleteInfoKanc(String[] data) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.DeleteKanc(data[0]);
    }

    public void deleteInfoSouvenirsOrder(String[] data) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.DeleteSouvenirOrder(data[0]);
    }

    public void deleteInfoPresentsOrder(String[] data) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.DeletePresentOrder(data[0]);
    }

    public void deleteInfoKancOrder(String[] data) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.DeleteKancOrder(data[0]);
    }
    private void DeleteUser(String data) {
        String delete = "DELETE FROM " + " `users` WHERE " + Constants.USERS_ID + "  = ? AND " + Constants.USERS_ROLE + " = 'user'";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, data);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void DeleteSouvenir(String data) {
        String delete = "DELETE FROM " + " `souvenirs` WHERE " + Constants.SOUVENIRS_ID + "  = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, data);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void DeletePresent(String data) {
        String delete = "DELETE FROM " + " `presents` WHERE " + Constants.PRESENTS_ID + "  = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, data);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void DeleteKanc(String data) {
        String delete = "DELETE FROM " + " `kanctovari` WHERE " + Constants.KANCTOVARI_ID+ "  = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, data);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void DeleteSouvenirOrder(String data) {
        String delete = "DELETE FROM " + " `souvenirs_order` WHERE " + Constants.SOUVENIRS_ORDER_ID + "  = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, data);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void DeletePresentOrder(String data) {
        String delete = "DELETE FROM " + " `presents_order` WHERE " + Constants.PRESENTS_ORDER_ID + "  = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, data);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void DeleteKancOrder(String data) {
        String delete = "DELETE FROM " + " `kanctovari_order` WHERE " + Constants.KANCTOVARI_ORDER_ID + "  = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, data);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String tableUpdateSouvenirs() {

        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.UpdateTableSouvenirs();
        String message = "";
        try {
            while (result.next()) {
                message += result.getString(1) + "|";
                message += result.getString(2) + "|";
                message += result.getString(3) + "|";
                message += result.getString(4) + "|";
                message += result.getString(5) + "|";
                message += result.getString(6) + "|";
                message += result.getString(7) + "\n";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
    public String tableUpdatePresents() {

        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.UpdateTablePresents();
        String message = "";
        try {
            while (result.next()) {
                message += result.getString(1) + "|";
                message += result.getString(2) + "|";
                message += result.getString(3) + "|";
                message += result.getString(4) + "|";
                message += result.getString(5) + "|";
                message += result.getString(6) + "|";
                message += result.getString(7) + "\n";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String tableUpdateKanc() {

        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.UpdateTableKanc();
        String message = "";
        try {
            while (result.next()) {
                message += result.getString(1) + "|";
                message += result.getString(2) + "|";
                message += result.getString(3) + "|";
                message += result.getString(4) + "|";
                message += result.getString(5) + "|";
                message += result.getString(6) + "|";
                message += result.getString(7) + "\n";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String tableUpdateSouvenirsOrder() {

        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.UpdateTableSouvenirsOrder();

        String message = "";
        try {
            while (result.next()) {
                message += result.getString(1) + "|";
                message += result.getString(2) + "|";
                message += result.getString(3) + "|";
                message += result.getString(4) + "|";
                message += result.getString(5) + "|";
                message += result.getString(6) + "|";
                message += result.getString(7) + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String tableUpdatePresentsOrder() {

        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.UpdateTablePresentsOrder();

        String message = "";
        try {
            while (result.next()) {
                message += result.getString(1) + "|";
                message += result.getString(2) + "|";
                message += result.getString(3) + "|";
                message += result.getString(4) + "|";
                message += result.getString(5) + "|";
                message += result.getString(6) + "|";
                message += result.getString(7) + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String tableUpdateKancOrder() {

        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.UpdateTableKancOrder();

        String message = "";
        try {
            while (result.next()) {
                message += result.getString(1) + "|";
                message += result.getString(2) + "|";
                message += result.getString(3) + "|";
                message += result.getString(4) + "|";
                message += result.getString(5) + "|";
                message += result.getString(6) + "|";
                message += result.getString(7) + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
    public String refreshGraphicsSouvenirs() {
        String message = "";
        String query = "SELECT " + Constants.SOUVENIRS_ORDER_KOLICHESCTVO + ","  + Constants.SOUVENIRS_ORDER_NAME +
                " FROM " + Constants.SOUVENIRS_ORDER_TABLE;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                int orderKol = rs.getInt("order_kolichestvo");
                message +=  rs.getString("order_name") + "|" + Integer.toString(orderKol) + "\n";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String refreshGraphicsPresents() {
        String message = "";
        String query = "SELECT " + Constants.PRESENTS_ORDER_KOLICHESCTVO + ","  + Constants.PRESENTS_ORDER_NAME +
                " FROM " + Constants.PRESENTS_ORDER_TABLE;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                int orderKol = rs.getInt("order_kolichestvo");
                message +=  rs.getString("order_name") + "|" + Integer.toString(orderKol) + "\n";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String refreshGraphicsKanc() {
        String message = "";
        String query = "SELECT " + Constants.KANCTOVARI_ORDER_KOLICHESCTVO + ","  + Constants.KANCTOVARI_ORDER_NAME +
                " FROM " + Constants.KANCTOVARI_ORDER_TABLE;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next())
            {
                int orderKol = rs.getInt("order_kolichestvo");
                message +=  rs.getString("order_name") + "|" + Integer.toString(orderKol) + "\n";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void addSouvenir(String[] data) {

        String insert = "INSERT INTO " + Constants.SOUVENIRS_TABLE + "(" + Constants.SOUVENIRS_NAME + "," + Constants.SOUVENIRS_BASICCOST + "," +
                Constants.SOUVENIRS_SELLINGCOST + "," + Constants.SOUVENIRS_NDS + "," + Constants.SOUVENIRS_KOLICHESCTVO + ","
                + Constants.SOUVENIRS_MATERIAL + ")" + "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            for (int i = 1; i <= data.length; i++) {
                prSt.setString(i, data[i - 1]);
            }
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addPresent(String[] data) {

        String insert = "INSERT INTO " + Constants.PRESENTS_TABLE + "(" + Constants.PRESENTS_NAME + "," + Constants.PRESENTS_BASICCOST + "," +
                Constants.PRESENTS_SELLINGCOST + "," + Constants.PRESENTS_NDS + "," + Constants.PRESENTS_KOLICHESCTVO + ","
                + Constants.PRESENTS_HOLIDAY + ")" + "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            for (int i = 1; i <= data.length; i++) {
                prSt.setString(i, data[i - 1]);
            }
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

        public void addKanc (String[] data) {

            String insert = "INSERT INTO " + Constants.KANCTOVARI_TABLE + "(" + Constants.KANCTOVARI_NAME + "," + Constants.KANCTOVARI_BASICCOST + "," +
                    Constants.KANCTOVARI_SELLINGCOST + "," + Constants.KANCTOVARI_NDS + "," + Constants.KANCTOVARI_KOLICHESCTVO + ","
                    + Constants.KANCTOVARI_PROIZVODITEL + ")" + "VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                for (int i = 1; i <= data.length; i++) {
                    prSt.setString(i, data[i - 1]);
                }
                prSt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

}
