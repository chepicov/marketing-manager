package client.pages.shop;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.helpers.PageHelper;
import entities.Category;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class CategoriesController {

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
        Category category = new Category("1", "category1");
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        categoryArrayList.add(category);
        categoryArrayList.forEach(s -> categoryList.getItems().addAll(s.getName()));
        categoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String item = categoryList.getSelectionModel().getSelectedItem();
                if (item == "" || item == null) {
                    return;
                }
                categoryArrayList.forEach(s -> {
                    if (item == s.getName()) {
                        System.out.println(s.getName());
                    }
                });
                // set active category
                categoryList.getScene().getWindow().hide();
                PageHelper.onPageChange(getClass().getResource("Products.fxml"));
            }
        });
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            PageHelper.onPageChange(getClass().getResource("../Menu.fxml"));
        });

    }
}
