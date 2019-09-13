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


    public String signin(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.signinUser(data[0], data[1]);
        return getEntities(result, 5);
    }

    public void signup(String data[]) {
        System.out.println(1 + data[0]);
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.signupUser(data);
    }

    public void addOrder(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.addOrderItem(data);
    }

    public ResultSet signinUser(String login, String password) {
        String update = "SELECT * FROM test.users where login = '" + login + "' AND password = '" + password + "'";
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

    public void signupUser(String[] data) {
        String insert = "INSERT INTO users (login,password,name,phone)VALUES (?,?,?,?)";
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

    public void addCategory(String[] data) {
        String insert = "INSERT INTO categories (name,description)VALUES (?,?)";
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

    public void addOrderItem(String[] data) {
        String insert = "INSERT INTO orders (product,user,price,status)VALUES (?,?,?,'active')";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            for (int i = 1; i <= data.length - 1; i++) {
                prSt.setString(i, data[i - 1]);
            }
            prSt.setDouble(data.length, Double.parseDouble(data[data.length - 1]));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addAds(String[] data) {
        String insert = "INSERT INTO ads (product,header,text,phone,company,cpc,views,clicks)VALUES (?,?,?,?,?,?,0,0)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            for (int i = 1; i <= data.length - 1; i++) {
                prSt.setString(i, data[i - 1]);
            }
            prSt.setDouble(data.length, Double.parseDouble(data[data.length - 1]));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String[] data) {
        String insert = "INSERT INTO products (name,category,description,price)VALUES (?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            for (int i = 1; i <= data.length - 1; i++) {
                prSt.setString(i, data[i - 1]);
            }
            prSt.setDouble(data.length, Double.parseDouble(data[data.length - 1]));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getCategories() {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getCategoryItems();
        return getEntities(result, 3);
    }

    public String getOrders() {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getOrderItems();
        return getEntities(result, 6);
    }

    public String getEntities(ResultSet result, int n) {
        String message = "";
        try {
            while (result.next()) {
                for (int i = 1; i < n - 1; i++) {
                    System.out.println(result.getString(i));
                    message += result.getString(i) + ";";
                }
                message += result.getString(n - 1) + "\n";

            }
            System.out.println(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
    public ResultSet getCategoryItems() {
        String update = "SELECT id, name, description FROM test.categories";
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


    public String getAllProducts() {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAllProductItems();
        return getEntities(result, 5);
    }

    public String getProducts(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getProductItems(data[0]);
        return getEntities(result, 5);
    }

    public String getAdsByProduct(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAdsItemByProduct(data[0]);
        return getEntities(result, 7);
    }

    public String getProductByName(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getProductItemByName(data[0]);
        return getEntities(result, 5);
    }

    public String getCategoryByName(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getCategoryItemByName(data[0]);
        return getEntities(result, 3);
    }

    public ResultSet getAllProductItems() {
        String update = "SELECT id, name, category, price, description FROM test.products";
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

    public ResultSet getProductItems(String category) {
        String update = "SELECT id, name, category, price, description FROM test.products where category = '" + category + "'";
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

    public ResultSet getOrderItems() {
        String update = "SELECT * FROM test.orders";
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


    public ResultSet getCategoryItemByName(String name) {
        String update = "SELECT id, name, description FROM test.categories where name = '" + name + "'";
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

    public ResultSet getProductItemByName(String name) {
        String update = "SELECT id, name, category, price, description FROM test.products where name = '" + name + "'";
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

    public ResultSet getAdsItemByProduct(String product) {
        String update = "SELECT id, product, header, text, cpc, phone, company FROM test.ads where id = '" + product + "'";
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

    public String getAds(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAdsItems(data.length > 0 ? data[0] : "");
        return getEntities(result, 8);
    }

    public void onAdsClick(String data[]) {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.addClick(data[0]);
    }


    public String getAllAds() {
        final DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet result = dbHandler.getAllAdsItems();
        return getEntities(result, 8);
    }

    public ResultSet getAllAdsItems() {
        String update = "SELECT id, product, header, text, cpc, phone, company FROM test.ads";
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

    public ResultSet getAdsItems(String product) {
        String update = "SELECT id, product, header, text, cpc, phone, company FROM test.ads where product like '%" + product + "%'";
        ResultSet Info = null;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            Info = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String update1 = "UPDATE test.ads \n" +
                "  SET views = views + 1 \n" +
                "  WHERE product like '%" + product + "%'";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update1);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Info;
    }

    public void addClick(String product) {
        String update = "UPDATE test.ads \n" +
                "  SET clicks = clicks + 1 \n" +
                "  WHERE product like '" + product + "'";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
