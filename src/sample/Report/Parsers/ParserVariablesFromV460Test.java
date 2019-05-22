package sample.Report.Parsers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sample.v460.PointParam;
import sample.v460.ResourceBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ParserVariablesFromV460Test {

    private String txtPath;
    private ArrayList<PointParam> points;


    private EnipObject enip1;

    private ArrayList<EnipObject> expected;


    @Before
    public void setUp() throws Exception {
        txtPath = new File("." + "/tests/v460.txt").getAbsolutePath();

        /*enip1 = new EnipObject(
                "1100",
                "120",
                "132000",
                "10.137.8.10",
                "ENIP10"
        );*/

        expected = EnipObject.getAllEnips();
        points = new ParserVariablesFromV460(txtPath, expected).buildPoints();
    }

    @Test
    public void buildPoints_NOT_NULL() {
        Assert.assertNotNull(points);
    }

    @Test
    public void countPoints_FIVE(){
        Assert.assertEquals(5, points.size());
    }
}