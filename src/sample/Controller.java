package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static sample.Model.*;

public class Controller implements Initializable {
    @FXML
    GridPane grid;
    @FXML
    Button startNode;
    @FXML
    Button targetNode;
    @FXML
    Button wallNode;
    @FXML
    Button removeWall;
    @FXML
    Button clearBoard;
    @FXML
    MenuItem dijkstra;
    @FXML
    MenuItem BFS;
    @FXML
    MenuItem aStar;
    @FXML
    MenuItem fast;
    @FXML
    MenuItem normal;
    @FXML
    MenuItem slow;
    @FXML
    MenuButton speed;


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
        removeWall.setOnMouseClicked(event -> {
            model.setSelectMode(SelectMode.REMOVE_WALL);
        });
        clearBoard.setOnMouseClicked(event -> {
            model.clearBoard();
            update();
        });
        dijkstra.setOnAction(event -> {
            model.setAlgorithm(Model.Algorithm.DIJKSTRA);
            model.shortestPath();
            visualize_algorithm(model.visitOrder, model.shortestPath);
        });
        BFS.setOnAction(event -> {
            model.setAlgorithm(Model.Algorithm.BFS);
            model.shortestPath();
            visualize_algorithm(model.visitOrder, model.shortestPath);
        });
        aStar.setOnAction(event -> {
            model.setAlgorithm(Model.Algorithm.ASTAR);
            model.shortestPath();
            visualize_algorithm(model.visitOrder, model.shortestPath);
        });
        fast.setOnAction(event -> {
            model.speed = FAST_SPEED;
            speed.setText("Speed: Fast");
        });
        normal.setOnAction(event -> {
            model.speed = NORMAL_SPEED;
            speed.setText("Speed: Normal");
        });
        slow.setOnAction(event -> {
            model.speed = SLOW_SPEED;
            speed.setText("Speed: Slow");
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

    private void visualize_algorithm(List<Node> visited, List<Node> path){

        Timeline pathDelay = new Timeline(new KeyFrame(Duration.millis(model.speed), new EventHandler<ActionEvent>() {
            int visited_index = 1;
            int path_index = path.size() - 1;
            @Override
            public void handle(ActionEvent event) {
                if(visited_index < visited.size() - 1){
                    panes.get(visited.get(visited_index).getColumn()).get(visited.get(visited_index).getRow()).setBackground(
                            new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
                    visited_index++;
                }
                else{
                    panes.get(path.get(path_index).getColumn()).get(path.get(path_index).getRow()).setBackground(
                            new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                    path_index--;
                }

            }
        }));
        pathDelay.setCycleCount(visited.size() + path.size() - 2);
        pathDelay.play();
        pathDelay.setOnFinished(event -> {
            model.visitOrder = new ArrayList<>();
            model.shortestPath = new ArrayList<>();
        });


    }


    private void addPane(int colIndex, int rowIndex) {


        panes.get(colIndex).get(rowIndex).setOnMouseClicked(e -> {
            dragging = !dragging;
            model.selectNode(colIndex,rowIndex);

            panes.get(colIndex).get(rowIndex).setBackground(new Background(new BackgroundFill(colorDecider(colIndex,rowIndex), CornerRadii.EMPTY, Insets.EMPTY)));

        });
        panes.get(colIndex).get(rowIndex).setOnMouseEntered(event -> {
            if(dragging && model.getSelectMode() == Model.SelectMode.WALL
                    || dragging && model.getSelectMode() == SelectMode.REMOVE_WALL){
                model.selectNode(colIndex,rowIndex);

                panes.get(colIndex).get(rowIndex).setBackground(new Background(new BackgroundFill(colorDecider(colIndex,rowIndex), CornerRadii.EMPTY, Insets.EMPTY)));

            }
        });


        grid.add(panes.get(colIndex).get(rowIndex), colIndex, rowIndex);
    }

    public Paint colorDecider(int c,int r){
        if(model.maze.get(c).get(r).isPath()){
            return Color.YELLOW;
        }
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
