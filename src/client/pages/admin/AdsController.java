package client.pages.admin;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Client;
import client.Main;
import client.helpers.PageHelper;
import client.helpers.Utils;
import entities.Ads;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdsController {
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
    private Button exit;

    @FXML
    private TextField headerField;

    @FXML
    private TextField priceField;

    @FXML
    private TextArea textField;

    @FXML
    private ChoiceBox<String> productField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label headerLabel;

    @FXML
    private Label errorBlock;

    @FXML
    void initialize() {
        setLinks();
        String activeProduct = Main.getActiveAds();
        System.out.println(2 + activeProduct);
        if (activeProduct != null && !activeProduct.equals("")) {
            headerLabel.setText("Редактировать объявление");
            String message = "getAdsByProduct;" + activeProduct;
            String answer = Main.getClient().sendMessage(message);
            System.out.println(answer);
            String tableData = answer;
            Ads ads = getAds(tableData);
            productField.setValue(activeProduct);
            headerField.setText(ads.getHeader());
            textField.setText(ads.getText());
            priceField.setText(String.valueOf(ads.getCpc()));
        }
        String message = "getAllProducts";
        String answer = Main.getClient().sendMessage(message);
        String[] tableData = answer.split("\n");
        System.out.println(tableData[0]);
        for (int i = 0; i < tableData.length; i++) {
            getProduct(tableData[i]);
        }
        if (products.isEmpty()) {
            return;
        }

        products.forEach(product -> {
            productField.getItems().addAll(product.getName());
        });

        cancelButton.setOnAction(event -> {
            // delete ads
            cancelButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("AdsList.fxml"));
        });
        saveButton.setOnAction(event -> {
            String product = productField.getValue();
            if (product == null || product.equals("")) {
                errorBlock.setText("Выберите целевой товар.");
                return;
            }
            products.forEach(s -> {
                if (product.equals(s.getName())) {
                    System.out.println(s.getId());
                }
            });
            String header = headerField.getCharacters().toString();
            if (header == null || header.equals("")) {
                errorBlock.setText("Введите заголовок объявления.");
                return;
            }
            String priceStr = priceField.getCharacters().toString();
            if (!Utils.isDigitDouble(priceStr)) {
                errorBlock.setText("Неверный формат цены.");
                return;
            }
            double price;
            price = Double.parseDouble(priceStr);
            String text = textField.getText();
            if (text == null || text.equals("")) {
                errorBlock.setText("Введите текст объявления.");
                return;
            }
            Client client = Main.getClient();
            String phone = "+375291234567";
            String company = "Shop No1";
            String newMessage = "addAds;" + product + ";" + header + ";" + text + ";" + phone + ";" + company + ";" + price;
            client.sendMessage(newMessage);
            saveButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("AdsList.fxml"));
        });

        productField.setOnAction(event -> {
            String product = productField.getValue();
            products.forEach(s -> {
                if (product.equals(s.getName())) {
                    headerField.setText("Купить " + product + "!");
                    textField.setText("Купить " + product + " Минск. Звоните!");
                }
            });
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

    private void getProduct(String tableData) {
        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;
        System.out.println("1 " + data[0]);
        Product product = new Product();
        product.setId(data[i++]);
        product.setName(data[i++]);
        product.setCategory(data[i++]);
        product.setPrice(Double.parseDouble(data[i++]));
        if (data.length == 5)
            product.setDescription(data[i++]);

        products.addAll(product);
    }


    private Ads getAds(String tableData) {
        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;
        Ads adsItem = new Ads();
        adsItem.setId(data[i++]);
        adsItem.setProduct(data[i++]);
        adsItem.setHeader(data[i++]);
        adsItem.setText(data[i++]);
        adsItem.setCpc(Double.parseDouble(data[i++]));
        adsItem.setPhone(data[i++]);
        adsItem.setCompany(data[i++]);
        return adsItem;
    }
}
