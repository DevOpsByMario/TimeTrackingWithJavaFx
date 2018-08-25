package timeCalculator.saveANDwrit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import timeCalculator.table.Time;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVwrite {

    private static final String COMMA_DELIMITER = ",";
    private static final int TIME_ID_IDX = 0;
    private static final int TIME_DATE_IDX = 1;
    private static final int TIME_STARTTIME_IDX = 2;
    private static final int TIME_ENDTIME_IDX = 3;
    private static final int TIME_DURATION = 4;

    public ObservableList readCsvFile(String fileName)throws IOException{
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            List newTime = new ArrayList();

            String line = "";

            fileReader.readLine();
            while ((line= fileReader.readLine()) !=null){
                String[]tokens = line.split(COMMA_DELIMITER);
                if(tokens.length > 0){
                    Time timeLine = new Time(Integer.parseInt(
                            tokens[TIME_ID_IDX]),
                            tokens[TIME_DATE_IDX],
                            tokens[TIME_STARTTIME_IDX],
                            tokens[TIME_ENDTIME_IDX],
                            tokens[TIME_DURATION]);
                    newTime.add(timeLine);
                }
            }
            ObservableList<Time> readerCSVObervableList = FXCollections.observableArrayList(newTime);
            return readerCSVObervableList;

    }

}
