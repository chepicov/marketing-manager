package client.pages.admin;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Client;
import client.Main;
import client.helpers.PageHelper;
import entities.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CategoryController {

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
    private TextField nameField;

    @FXML
    private TextArea descField;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorBlock;

    @FXML
    private Button cancelButton;

    @FXML
    private Label headerLabel;

    @FXML
    void initialize() {
        setLinks();
        String categoryName = Main.getActiveCategory();
        if (categoryName != null && !categoryName.equals("")) {
            headerLabel.setText("Редактировать категорию");
            String message = "getCategoryByName;" + categoryName;
            String answer = Main.getClient().sendMessage(message);
            System.out.println(answer);
            String tableData = answer;

            Category category = getCategory(tableData);
            nameField.setText(category.getName());
            descField.setText(category.getDescription());
        }
        cancelButton.setOnAction(event -> {
            // delete category
            cancelButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("ProductList.fxml"));
        });
        saveButton.setOnAction(event -> {
            String name = nameField.getCharacters().toString();
            if (name == null || name.equals("")) {
                errorBlock.setText("Введите название.");
                return;
            }
            String description = descField.getText();
            if (description == null) {
                description = "";
            }
            Client client = Main.getClient();
            String message = "addCategory;" + name + ";" + description;
            client.sendMessage(message);
            saveButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("CategoryList.fxml"));
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

    private Category getCategory(String tableData) {

        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;

        Category category = new Category();
        category.setId(data[i++]);
        category.setName(data[i++]);

        return category;
    }
}
