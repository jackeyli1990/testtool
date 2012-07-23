package DataStructures;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import DataStructures.UnitConvertStruct.UnitGroup;
import DataStructures.UnitConvertStruct.UnitGroupCenter;
import DataStructures.UnitConvertStruct.UnitGroupItem;
public class UnitGroupHelper
{
    public static void AddUnit(Config c,String name,double value)
    {
        UnitGroupCenter ugc = c.ugc;
        int size = name.length();
        UnitGroupItem ugi = new UnitGroupItem(value,name);
        ugc.AddUnit(ugi);
    }
 /*   public static UnitGroupItem GetUnit(Config c,String s)
    {
        UnitGroupCenter ugc = c.ugc;
            for(int j = 0; j < ugc.al.size(); j ++)
            {
  //              System.out.println(ug.al.get(j));
               UnitGroup ug = ugc.al.get(j);
               for(int i = 0; i < ug.al.size(); i ++)
               {
                   if(s.indexOf(ug.al.get(i).name) >= 0)
                   {
                       return ug.al.get(i);
                   }
               }
            }
            return null;
    }*/
    public static UnitGroupItem AnalysisUnit(Config c,String s)
    {
        UnitGroupCenter ugc = c.ugc;
        List<Integer> l = c.GetUnitLengthes();
        for(int i = 0; i < l.size(); i ++)
        {
            int len = l.get(i);
            if(len < s.length())
            {
                UnitGroupItem ugi = ugc.GetUnit(s.substring(s.length() - len,s.length()));
                if(ugi != null)
                    return ugi;
            }
        }
        return null;
    }
    public static UnitGroupItem GetUnit(Config c,String s)
    {
        return c.ugc.GetUnit(s);
    }
    public static List<UnitGroupItem> GetUnits(Config c)
    {
        return c.ugc.ListUnits();
    }
}