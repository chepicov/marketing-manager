package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Client client;
    private static String activeCategory;
    private static String activeProduct;
    private static String activeAds;
    private static String login;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("pages/Menu.fxml"));
        primaryStage.setTitle("Маркетинг");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
        client = new Client();
    }
    @Override
    public void stop()
    {
        try
        {
            super.stop();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            client.close();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public static Client getClient()
    {
        return client;
    }

    public static String getActiveCategory() {
        return activeCategory;
    }

    public static String getActiveProduct() {
        return activeProduct;
    }

    public static String getActiveAds() {
        return activeAds;
    }

    public static void setActiveCategory(String activeCategory) {
        Main.activeCategory = activeCategory;
    }

    public static void setActiveProduct(String activeProduct) {
        Main.activeProduct = activeProduct;
    }

    public static void setActiveAds(String product) {
        Main.activeAds = product;
    }

    public static void setLogin(String login) {
        Main.login = login;
    }

    public static String getLogin() {
        return login;
    }
}

