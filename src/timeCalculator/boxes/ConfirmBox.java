package timeCalculator.boxes;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import timeCalculator.table.Time;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfirmBox {

    static boolean answer;

    public static boolean diplay(String title, String message){

        Stage confirmWindow = new Stage();

        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        confirmWindow.setTitle(title);
        confirmWindow.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Ja, ich kann schon klicken!");
        Button noButton = new Button("Ups, falsche Taste erwischt");

        yesButton.setOnAction(event -> {
            answer = true;
            confirmWindow.close();
        });
        noButton.setOnAction(event -> {
            answer = false;
            confirmWindow.close();
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        label.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        confirmWindow.setScene(scene);
        confirmWindow.showAndWait();

        return answer;
    }



}
