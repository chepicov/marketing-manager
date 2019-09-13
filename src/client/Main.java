package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Client client;

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

}

