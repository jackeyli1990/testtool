package DataStructures;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import DataStructures.UnitConvertStruct.*;
public class ConfigTest {

    private static Config conf;
    @Before
    public void setUp() throws Exception {
        conf = new Config();
        conf.Set("0",DataConsts.Config_BeginLine);
        conf.Set("1",DataConsts.Config_EndLine);
        conf.Set("mchart",DataConsts.Config_chartname);
        conf.Set("100",DataConsts.Config_Height);
        conf.Set("C:\\xte.log",DataConsts.Config_InputFile);
        conf.Set("C:\\ddd.png",DataConsts.Config_OutputFile);
        conf.Set("(dt) (*dt)",DataConsts.Config_linelayout);
        conf.Set("png",DataConsts.Config_Style);
        conf.Set("350",DataConsts.Config_Width);
        conf.Set("2",DataConsts.Config_x_value_column);
        conf.Set("3",DataConsts.Config_x_value_type);
        conf.Set("2.2",DataConsts.Config_XStep);
        conf.Set("2.5",DataConsts.Config_YStep);
        conf.Set("dt1",DataConsts.dataname_lable + "1");
        conf.Set("1200","mg");
        conf.Set("1","g");
        conf.Set("10000","kg");
    }

    @Test
    public void testSetAndGet() {
        Assert.assertEquals(conf.Get(DataConsts.Config_BeginLine),0);
        Assert.assertEquals(conf.Get(DataConsts.Config_EndLine),1);
        Assert.assertEquals(conf.Get(DataConsts.Config_chartname),"mchart");
        Assert.assertEquals(conf.Get(DataConsts.Config_Height),100);
        Assert.assertEquals(conf.Get(DataConsts.Config_InputFile),"C:\\xte.log");
        Assert.assertEquals(conf.Get(DataConsts.Config_OutputFile), "C:\\ddd.png");
        Assert.assertEquals(conf.Get(DataConsts.Config_linelayout),"(dt) (*dt)");
        Assert.assertEquals(conf.Get(DataConsts.Config_Style),"png");
        Assert.assertEquals(conf.Get(DataConsts.Config_Width),350);
        Assert.assertEquals(conf.Get(DataConsts.Config_x_value_column),2);
        Assert.assertEquals(conf.Get(DataConsts.Config_x_value_type), "3");
        Assert.assertEquals(conf.Get(DataConsts.Config_XStep), new Double(2.2));
        Assert.assertEquals(conf.Get(DataConsts.Config_YStep), new Double(2.5));
        Assert.assertEquals(conf.Get(DataConsts.dataname_lable + "1"),"dt1");
        Assert.assertEquals(((UnitGroupItem)conf.Get("mg")).name,"mg");
        Assert.assertEquals(new Double(1200.0),((UnitGroupItem)conf.Get("mg")).value,0.00001);
        Assert.assertEquals(((UnitGroupItem)conf.Get("g")).name,"g");
        Assert.assertEquals(((UnitGroupItem)conf.Get("g")).value,new Double(1).doubleValue(),0.00001);
        Assert.assertEquals(((UnitGroupItem)conf.Get("kg")).name,"kg");
        Assert.assertEquals(((UnitGroupItem)conf.Get("kg")).value,(double)10000,0.00001);
    }
    @Test
    public void testGetCoorConf() {
        conf.Set(DataConsts.Value_Type_Number,DataConsts.Config_x_value_type);
        Assert.assertEquals(conf.GetCoorConf().x_place,2);
        conf.Set(DataConsts.Value_Type_Date,DataConsts.Config_x_value_type);
        Assert.assertEquals(conf.GetCoorConf().date_place, 2);
        conf.Set(DataConsts.Value_Type_timestamp,DataConsts.Config_x_value_type);
        Assert.assertEquals(conf.GetCoorConf().timeskil_place,2);
    }

}
