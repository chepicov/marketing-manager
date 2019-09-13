package client.pages;

import java.net.URL;
import java.util.ResourceBundle;

import client.helpers.PageHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Menu {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button searchLink;

    @FXML
    private Button shopLink;

    @FXML
    private Button adminLink;

    @FXML
    void initialize() {
        searchLink.setOnAction(event -> {
            searchLink.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("search/Search.fxml"));
        });

        shopLink.setOnAction(event -> {
            shopLink.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("shop/Categories.fxml"));
        });

        adminLink.setOnAction(event -> {
            adminLink.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("admin/AdminLogin.fxml"));
        });

    }
}
