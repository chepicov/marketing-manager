package client.pages.admin;

import client.helpers.PageHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
            signinButton.getScene().getWindow().hide();
            // signin
            PageHelper.onPageChange(getClass().getResource("ProductList.fxml"));
        });
    }
}
