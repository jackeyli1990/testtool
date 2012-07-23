package DataStructures;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.UnitConvertStruct.UnitGroupCenter;
import DataStructures.UnitConvertStruct.UnitGroupItem;

public class UnitGroupCenterTest extends UnitGroupCenter {

    private static UnitGroupCenter ugc; 
    @Before
    public void setUp() throws Exception {
        ugc = new UnitGroupCenter();
        UnitGroupItem ugi = new UnitGroupItem(1000,"g");
        ugc.AddUnit(ugi);
        ugi = new UnitGroupItem(1200,"xtg");
        ugc.AddUnit(ugi);
        ugi = new UnitGroupItem(1123,"m");
        ugc.AddUnit(ugi);
    }
    
    @Test
    public void testFindPosition() {
        System.out.println("test_1");
        Assert.assertEquals(ugc.FindPosition(1),-2);
        System.out.println("test_2");
        Assert.assertEquals(ugc.FindPosition(3), -1);
        System.out.println("test_3");
        Assert.assertEquals(ugc.FindPosition(2),1);
    }
    
    @Test
    public void testAddUnit() {
        Assert.assertEquals(ugc.GetUnit("g").name,"g" );
        Assert.assertEquals(ugc.GetUnit("g").value,new Double(1000));
        Assert.assertEquals(ugc.GetUnit("xtg").name, "xtg");
        Assert.assertEquals(ugc.GetUnit("xtg").value,new Double(1200));
        Assert.assertEquals(ugc.GetUnit("m").name,"m");
        Assert.assertEquals(ugc.GetUnit("m").value, new Double(1123));
    }

    @Test
    public void testGetUnit() {
        ugc.AddUnit(new UnitGroupItem(11200,"kg"));
        Assert.assertEquals(ugc.GetUnit("kg").name,"kg");
        Assert.assertEquals(ugc.GetUnit("kg").value,new Double(11200));
    }

    @Test
    public void testUnitLengths() {
        List<Integer> l  = ugc.UnitLengths();
        Assert.assertEquals(l.size(), 2);
        Assert.assertEquals(l.get(0).intValue(),3);
        Assert.assertEquals(l.get(1).intValue(), 1);
    }

    @Test
    public void testListUnits() {
        List<UnitGroupItem> l = ugc.ListUnits();
        for(int i = 0; i < l.size(); i ++)
        {
            System.out.println(l.get(i).name + ":" + String.valueOf(l.get(i).value));
        }
    }

}
