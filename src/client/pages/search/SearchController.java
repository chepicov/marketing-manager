package client.pages.search;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.helpers.PageHelper;
import entities.Ads;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SearchController {

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
        Ads ads1 = new Ads("1", "product", "Купить product в Минске!", "product по доступной цене. Доставка. Звоните!", 1, "+375291234567");
        Ads ads2 = new Ads("1", "product1", "Купить product1 в Минске!", "product1 по доступной цене. Доставка. Звоните!", 1, "+375291234567");
        ArrayList<Ads> adsArrayList = new ArrayList<Ads>();
        adsArrayList.add(ads1);
        adsArrayList.add(ads2);
        adsArrayList.add(ads2);
        adsArrayList.add(ads2);
        adsArrayList.forEach(s -> adsList.getItems().addAll(s.show()));
        adsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String item = adsList.getSelectionModel().getSelectedItem();
                if (item == "" || item == null) {
                    return;
                }
                adsArrayList.forEach(s -> {
                    if (item.split("\n")[0].contains(s.getHeader())) {
                        // set active product, inc clicks
                        System.out.println(s.getProduct());
                    }
                });
                adsList.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("../shop/Product.fxml"));
            }
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });
    }
}
