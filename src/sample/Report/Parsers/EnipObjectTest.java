package sample.Report.Parsers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EnipObjectTest {

    private EnipObject enip1;
    private EnipObject enip2;
    private EnipObject enip3;

    //private ArrayList<EnipObject> expected;

    @Before
    public void setUp() {
        enip1 = new EnipObject(
                "1100",
                "120",
                "132000",
                "10.137.8.10",
                "ENIP10"
        );
        enip2 = new EnipObject(
                "2200",
                "1500",
                "132000",
                "10.137.8.11",
                "ENIP11"
        );
        enip3 = new EnipObject(
                "100",
                "75",
                "132000",
                "10.137.8.12",
                "ENIP12"
        );
    }

    @Test
    public void getAllEnips() {

        List<EnipObject> actual = new ArrayList<>();
        ArrayList<EnipObject> expected = EnipObject.getAllEnips();
        actual.add(enip1);
        actual.add(enip2);
        actual.add(enip3);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void getAllEnips_NO_NULL() {
        ArrayList<EnipObject> expected = EnipObject.getAllEnips();
        Assert.assertNotNull(expected);
    }

    @Test
    public void getEnipsCount() {
        Assert.assertEquals(3, EnipObject.getEnipsCount());
    }

    @Test
    public void getEmptyListEnips_NO_NULL(){
        EnipObject.clearAllEnips();
        Assert.assertEquals(0, EnipObject.getEnipsCount());
    }

}