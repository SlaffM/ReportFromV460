package sample.v460;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sample.Report.Parsers.EnipObject;
import sample.Report.Parsers.ParserVariablesFromV460;

import java.io.File;
import java.util.ArrayList;

public class PointParamTest {


    private String txtPath;
    private ArrayList<PointParam> points;

    private EnipObject enip1;


    private ArrayList<EnipObject> enips;


    @Before
    public void setUp() throws Exception {
        txtPath = new File("." + "/tests/v460.txt").getAbsolutePath();
        EnipObject.clearAllEnips();
        enips = EnipObject.getAllEnips();

        ArrayList<ResourceBean> resourceBeans = new ParserVariablesFromV460(txtPath).getBeansFromCsv();
        PointParam.buildPoints(resourceBeans, enips);
        points = PointParam.getAllPoints();
        //points = new ParserVariablesFromV460(txtPath);
    }

    @Test
    public void points_should_NOT_NULL() {
        Assert.assertNotNull(points);
    }

    @Test
    public void points_should_be_SIX() {
        Assert.assertEquals(6, PointParam.getPointsCount());
    }

    @Test
    public void point_should_driver_SPRECON850() {
        PointParam pointParam = PointParam.getPointByNetAddr(3);
        Assert.assertEquals(DriverType.SPRECON850, pointParam.getDriverType());
    }

    @Test
    public void point_should_driver_IEC850() {
        PointParam pointParam = PointParam.getPointByNetAddr(118);
        Assert.assertEquals(DriverType.IEC850, pointParam.getDriverType());
    }

    @Test
    public void point_should_driver_SPRECON870() {
        PointParam pointParam = PointParam.getPointByNetAddr(120);
        Assert.assertEquals(DriverType.SPRECON870, pointParam.getDriverType());
    }

    @Test
    public void point_should_driver_IEC870() {
        PointParam pointParam = PointParam.getPointByNetAddr(43);
        Assert.assertEquals(DriverType.IEC870, pointParam.getDriverType());
    }

    @Test
    public void point_should_have_netAddr_3() {
        PointParam pointParam = PointParam.getPointByNetAddr(3);
        Assert.assertEquals(3, pointParam.getNetAddr());
    }

    @Test
    public void TI_should_coefTransform_5000_1() throws Exception {
        createEnip();
        changeEnipAndSetPoints();

        PointParam pointParam = PointParam.getPointByNetAddr(6);
        ResourceBean resourceBean = pointParam.getResourceBeans().get(1);                 //Коеффициент напряжения
        Assert.assertEquals("5000/1", resourceBean.getCoefficientTransform());
    }

    @Test
    public void TI_should_coefTransform_600_5() throws Exception {
        createEnip();
        enip1.setCurrentCoefficient("120");
        changeEnipAndSetPoints();

        PointParam pointParam = PointParam.getPointByNetAddr(6);
        ResourceBean resourceBean = pointParam.getResourceBeans().get(2);                     //Коэффициент тока
        Assert.assertEquals("600/5", resourceBean.getCoefficientTransform());
    }

    @Test
    public void TI_should_coefTransform_1500_1() throws Exception {
        createEnip();
        //enip1.setCurrentCoefficient("1500");
        changeEnipAndSetPoints();

        PointParam pointParam = PointParam.getPointByNetAddr(6);
        ResourceBean resourceBean = pointParam.getResourceBeans().get(2);                     //Коэффициент тока
        Assert.assertEquals("1500/1", resourceBean.getCoefficientTransform());
    }

    private void changeEnipAndSetPoints() throws Exception {
        enips = EnipObject.getAllEnips();
        PointParam.clearPoints();
        ArrayList<ResourceBean> resourceBeans = new ParserVariablesFromV460(txtPath).getBeansFromCsv();

        PointParam.buildPoints(resourceBeans, enips);

        points = PointParam.getAllPoints();
    }

    private void createEnip() {
        enip1 = new EnipObject(
                "5000",
                "1500",
                "132000",
                "10.137.8.6",
                "ENIP6"
        );
    }

}