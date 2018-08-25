package timeCalculator.saveANDwrit;

import javafx.collections.ObservableList;
import timeCalculator.table.Time;

import java.io.FileWriter;
import java.io.IOException;

public class CSVsave  {

    private static final String FILENAME = "einhorn";
    private static final String FILENAME2 = "zweihorn";

    private static final String COMMA_DELIMETER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "id, date, startTime, endTime, duration";

    public static void saveCsvFile(ObservableList<Time> observableListTime, String fileName)throws IOException{

    FileWriter fileWriter = null;

        fileWriter = new FileWriter(FILENAME2);
        fileWriter.append(FILE_HEADER.toString());
        fileWriter.append(NEW_LINE_SEPARATOR);
        for (Time zeit : observableListTime ){
            fileWriter.append(String.valueOf(zeit.getId()));
            fileWriter.append(COMMA_DELIMETER);
            fileWriter.append(zeit.getDate());
            fileWriter.append(COMMA_DELIMETER);
            fileWriter.append(zeit.getStartTime());
            fileWriter.append(COMMA_DELIMETER);
            fileWriter.append(zeit.getEndTime());
            fileWriter.append(COMMA_DELIMETER);
            fileWriter.append(zeit.getDuration());
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
            fileWriter.flush();
            fileWriter.close();

    }
}
