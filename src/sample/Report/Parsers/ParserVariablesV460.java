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
        ms.setType(ResourceBean.class);

        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_16);


        CsvToBean<ResourceBean> cb = new CsvToBeanBuilder(reader)
                .withType(ResourceBean.class)
                .withMappingStrategy(ms)
                .withSeparator('\t')
                .withSkipLines(1)
                .build();

        ArrayList<ResourceBean> resourceBeans = new ArrayList<>(cb.parse());

        reader.close();

        return resourceBeans;
    }




}
