package LogReading;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import DataStructures.Config;
import DataStructures.DataConsts;
import DataStructures.UnitConvertStruct.UnitGroupItem;

public class ConfigStreamerTest {

    private static ConfigStreamer cs;
    private static Config c;
    @Before
    public void setUp() throws Exception {
        cs = new ConfigStreamer();
        c = cs.Read("C:\\mxe\\logreading.properties");
    }

    @Test
    public void testCreateDefaultConfig() {
        //fail("Not yet implemented");
    }

    @Test
    public void testRead() {
        Assert.assertEquals("png",c.Get(DataConsts.Config_Style));
        Assert.assertEquals("C:\\mxe\\mpe_xx2.log",c.Get(DataConsts.Config_InputFile));
        Assert.assertEquals("C:\\mxe\\ntd.png",c.Get(DataConsts.Config_OutputFile));
        Assert.assertEquals("(dt) (dt) (dt) (*dt) (dt) (*dt) (dt) S (*dt) (dt) (dt) (dt)",
                c.Get(DataConsts.Config_linelayout));
        Assert.assertEquals("mynchart",c.Get(DataConsts.Config_chartname));
        Assert.assertEquals("jlasldf",c.Get(DataConsts.dataname_lable + "0"));
        Assert.assertEquals("ssdsd",c.Get(DataConsts.dataname_lable + "1"));
        Assert.assertEquals("asdf",c.Get(DataConsts.dataname_lable + "2"));
        Assert.assertEquals(1200,c.Get(DataConsts.Config_Width));
        Assert.assertEquals(1000,c.Get(DataConsts.Config_Height));
        UnitGroupItem ugi = (UnitGroupItem)c.Get("m");
        Assert.assertEquals(new Double(1),ugi.value);
        Assert.assertEquals("m",ugi.name);
        ugi = (UnitGroupItem)c.Get("g");
        Assert.assertEquals("g",ugi.name);
        Assert.assertEquals(new Double(1000),ugi.value);
    }

    @Test
    public void testWrite() throws IOException {
        cs.Write("C:\\mxe\\nconfig.properties", c);
    }

}
