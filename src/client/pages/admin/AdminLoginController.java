package client.pages.admin;

import client.helpers.PageHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorBlock;

    @FXML
    private Button backButton;

    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Button signinButton;

    @FXML
    void initialize() {
        signinButton.setOnAction(event -> {
            if (!loginInput.getCharacters().toString().equals("admin") || !passwordInput.getCharacters().toString().equals("admin")) {
                errorBlock.setText("Неверный ввод.");
                return;
            }
            signinButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("ProductList.fxml"));
        });
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });
    }
}
