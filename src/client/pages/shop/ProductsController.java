package client.pages.shop;

import client.Main;
import client.helpers.PageHelper;
import entities.Category;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ProductsController {
    protected ObservableList<Product> products = FXCollections.observableArrayList();

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
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Categories.fxml"));
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });


        String categoryName = Main.getActiveCategory();
        category.setText(categoryName);
        if (!(products.isEmpty())) {
            products.clear();
            ObservableList<String> productItems = FXCollections.observableArrayList();
            products.forEach(s -> {
                productItems.addAll(s.show());
            });
            productList.setItems(productItems);
        }
        String message = "getProducts;" + categoryName;
        String answer = Main.getClient().sendMessage(message);
        System.out.println(answer);
        String[] tableData = answer.split("\n");
        System.out.println(tableData[0]);
        if (tableData[0].equals("null")) {
            return;
        }
        for (int i = 0; i < tableData.length; i++) {
            getProduct(tableData[i]);
        }
        if (products.isEmpty()) {
            return;
        }
        ObservableList<String> productItems = FXCollections.observableArrayList();
        products.forEach(s -> {
            productItems.addAll(s.show());
        });
        productList.setItems(productItems);
        productList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String item = productList.getSelectionModel().getSelectedItem();
                if (item == null || item == "") {
                    return;
                }
                products.forEach(s -> {
                    if (item.split("\n")[0].contains(s.getName())) {
                        Main.setActiveProduct(s.getName());
                        System.out.println(s.getName());
                    }
                });
                productList.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Product.fxml"));
            }
        });
    }



    private void getProduct(String tableData) {

        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;
        System.out.println(data);
        Product product = new Product();
        product.setId(data[i++]);
        product.setName(data[i++]);
        product.setCategory(data[i++]);
        product.setPrice(Double.parseDouble(data[i++]));
        if (data.length == 5)
            product.setDescription(data[i++]);

        products.add(product);
    }
}
