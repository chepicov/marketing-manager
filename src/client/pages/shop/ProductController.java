package client.pages.shop;

import java.net.URL;
import java.util.ResourceBundle;

import client.helpers.PageHelper;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label productLabel;

    @FXML
    private Button backButton;

    @FXML
    private Text descBlock;

    @FXML
    private Label priceLabel;

    @FXML
    private Button buyButton;

    @FXML
    private Label productNameLabel;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        Product product = new Product("1", "product", "category", 1.2, "");
        productLabel.setText(product.getName());
        descBlock.setText(product.getDesc());
        priceLabel.setText(product.getPrice() + " BYN");
        productNameLabel.setText(product.getName());
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Products.fxml"));
        });
        buyButton.setOnAction(event -> {
            buyButton.getScene().getWindow().hide();
            // add order
            PageHelper.onPageChange(getClass().getResource("Login.fxml"));
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });
    }
}
