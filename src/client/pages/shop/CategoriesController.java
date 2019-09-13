package client.pages.shop;

import java.net.URL;
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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class CategoriesController {
    protected ObservableList<Category> categories = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> categoryList;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });
        if (!(categories.isEmpty())) {
            categories.clear();
            ObservableList<String> categoryNames = FXCollections.observableArrayList();
            categories.forEach(s -> {
                categoryNames.addAll(s.getName());
            });
            categoryList.setItems(categoryNames);
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
        ObservableList<String> categoryNames = FXCollections.observableArrayList();
        categories.forEach(s -> {
            categoryNames.addAll(s.getName());
        });
        categoryList.setItems(categoryNames);
        categoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String item = categoryList.getSelectionModel().getSelectedItem();
                if (item.equals("") || item == null) {
                    return;
                }
                categories.forEach(s -> {
                    if (item.equals(s.getName())) {
                        Main.setActiveCategory(s.getName());
                        System.out.println(s.getName());
                    }
                });

                categoryList.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Products.fxml"));
            }
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
