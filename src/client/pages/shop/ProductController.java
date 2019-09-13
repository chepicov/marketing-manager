package client.pages.shop;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Client;
import client.Main;
import client.helpers.PageHelper;
import entities.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label productLabel;

    @FXML
    private Button backButton;

    @FXML
    private Text descBlock;

    @FXML
    private Label priceLabel;

    @FXML
    private Button buyButton;

    @FXML
    private Label productNameLabel;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        String productName = Main.getActiveProduct();
        String message = "getProductByName;" + productName;
        String answer = Main.getClient().sendMessage(message);
        System.out.println(answer);
        String tableData = answer;
        Product product = getProduct(tableData);
        Main.setActiveCategory(product.getCategory());
        productLabel.setText(product.getName());
        descBlock.setText(product.getDescription());
        priceLabel.setText(product.getPrice() + " BYN");
        productNameLabel.setText(product.getName());
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Products.fxml"));
        });
        buyButton.setOnAction(event -> {
            if (Main.getLogin() == null || Main.getLogin().equals("")) {
                buyButton.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Login.fxml"));
                return;
            }
            Client client = Main.getClient();
            String newMessage = "addOrder;" + product.getName() + ";" + Main.getLogin() + ";" + product.getPrice();
            client.sendMessage(newMessage);
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
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
}
