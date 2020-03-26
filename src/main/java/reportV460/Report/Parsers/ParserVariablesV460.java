package reportV460.Report.Parsers;


import com.opencsv.bean.*;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import reportV460.v460.ResourceBean;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParserVariablesV460 {

    private String file;

    public ParserVariablesV460(String file) {
        this.file = file;
    }

    private String getFile() {
        return file;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public ArrayList getBeansFromCsv() throws IOException {

        Path path = Paths.get(getFile());
        ColumnPositionMappingStrategy<ResourceBean> ms = new ColumnPositionMappingStrategy<ResourceBean>();

        Map<String, String> columnMapping = new HashMap<String, String>();
/*        columnMapping.put("VariableName", "variableName");
        columnMapping.put("DriverType", "driverType");
        columnMapping.put("TypeName", "typeName");
        columnMapping.put("Matrix", "matrix");
        columnMapping.put("Tagname", "tagname");
        columnMapping.put("Unit", "unit");
        columnMapping.put("SystemModelGroup", "systemModel");
        columnMapping.put("Recourceslabel", "recourcesLabel");
        columnMapping.put("NetAddr", "netAddr");
        columnMapping.put("SymbAddr", "symbAddr");
        columnMapping.put("IEC870_TYPE", "iec870_type");
        columnMapping.put("IEC870_COA1", "iec870_coa1");
        columnMapping.put("IEC870_IOA1", "iec870_ioa1");*/

        ms.setType(ResourceBean.class);

        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_16);

        CsvToBean<ResourceBean> cb = new CsvToBeanBuilder(reader)
                .withType(ResourceBean.class)
                .withMappingStrategy(ms)
                .withSeparator('\t')
                .withSkipLines(1)
                //.withFilter(line -> (!line[0].contains("Connect_vba") && !line[2].contains("Intern")))
                .withFieldAsNull(CSVReaderNullFieldIndicator.NEITHER)
                .build();

        ArrayList<ResourceBean> resourceBeans = new ArrayList<>(cb.parse());

        reader.close();

        return resourceBeans;
    }
}
