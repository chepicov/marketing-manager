package client.pages.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import client.Main;
import client.helpers.PageHelper;
import entities.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class CategoryListController {
    protected ObservableList<Category> categories = FXCollections.observableArrayList();
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
    private Button addCategoryButton;

    @FXML
    private TableColumn<Category, String> idColumn;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, String> nameColumn;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        setLinks();

        addCategoryButton.setOnAction(event -> {
            Main.setActiveCategory("");
            addCategoryButton.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("Category.fxml"));
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));

        if (!(categories.isEmpty())) {
            categories.clear();
            categoryTable.setItems(categories);
        }
        String message = "getCategories";
        String answer = Main.getClient().sendMessage(message);
        System.out.println(answer);
        String[] tableData = answer.split("\n");

        for (int i = 0; i < tableData.length; i++) {
            getCategory(tableData[i]);
        }
        if (categories.isEmpty()) {
            return;
        }
        categoryTable.setItems(categories);
        categoryTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Category item = categoryTable.getSelectionModel().getSelectedItem();
                if (item == null || item.getName() == "") {
                    return;
                }
                Main.setActiveCategory(item.getName());
                System.out.println(item.getName());
                categoryTable.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Category.fxml"));
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


    private void getCategory(String tableData) {

        String[] data = tableData.split(Pattern.quote(";"));
        int i = 0;

        Category category = new Category();
        category.setId(data[i++]);
        category.setName(data[i++]);

        categories.add(category);
    }
}
