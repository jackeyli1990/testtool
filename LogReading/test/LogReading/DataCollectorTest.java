package LogReading;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import DataStructures.Config;

public class DataCollectorTest {
private static DataCollector dc = new DataCollector();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testStringToNumberic() {
        ConfigStreamer cs = new ConfigStreamer();
        Config c = cs.Read("C:\\mxe\\logreading.properties");
        Number b = dc.StringToNumberic(c,new String("119m"));
        Number nb = dc.StringToNumberic(c, new String("156kg"));
        Number nbc=dc.StringToNumberic(c,new String("147g"));
        Assert.assertEquals(b,new Double(119));
        Assert.assertEquals(nb,new Double(156 * 500));
        Assert.assertEquals(nbc, new Double(147 * 1000));
    }

}
