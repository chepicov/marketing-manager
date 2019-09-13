package client.pages.search;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Main;
import client.helpers.PageHelper;
import entities.Ads;
import entities.Category;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SearchController {
    protected ObservableList<Ads> ads = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> adsList;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        adsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String item = adsList.getSelectionModel().getSelectedItem();
                if (item == "" || item == null) {
                    return;
                }
                ads.forEach(s -> {
                    if (item.split("\n")[0].equals(s.getHeader())) {
                        String message = "onAdsClick;" + s.getProduct();
                        String answer = Main.getClient().sendMessage(message);
                        Main.setActiveProduct(s.getProduct());
                        System.out.println(s.getProduct());
                    }
                });
                adsList.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("../shop/Product.fxml"));
            }
        });
        searchButton.setOnAction(event -> {
            if (!(ads.isEmpty())) {
                ads.clear();
                ObservableList<String> adsContent = FXCollections.observableArrayList();
                ads.forEach(s -> {
                    adsContent.addAll(s.show());
                });
                adsList.setItems(adsContent);
            }
            String message = "getAds;" + searchField.getCharacters().toString();
            String answer = Main.getClient().sendMessage(message);
            System.out.println(answer);
            String[] tableData = answer.split("\n");

            for (int i = 0; i < tableData.length; i++) {
                getAds(tableData[i]);
            }
            if (ads.isEmpty()) {
                return;
            }
            ObservableList<String> adsContent = FXCollections.observableArrayList();
            ads.forEach(s -> {
                adsContent.addAll(s.show());
            });
            adsList.setItems(adsContent);
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
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
