package client.pages.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Client;
import client.Main;
import client.helpers.PageHelper;
import client.helpers.Utils;
import entities.Category;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductController {
    protected ObservableList<Category> categories = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label headerLabel;

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
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextArea descField;

    @FXML
    private ChoiceBox<String> categoryField;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorBlock;

    @FXML
    private Button cancelButton;

    @FXML
    void initialize() {
        setLinks();

        String productName = Main.getActiveProduct();
        if (productName != null && !productName.equals("")) {
            headerLabel.setText("Редактировать товар");
            String message = "getProductByName;" + productName;
            String answer = Main.getClient().sendMessage(message);
            System.out.println(answer);
            String tableData = answer;
            Product product = getProduct(tableData);
            nameField.setText(product.getName());
            descField.setText(product.getDescription());
            priceField.setText(String.valueOf(product.getPrice()));
        }
        String message = "getCategories";
        String answer = Main.getClient().sendMessage(message);
        System.out.println(answer);
        String[] tableData = answer.split("\n");

        for (int i = 0; i < tableData.length; i++) {
            getCategory(tableData[i]);
        }
        cancelButton.setOnAction(event -> {
            // delete product
            cancelButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("ProductList.fxml"));
        });
        saveButton.setOnAction(event -> {
            String name = nameField.getCharacters().toString();
            if (name == null || name.equals("")) {
                errorBlock.setText("Введите название.");
                return;
            }
            String category = categoryField.getValue();
            if (category == null || category.equals("")) {
                errorBlock.setText("Выберите категорию.");
                return;
            }
            categories.forEach(s -> {
                if (category.equals(s.getName())) {
                    System.out.println(s.getId());
                }
            });
            String priceStr = priceField.getCharacters().toString();
            if (!Utils.isDigitDouble(priceStr)) {
                errorBlock.setText("Неверный формат цены.");
                return;
            }
            double price;
            price = Double.parseDouble(priceStr);
            String desc = descField.getText();
            System.out.println(name + " " + category + " " + price + " " + desc);
            String messageBody = name + ";" + category + ";" + desc + ";" + price;
            if (productName != null && !productName.equals("")) {
                // update
                Client client = Main.getClient();
                String newMessage = "updateProduct;" + messageBody;
                client.sendMessage(newMessage);
            } else {
                Client client = Main.getClient();
                String newMessage = "addProduct;" + messageBody;
                client.sendMessage(newMessage);
                // add
            }

            saveButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("ProductList.fxml"));
        });
        categories.forEach(category -> {
            categoryField.getItems().addAll(category.getName());
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


    private Product getProduct(String tableData) {
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

        return product;
    }

    private void getCategory(String tableData) {

        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;

        Category category = new Category();
        category.setId(data[i++]);
        category.setName(data[i++]);

        categories.add(category);
    }
}
