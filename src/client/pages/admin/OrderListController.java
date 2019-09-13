package client.pages.admin;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Main;
import client.helpers.PageHelper;
import entities.Order;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderListController {
    protected ObservableList<Order> orders = FXCollections.observableArrayList();

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
        setLinks();

        if (!(orders.isEmpty())) {
            orders.clear();
            orderListTable.setItems(orders);
        }
        String message = "getOrders";
        String answer = Main.getClient().sendMessage(message);
        String[] tableData = answer.split("\n");
        if (tableData[0].equals("null")) {
            return;
        }
        for (int i = 0; i < tableData.length; i++) {
            getOrder(tableData[i]);
        }
        if (orders.isEmpty()) {
            return;
        }
        orderListTable.setItems(orders);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("user"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("product"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
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


    private void getOrder(String tableData) {
        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;
        Order order = new Order();
        order.setId(data[i++]);
        order.setProduct(data[i++]);
        order.setUser(data[i++]);
        order.setPrice(Double.parseDouble(data[i++]));
        if (data.length > i) {
            order.setStatus(data[i++]);
        }

        orders.addAll(order);
    }
}