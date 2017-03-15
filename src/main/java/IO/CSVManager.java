package IO;

import LeMonde.LeMondeArticle;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Erwan on 07/03/2017.
 */
public class CSVManager<T extends CSVConvertible> {

    private String separator = ";";
    private boolean usingHeaders = true;

    public CSVManager() {
    }

    public CSVManager(String separator, boolean usingHeaders) {
        this.separator = separator;
        this.usingHeaders = usingHeaders;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public boolean isUsingHeaders() {
        return usingHeaders;
    }

    public void setUsingHeaders(boolean usingHeaders) {
        this.usingHeaders = usingHeaders;
    }

    public  void writeToCSV(ArrayList<T> items, String path){
        if(items.size()==0 || path.length() == 0){
            return;
        }
        try {
            // we open a BufferedWriter : it allow Java to write files, at a specific path ( given in parameters )
            OutputStream os = new FileOutputStream(path);
            os.write(239);
            os.write(187);
            os.write(191); //little fix for UTF-8
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //header :
            if(usingHeaders){
                //getting headers ( we know that the array is not empty )
                String[] headersArray = items.get(0).getCSVHeaders();
                boolean firstHeader = true;
                String headerLine = "";
                for(String header : headersArray){
                    if(!firstHeader){
                        headerLine+=separator;
                    }
                    headerLine+=header;
                    firstHeader = false;
                }

                bw.write(headerLine);
                bw.newLine();
            }

            boolean firstItem = true;
            for (CSVConvertible item : items) {
                if(!firstItem){
                    bw.newLine(); //next line
                }
                //creating CSV line for the item
                String itemLine = "";
                boolean firstField = true;
                for(String field : item.getFields()){
                    if(!firstField){
                        itemLine+=separator;
                    }
                    itemLine+=field;
                    firstField = false;
                }

                //  we write the line in the file, via by the bufferedWriter
                bw.write(itemLine);
                firstItem = false;

            }
            // at the end of the procedure, we flush the stream, and close the buffer.
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<T> readFromCSV(String path, T exampleClass) {
        ArrayList<T> result = new ArrayList<>();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                boolean first = true;

                while ((line = br.readLine()) != null) {
                    if (usingHeaders && first) {
                        first = false;
                    } else {


                        // process the line.
                    	int normalLength = exampleClass.getCSVHeaders().length;
                        String[] fields = line.split(";");
                        if(fields.length==normalLength){
                        	for (String field : fields) {
//                              System.out.println(field);
                          }
                          result.add((T) exampleClass.getObjectFromField(fields));
                        }
                        
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("Y-M-d'T'hh:mm:ss", Locale.FRENCH);

        System.out.println(dateFormat.format(new Date()));
        LeMondeArticle lma = new LeMondeArticle("AA", "TITRE", new Date(), "..", "topic");
        ArrayList<LeMondeArticle> list = new ArrayList<>();
        list.add(lma);
        CSVManager<LeMondeArticle> csvManager = new CSVManager<>();
        csvManager.writeToCSV(list, "test.csv");
        csvManager.readFromCSV("test.csv", new LeMondeArticle(null, null, null, null, null)).forEach(System.out::println);
    }
}
