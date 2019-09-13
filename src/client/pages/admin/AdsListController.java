package client.pages.admin;

import client.helpers.PageHelper;
import client.helpers.Utils;
import entities.Ads;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdsListController {
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
    private Button addAdsButton;

    @FXML
    private TableView<Ads> adsListTable;

    @FXML
    private TableColumn<Ads, String> idColumn;

    @FXML
    private TableColumn<Ads, String> productColumn;

    @FXML
    private TableColumn<Ads, String> priceColumn;

    @FXML
    private TableColumn<Ads, String> clicksColumn;

    @FXML
    private TableColumn<Ads, String> viewsColumn;

    @FXML
    private TableColumn<Ads, String> ctrColumn;

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

        addAdsButton.setOnAction(event -> {
            Ads product = new Ads("1", "product", "header", "text", 1, "+375291234567");
            adsListTable.getItems().addAll(product);
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("product"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("cpc"));
        clicksColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("clicks"));
        viewsColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("views"));
        ctrColumn.setCellValueFactory(c-> new SimpleStringProperty(
            Utils.getCTR(c.getValue().getClicks(), c.getValue().getViews())
        ));
    }
}
