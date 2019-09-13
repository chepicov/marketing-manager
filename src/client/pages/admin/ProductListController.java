package client.pages.admin;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Main;
import client.helpers.PageHelper;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductListController {
    protected ObservableList<Product> products = FXCollections.observableArrayList();

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
        setLinks();

        if (!(products.isEmpty())) {
            products.clear();
            productListTable.setItems(products);
        }
        String message = "getAllProducts";
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
        productListTable.setItems(products);

        addProductButton.setOnAction(event -> {
            addProductButton.getScene().getWindow().hide();
            Main.setActiveProduct("");
            PageHelper.onPageChange(getClass().getResource("Product.fxml"));
        });
        productListTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Product item = productListTable.getSelectionModel().getSelectedItem();
                if (item == null) {
                    return;
                }
                products.forEach(s -> {
                    Main.setActiveProduct(s.getName());
                    System.out.println(s.getName());
                });
                productListTable.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Product.fxml"));
            }
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
    }

    private void setLinks() {
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
