package Utilities;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.List;

public class CsvUtility {
    /**
     *
     * @param file file path of csv
     * @return the all data of scv in list<String[]> format
     */
    public static  List<String[]> loadData(String file) {
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);
            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .build();
            return csvReader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load all the data");
        }
    }

    /**
     *
     * @param header csv
     * @param row from which want data
     * @param data csv data object which contains whole csv data
     * @return return the expected row-column value
     */
    public static String getData(String header,int row,List<String[]> data){
       try {
           int index = 0;
           for(String headerVal : data.get(0)){
               if(headerVal.equalsIgnoreCase(header)){
                   return data.get(row)[index];
               }else {
                   index++;
               }
           }
           throw new RuntimeException("Unable to find data for row "+row+" and header "+header);
       }catch (Exception e){
           throw new RuntimeException("Unable to find data for row "+row+" and header "+header);
       }
    }
}
