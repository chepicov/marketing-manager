package client.pages.shop;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Main;
import client.helpers.PageHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button signinButton;

    @FXML
    private Button signupButton;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            signinButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Product.fxml"));
        });
        signinButton.setOnAction(event -> {
            String login = loginInput.getText();
            String password = passwordInput.getText();
            String message = "signin;" + login + ";" + password;
            String answer = Main.getClient().sendMessage(message);
            if (answer.equals("null")) {
                System.out.println("Error");
                return;
            }
            setUserId(answer);
            signinButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Product.fxml"));
        });

        signupButton.setOnAction(event -> {
            signupButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Signup.fxml"));
        });
    }

    private void setUserId(String tableData) {
        String[] data = tableData.split(Pattern.quote(";"));
        System.out.println(data);
        Main.setLogin(data[1]);
    }
}
