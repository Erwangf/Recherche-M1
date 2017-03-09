package IO;


public interface CSVConvertible {

    String[] getFields();
    String[] getCSVHeaders();
    Object getObjectFromField(String[] fields);
}
