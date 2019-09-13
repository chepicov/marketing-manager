package client.pages.shop;

import client.Main;
import client.helpers.PageHelper;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button signupButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordConfirmField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField nameField;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Login.fxml"));
        });

        signupButton.setOnAction(event -> {
            if (!passwordField.getText().equals(passwordConfirmField.getText())) {
                return;
            }
            String name = nameField.getText();
            String login = loginField.getText();
            String password = passwordField.getText();
            String phone = phoneField.getText();
            System.out.println("signup;" + login + ";" + password + ";" + name + ";" + phone);
            String message = "signup;" + login + ";" + password + ";" + name + ";" + phone;
            Main.getClient().sendMessage(message);
            setUserId(login);
            signupButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Product.fxml"));
        });
    }


    private void setUserId(String tableData) {
        String[] data = tableData.split(Pattern.quote(";"));
        System.out.println(data);
        Main.setLogin(data[0]);
    }
}
