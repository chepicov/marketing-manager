package client.pages.shop;

import client.helpers.PageHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private PasswordField passwordConfirmInput;

    @FXML
    private Button signupButton;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    void initialize() {
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Login.fxml"));
        });

        signupButton.setOnAction(event -> {
            signupButton.getScene().getWindow().hide();
            // signup
            PageHelper.onPageChange(getClass().getResource("Product.fxml"));
        });
    }
}
