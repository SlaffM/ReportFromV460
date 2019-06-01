package sample.v460;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ResourceBeanTest {

    private ResourceBean sourceBean;

    @Before
    public void setUp() {
        sourceBean = new ResourceBean();
        sourceBean.setDriverType("IEC850");
        sourceBean.setTypeName("BOOL");
        sourceBean.setMatrix("Сигнал/Норма_GM3");
        sourceBean.setTagname("ГЩУ     ССПИ            Маслосборник               Шкаф HP00C003              Уровень высокий");
        sourceBean.setUnit("");
        sourceBean.setRecourcesLabel("01.03.74.04");
        sourceBean.setNetAddr("3");
        sourceBean.setSymbAddr("SPR.0300!BAY03GENERAL/GGIO1/Alm29700/stVal[ST]");
        sourceBean.setIec870_type("");
        sourceBean.setIec870_coa1("");
        sourceBean.setIec870_ioa1("");
    }

    private ResourceBean getValidatedBean(){
        ArrayList<ResourceBean> resourceBeans = new ArrayList<ResourceBean>();
        resourceBeans.add(sourceBean);
        resourceBeans.forEach(ResourceBean::validationDriverType);
        return resourceBeans.get(0);
    }

    @Test
    public void getDriverType_SPRECON850() {
        sourceBean.setDriverType("IEC850");
        Assert.assertEquals(DriverType.SPRECON850, getValidatedBean().getDriverType());
    }

    @Test
    public void getDriverType_IEC850() {
        sourceBean.setRecourcesLabel("230");
        sourceBean.setDriverType("IEC850");
        Assert.assertEquals(DriverType.IEC850, getValidatedBean().getDriverType());
    }
    @Test
    public void getDriverType_IEC870() {
        sourceBean.setRecourcesLabel("");
        sourceBean.setDriverType("IEC870");
        Assert.assertEquals(DriverType.IEC870, getValidatedBean().getDriverType());
    }
    @Test
    public void getDriverType_SPRECON870() {
        sourceBean.setDriverType("IEC870");
        Assert.assertEquals(DriverType.SPRECON870, getValidatedBean().getDriverType());
    }

    @Test
    public void getAlarmClass_GM1() {
        sourceBean.setMatrix("Сигнал/Норма_GM1");
        Assert.assertEquals(AlarmClassType.GM1, getValidatedBean().getAlarmClassEnum());
    }

    @Test
    public void getAlarmClass_GM2() {
        sourceBean.setMatrix("Сигнал/Норма_GM2");
        Assert.assertEquals(AlarmClassType.GM2, getValidatedBean().getAlarmClassEnum());
    }

    @Test
    public void getAlarmClass_GM3() {
        sourceBean.setMatrix("Сигнал/Норма_GM3");
        Assert.assertSame(AlarmClassType.GM3, getValidatedBean().getAlarmClassEnum());
    }

    @Test
    public void getAlarmClass_BM_DM() {
        sourceBean.setMatrix("Сигнал/Норма_BM");
        Assert.assertEquals(AlarmClassType.BM, getValidatedBean().getAlarmClassEnum());
        sourceBean.setMatrix("Сигнал/Норма_DM");
        Assert.assertEquals(AlarmClassType.BM, getValidatedBean().getAlarmClassEnum());
    }

}