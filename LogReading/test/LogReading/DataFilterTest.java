package LogReading;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import DataStructures.BaseLineData;
import DataStructures.Config;
import DataStructures.LineData;

public class DataFilterTest {

    private DataFilter df;
    @Before
    public void setUp() throws Exception {
        df = new DataFilter();
    }

    @Test
    public void testGetConfig() {
        //fail("Not yet implemented");
    }

    @Test
    public void testApplyDataConfig() {
       // ConfigStreamer cs = new ConfigStreamer();
       // Config c = cs.Read("C:\\mxe\\logreading.properties");
       // df.ApplyDataConfig(c);
       // Assert.assertEquals(c.GetCoorConf().,);
        
    }

    @Test
    public void testGetLineYValueCount() {
        ConfigStreamer cs = new ConfigStreamer();
        Config c = cs.Read("C:\\mxe\\logreading.properties");
        df.ApplyDataConfig(c);
        Assert.assertEquals(df.GetLineYValueCount(),3);
    }

    @Test
    public void testCollectLineData() {
        ConfigStreamer cs = new ConfigStreamer();
        Config c = cs.Read("C:\\mxe\\logreading.properties");
        df.ApplyDataConfig(c);
        String str = new String("12455 asuser    20   0 1302m 175m  10m S  176  0.7   0:47.74 java");
        BaseLineData bld = df.CollectLineData(str);
        LineData ld = (LineData)bld;
        Assert.assertEquals(ld.y_valuenum.get(0),new Double(0));
        Assert.assertEquals(ld.y_valuenum.get(1), new Double(175));
        Assert.assertEquals(ld.y_valuenum.get(2), new Double(176));
    }

}
