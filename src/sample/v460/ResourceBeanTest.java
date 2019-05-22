package sample.v460;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResourceBeanTest {

    private ResourceBean bean;

    @Before
    public void setUp() throws Exception {
        bean = new ResourceBean();
        bean.setDriverType("IEC850");
        bean.setTypeName("BOOL");
        bean.setMatrix("Сигнал/Норма_GM3");
        bean.setTagname("ГЩУ     ССПИ            Маслосборник               Шкаф HP00C003              Уровень высокий");
        bean.setUnit("");
        bean.setRecourcesLabel("01.03.74.04");
        bean.setNetAddr("3");
        bean.setSymbAddr("SPR.0300!BAY03GENERAL/GGIO1/Alm29700/stVal[ST]");
        bean.setIec870_type("");
        bean.setIec870_coa1("");
        bean.setIec870_ioa1("");

    }

    @Test
    public void getDriverType_SPRECON850() {
        bean.setDriverType("IEC850");
        Assert.assertEquals(DriverType.SPRECON850, bean.getDriverType());
    }

    @Test
    public void getDriverType_IEC850() {
        bean.setRecourcesLabel("230");
        bean.setDriverType("IEC850");
        Assert.assertEquals(DriverType.IEC850, bean.getDriverType());
    }
    @Test
    public void getDriverType_IEC870() {
        bean.setRecourcesLabel("");
        bean.setDriverType("IEC870");
        Assert.assertEquals(DriverType.IEC870, bean.getDriverType());
    }
    @Test
    public void getDriverType_SPRECON870() {
        bean.setDriverType("IEC870");
        Assert.assertEquals(DriverType.SPRECON870, bean.getDriverType());
    }

    @Test
    public void getAlarmClass_GM1() {
        bean.setMatrix("Сигнал/Норма_GM1");
        Assert.assertEquals(AlarmClassType.GM1, bean.getAlarmClassEnum());
    }

    @Test
    public void getAlarmClass_GM2() {
        bean.setMatrix("Сигнал/Норма_GM2");
        Assert.assertEquals(AlarmClassType.GM2, bean.getAlarmClassEnum());
    }

    @Test
    public void getAlarmClass_GM3() {
        bean.setMatrix("Сигнал/Норма_GM3");
        Assert.assertSame(AlarmClassType.GM3, bean.getAlarmClassEnum());
    }

    @Test
    public void getAlarmClass_BM_DM() {
        bean.setMatrix("Сигнал/Норма_BM");
        Assert.assertEquals(AlarmClassType.BM, bean.getAlarmClassEnum());
        bean.setMatrix("Сигнал/Норма_DM");
        Assert.assertEquals(AlarmClassType.BM, bean.getAlarmClassEnum());
    }

}