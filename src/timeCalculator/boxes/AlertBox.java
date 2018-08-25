package timeCalculator.boxes;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void diplay(String title, String message){

        Stage altertWindow = new Stage();

        altertWindow.initModality(Modality.APPLICATION_MODAL);
        altertWindow.setTitle(title);
        altertWindow.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close this Windows");
        closeButton.setOnAction(e -> altertWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        label.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        altertWindow.setScene(scene);
        altertWindow.showAndWait();

    }

}
