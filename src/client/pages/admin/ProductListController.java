package client.pages.admin;

import java.net.URL;
import java.util.ResourceBundle;

import client.helpers.PageHelper;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button productLink;

    @FXML
    private Button categoryLink;

    @FXML
    private Button adsLink;

    @FXML
    private Button orderLink;

    @FXML
    private Button addProductButton;

    @FXML
    private TableView<Product> productListTable;

    @FXML
    private TableColumn<Product, String> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, String> priceColumn;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });
        productLink.setOnAction(event -> {
            productLink.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("ProductList.fxml"));
        });

        categoryLink.setOnAction(event -> {
            productLink.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("CategoryList.fxml"));
        });

        adsLink.setOnAction(event -> {
            adsLink.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("AdsList.fxml"));
        });

        orderLink.setOnAction(event -> {
            adsLink.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("OrderList.fxml"));
        });

        addProductButton.setOnAction(event -> {
            Product product = new Product("1", "product", "category", 1.2, "");
            // set active product
            productListTable.getItems().addAll(product);
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
    }
}
