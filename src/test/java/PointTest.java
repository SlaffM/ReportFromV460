import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import reportV460.Report.Parsers.EnipObject;
import reportV460.Report.Parsers.ValidatorResourceBeans;
import reportV460.v460.DriverType;
import reportV460.v460.GrouperPoints;
import reportV460.v460.Point;
import reportV460.v460.ResourceBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PointTest {


    private String txtPath;
    private String pathEnipConfig;
    private ArrayList<Point> points;

    private EnipObject enip1;


    private ArrayList<EnipObject> enips;


    @Before
    public void setUp() throws Exception {
        txtPath = new File("." + "/tests/v460.txt").getAbsolutePath();
        pathEnipConfig = new File("." + "/tests").getAbsolutePath();
        EnipObject.clearAllEnips();
        buildPoints();
    }

    @Test
    public void points_should_NOT_NULL() {
        Assert.assertNotNull(Point.getAllPoints());
    }

    @Test
    public void points_should_be_SIX() {
        Assert.assertEquals(6, Point.getPointsCount());
    }

    @Test
    public void point_should_driver_SPRECON850() {
        Point point = Point.getPointByNetAddr(3);
        Assert.assertEquals(DriverType.SPRECON850, point.getDriverType());
    }

    @Test
    public void point_should_driver_IEC850() {
        Point point = Point.getPointByNetAddr(118);
        Assert.assertEquals(DriverType.IEC850, point.getDriverType());
    }

    @Test
    public void point_should_driver_SPRECON870() {
        Point point = Point.getPointByNetAddr(120);
        Assert.assertEquals(DriverType.SPRECON870, point.getDriverType());
    }

    @Test
    public void point_should_driver_IEC870() {
        Point point = Point.getPointByNetAddr(43);
        Assert.assertEquals(DriverType.IEC870, point.getDriverType());
    }

    @Test
    public void point_should_have_netAddr_3() {
        Point point = Point.getPointByNetAddr(3);
        Assert.assertEquals("3", point.getGrouppingParameter());
    }

    @Test
    public void TI_should_coefTransform_5000_1() throws Exception {
        createEnip();
        buildPoints();

        Point point = Point.getPointByNetAddr(6);
        ResourceBean resourceBean = point.getResourceBeans().get(1);                 //Коеффициент напряжения
        Assert.assertEquals("5000/1", resourceBean.getCoefficientTransform());
    }

    @Test
    public void TI_should_coefTransform_600_5() throws Exception {
        createEnip();
        enip1.setCurrentCoefficient("120");
        buildPointsWithEnip();


        Point point = Point.getPointByNetAddr(6);
        ResourceBean resourceBean = point.getResourceBeans().get(2);                     //Коэффициент тока
        Assert.assertEquals("600/5", resourceBean.getCoefficientTransform());
    }

    @Test
    public void TI_should_coefTransform_1500_1() throws Exception {
        createEnip();
        //enip1.setCurrentCoefficient("1500");
        buildPointsWithEnip();

        Point point = Point.getPointByNetAddr(6);
        ResourceBean resourceBean = point.getResourceBeans().get(2);                     //Коэффициент тока
        Assert.assertEquals("1500/1", resourceBean.getCoefficientTransform());
    }

    private void buildPoints() throws IOException {
        Point.clearPoints();
        List<ResourceBean> resourceBeans = new ValidatorResourceBeans(txtPath, new ArrayList<>()).getReadyBeans();
        Point.buildPoints(resourceBeans, GrouperPoints.GROUP_BY_NETADDR);
    }

    private void buildPointsWithEnip() throws IOException {
        Point.clearPoints();
        List<ResourceBean> resourceBeans = new ValidatorResourceBeans(txtPath, EnipObject.getAllEnips()).getReadyBeans();
        Point.buildPoints(resourceBeans, GrouperPoints.GROUP_BY_NETADDR);
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