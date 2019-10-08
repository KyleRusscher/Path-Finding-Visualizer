package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    GridPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid.setPadding(new Insets(10, 10, 10, 10));
        for(int i = 0; i < 40; i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(20);
            grid.getColumnConstraints().add(col);
            for(int j = 0; j < 20; j++){
                Label label = new Label();
                GridPane.setConstraints(label, i, j, 1,1);
                GridPane.setHgrow(label, Priority.ALWAYS);
                GridPane.setVgrow(label, Priority.ALWAYS);
                GridPane.setFillHeight(label, true);
                GridPane.setFillWidth(label, true);
                addPane(i, j);
                grid.getChildren().add(label);
            }
        }

    }

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        pane.setOnMouseEntered(e -> {
            System.out.printf("Moused over a cell at [%d, %d]%n", colIndex, rowIndex);
        });
        grid.add(pane, colIndex, rowIndex);
    }


}
