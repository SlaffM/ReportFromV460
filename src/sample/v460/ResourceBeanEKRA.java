package sample.v460;

import com.opencsv.bean.CsvBindByPosition;
import sample.Helpers.Helpers;

public class ResourceBeanEKRA extends ResourceBean{
    @CsvBindByPosition(position = 2)
    String driverType;
    @CsvBindByPosition(position = 4)
    String typeName;
    @CsvBindByPosition(position = 5)
    String matrix;
    @CsvBindByPosition(position = 6, required = true)
    String tagname;
    @CsvBindByPosition(position = 11)
    String recourcesLabel;
    @CsvBindByPosition(position = 12)
    String netAddr;
    @CsvBindByPosition(position = 19)
    String symbAddr;
    @CsvBindByPosition(position = 76)
    String iec870_type;
    @CsvBindByPosition(position = 77)
    String iec870_coa1;
    @CsvBindByPosition(position = 78)
    String iec870_ioa1;
}
