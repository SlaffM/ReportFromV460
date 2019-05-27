package sample.Report.Parsers;


import com.opencsv.bean.*;
import sample.v460.ResourceBean;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ParserVariablesV460 {

    private String file;
    private ArrayList<EnipObject> enipObjects;

    public ParserVariablesV460(String file) {
        this(file, new ArrayList<EnipObject>());
    }

    public ParserVariablesV460(String file, ArrayList<EnipObject> enipObjects){
        this.file = file;
        this.enipObjects = enipObjects;
    }

    private String getFile() {
        return file;
    }

    public ArrayList getBeansFromCsv() throws IOException {

        Path path = Paths.get(getFile());
        ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
        ms.setType(ResourceBean.class);

        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_16);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(ResourceBean.class)
                .withMappingStrategy(ms)
                .withSeparator('\t')
                .withSkipLines(1)
                .build();

        ArrayList resourceBeans = new ArrayList<>(cb.parse());

        ResourceBean.validateDriverType(resourceBeans);

        reader.close();

        return resourceBeans;
    }


}
