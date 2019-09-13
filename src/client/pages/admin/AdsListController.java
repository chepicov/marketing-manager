package client.pages.admin;

import client.Main;
import client.helpers.PageHelper;
import client.helpers.Utils;
import entities.Ads;
import entities.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdsListController {
    protected ObservableList<Ads> ads = FXCollections.observableArrayList();
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
        setLinks();
        if (!(ads.isEmpty())) {
            ads.clear();
            adsListTable.setItems(ads);
        }
        String message = "getAllAds";
        String answer = Main.getClient().sendMessage(message);
        String[] tableData = answer.split("\n");

        for (int i = 0; i < tableData.length; i++) {
            getAds(tableData[i]);
        }
        if (ads.isEmpty()) {
            return;
        }
        adsListTable.setItems(ads);

        addAdsButton.setOnAction(event -> {
            Main.setActiveAds(null);
            addAdsButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Ads.fxml"));
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("product"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("cpc"));
        clicksColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("clicks"));
        viewsColumn.setCellValueFactory(new PropertyValueFactory<Ads, String>("views"));
        ctrColumn.setCellValueFactory(c-> new SimpleStringProperty(
            Utils.getCTR(c.getValue().getClicks(), c.getValue().getViews())
        ));
        adsListTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Ads item = adsListTable.getSelectionModel().getSelectedItem();
                if (item == null) {
                    return;
                }
                Main.setActiveAds(item.getProduct());
                adsListTable.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Ads.fxml"));
            }
        });
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



    private void getAds(String tableData) {

        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;
        System.out.println(data);
        Ads adsItem = new Ads();
        adsItem.setId(data[i++]);
        adsItem.setProduct(data[i++]);
        adsItem.setHeader(data[i++]);
        adsItem.setText(data[i++]);
        adsItem.setCpc(Double.parseDouble(data[i++]));
        adsItem.setPhone(data[i++]);
        adsItem.setCompany(data[i++]);
        ads.add(adsItem);
    }
}
