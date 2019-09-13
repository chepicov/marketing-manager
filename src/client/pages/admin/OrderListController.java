package client.pages.admin;

import java.net.URL;
import java.util.ResourceBundle;

import client.helpers.PageHelper;
import entities.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderListController {

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
    private TableView<Order> orderListTable;

    @FXML
    private TableColumn<Order, String> idColumn;

    @FXML
    private TableColumn<Order, String> userColumn;

    @FXML
    private TableColumn<Order, String> productColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

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


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("user"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("product"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
    }
}
