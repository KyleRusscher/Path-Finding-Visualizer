package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    GridPane grid;
    @FXML
    Button startNode;
    @FXML
    Button targetNode;
    @FXML
    Button wallNode;

    Model model;
    boolean dragging;
    List<List<Pane>> panes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new Model();
        dragging = false;

        startNode.setOnMouseClicked(event -> {
            model.setSelectMode(Model.SelectMode.START);
        });
        targetNode.setOnMouseClicked(event -> {
            model.setSelectMode(Model.SelectMode.END);
        });
        wallNode.setOnMouseClicked(event -> {
            model.setSelectMode(Model.SelectMode.WALL);
        });

        //initialize the grid
        grid.setPadding(new Insets(10, 10, 10, 10));
        for(int i = 0; i < 40; i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(20);
            grid.getColumnConstraints().add(col);
            panes.add(new ArrayList<>());
            for(int j = 0; j < 20; j++){
                panes.get(i).add(new Pane());
                Label label = new Label();
                //label.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET,CornerRadii.EMPTY, Insets.EMPTY)));
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


        panes.get(colIndex).get(rowIndex).setOnMouseClicked(e -> {
            dragging = !dragging;
            model.selectNode(colIndex,rowIndex);
            System.out.println(model.maze.get(colIndex).get(rowIndex).isStart() + " " + model.maze.get(colIndex).get(rowIndex).isWall());
            panes.get(colIndex).get(rowIndex).setBackground(new Background(new BackgroundFill(colorDecider(colIndex,rowIndex), CornerRadii.EMPTY, Insets.EMPTY)));

        });
        panes.get(colIndex).get(rowIndex).setOnMouseEntered(event -> {
            if(dragging && model.getSelectMode() == Model.SelectMode.WALL){
                model.selectNode(colIndex,rowIndex);
                System.out.println(model.maze.get(colIndex).get(rowIndex).isStart() + " " + model.maze.get(colIndex).get(rowIndex).isWall());
                panes.get(colIndex).get(rowIndex).setBackground(new Background(new BackgroundFill(colorDecider(colIndex,rowIndex), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });


        grid.add(panes.get(colIndex).get(rowIndex), colIndex, rowIndex);
    }

    public Paint colorDecider(int c,int r){
        if(model.maze.get(c).get(r).isStart()){
            return Color.BLUE;
        }
        if(model.maze.get(c).get(r).isWall()){
            return Color.BLACK;
        }

        if(model.maze.get(c).get(r).isEnd()){
            return Color.RED;
        }
        return null;
    }

    public void update(){
        for(int c = 0; c < 40; c++){
            for(int r = 0; r < 20; r++){
                panes.get(c).get(r).setBackground(new Background(new BackgroundFill(colorDecider(c,r), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }

    }






}
