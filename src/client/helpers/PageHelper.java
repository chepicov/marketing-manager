package client.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PageHelper {
    public static void onPageChange(URL url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
    }
}
