package client.pages.shop;

import client.helpers.PageHelper;
import entities.Product;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label category;

    @FXML
    private ListView<String> productList;

    @FXML
    private Button backButton;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        category.setText("Category1");
        Product product = new Product("1", "product", "category", 1.2, "");
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        productArrayList.add(product);
        productArrayList.forEach(s -> productList.getItems().addAll(s.show()));
        productList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String item = productList.getSelectionModel().getSelectedItem();
                if (item == "" || item == null) {
                    return;
                }
                productArrayList.forEach(s -> {
                    if (item.split("\n")[0].contains(s.getName())) {
                        // set active product
                        System.out.println(s.getName());
                    }
                });
                productList.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Product.fxml"));
            }
        });
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Categories.fxml"));
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });
    }
}
