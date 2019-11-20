package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoadPopup {
    static String filename;

    public static String display(){
        filename = "";
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Label lbl = new Label();
        lbl.setText("Enter a filename");

        TextField fileText = new TextField();
        Button load = new Button("Load");
        load.setOnAction(event -> {
            filename = fileText.getText();
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(lbl, fileText, load);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return filename;
    }
}
