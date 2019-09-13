package client.pages.shop;

import java.net.URL;
import java.util.ResourceBundle;

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
            signinButton.getScene().getWindow().hide();
            // signin
            PageHelper.onPageChange(getClass().getResource("Product.fxml"));
        });

        signupButton.setOnAction(event -> {
            signupButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Signup.fxml"));
        });
    }
}
