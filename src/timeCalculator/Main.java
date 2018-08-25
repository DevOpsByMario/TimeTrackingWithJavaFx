package timeCalculator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import timeCalculator.boxes.ConfirmBox;
import timeCalculator.saveANDwrit.CSVsave;
import timeCalculator.saveANDwrit.CSVwrite;
import timeCalculator.table.Time;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);

    }

    //Set some Variables and Constants
    public static int WIDTH = 600;
    public static int HEIGHT = 500;
    File zweihorn = new File("zweihorn");

    Stage window;
    Scene scene;
    Button startButton, stopButton, exportButton;
    Label timeLabel, totalTime;
    LocalTime now;
    LocalTime later;
    String fromTo;
    int id;
    TableView<Time> timeTableView;


    @Override
    public void start(Stage primaryStage) throws IOException {
        /*
        if (zweihorn.exists()){
            CSVwrite.readCsvFile("zweihorn");
        }
        */



        window = primaryStage;

        TableColumn<Time, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Time, String> dateColumn = new TableColumn<>("Datum");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Time, String> startTimeColumn = new TableColumn<>("Startzeit");
        startTimeColumn.setMinWidth(100);
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<Time, Integer> endTimeColumn = new TableColumn<>("Endzeit");
        endTimeColumn.setMinWidth(100);
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<Time, Integer> durtionColumn = new TableColumn<>("Dauer");
        durtionColumn.setMinWidth(100);
        durtionColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Wird beim schließen über das X ausgeführt
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgramm();
        });

        //Initial Layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        //Verticale Spacing
        gridPane.setVgap(8);
        //Horizontal Spacing
        gridPane.setHgap(10);

        //Show GridLines
        //gridPane.setGridLinesVisible(true);

        //Initialisation of all Elements
        startButton = new Button("Start");
        stopButton = new Button("STOP");
        exportButton = new Button("Export");
        timeLabel = new Label("TimeCounterkönnte hierstehen");
        totalTime = new Label("TotalTime");

        //Create TimeTable
        timeTableView = new TableView<>();
        timeTableView.getColumns().addAll(idColumn, dateColumn, startTimeColumn, endTimeColumn, durtionColumn);

        // Position Elements
        GridPane.setHalignment(timeLabel, HPos.LEFT);
        GridPane.setHalignment(startButton, HPos.LEFT);
        GridPane.setHalignment(stopButton, HPos.LEFT);
        GridPane.setHalignment(exportButton, HPos.LEFT);
        GridPane.setHalignment(totalTime, HPos.LEFT);
        GridPane.setHalignment(timeTableView, HPos.CENTER);

        gridPane.add(timeLabel, 0, 0, 3, 1);
        gridPane.add(startButton, 0, 1);
        gridPane.add(stopButton, 1, 1);
        gridPane.add(exportButton, 1, 2);
        gridPane.add(totalTime, 2, 2);
        gridPane.add(timeTableView, 0, 3, 3, 1);


        //Actions for the Button
        //START BUTTON
        startButton.setOnAction(event -> {
            now = LocalTime.now();
            timeLabel.setText(String.valueOf(now.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            startButton.setVisible(false);
        });

        //STOP BUTTON
        stopButton.setOnAction(event -> {
            startButton.setVisible(true);
            later = LocalTime.now();
            //timeLabel.setText(String.valueOf(later.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            totalTime.setText(LocalTime.ofSecondOfDay(ChronoUnit.MINUTES.between(now, later)).format(DateTimeFormatter.ofPattern("mm")) + ":"
                    + LocalTime.ofSecondOfDay(ChronoUnit.SECONDS.between(now, later)).format(DateTimeFormatter.ofPattern("ss")));
            Duration duration = Duration.between(now, later);
            int min = (int) ((duration.getSeconds() % (60 * 60)) / 60);
            int sec = (int) (duration.getSeconds() % 60);
            fromTo = min + ":" + sec;
            try {
                addData();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        //EXPORT BUTTON
        exportButton.setOnAction(event -> {
            //AlertBox.diplay("ALARM", "Du hast zu viele Stunden");
            //totalTime.setText(String.valueOf(LocalDateTime.now().toLocalTime()));


        });

        //gridPane.getChildren().addAll(timeLabel, startButton, stopButton,exportButton, totalTime);
        scene = new Scene(gridPane, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle("Judith's Zeit-Fresser");
        window.show();
        restartAddData();

    }

    public void addData() throws IOException {
        Time times = new Time();
        id = getId();
        times.setId(id += 1);
        times.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        times.setStartTime(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        times.setEndTime(later.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        times.setDuration(fromTo);
        timeTableView.getItems().add(times);
        ObservableList<Time> zeut = FXCollections.observableArrayList(timeTableView.getItems());
        CSVsave.saveCsvFile(zeut, "zweihorn");

    }

    public void restartAddData() throws IOException {

        if (zweihorn.exists()) {
            CSVwrite csVwrite = new CSVwrite();
            ObservableList<Time> horst = csVwrite.readCsvFile("zweihorn");
            for (Time zeit : horst) {
                zeit.setId(zeit.getId());
                zeit.setDate(zeit.getDate());
                zeit.setStartTime(zeit.getStartTime());
                zeit.setEndTime(zeit.getEndTime());
                zeit.setDuration(zeit.getDuration());
                timeTableView.getItems().add(zeit);
            }
        }
    }

    private void closeProgramm() {
        Boolean answer = ConfirmBox.diplay("EXIT or NOT!", "Wurstfinger oder willst du wirklich zumachen??");
        //saveFile();
        if (answer)
            window.close();
    }

    public int getId() {
        return id;
    }


}


