package LogReading;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import DataStructures.Config;
import DataStructures.DataConsts;
import DataStructures.UnitConvertStruct.UnitGroup;
import DataStructures.UnitGroupHelper;
import DataStructures.UnitConvertStruct.UnitGroupItem;

public class ConfigStreamer
{
    public ConfigStreamer()
    {
        
    }
    /*If there does not define the config,then use this*/
    public static Config CreateDefaultConfig()
    {
        Config c = new Config();
        c.Set("png",DataConsts.Config_Style);
        c.Set("0",DataConsts.Config_BeginLine);
        c.Set("-1",DataConsts.Config_EndLine);
        c.Set("(dt) (dt) (dt) (*dt) (dt) (*dt) " +
        		"(*dt) S (*dt) (dt) (dt) (dt)",DataConsts.Config_linelayout);
        c.Set("560",DataConsts.Config_Height);
        c.Set("680",DataConsts.Config_Width);
        c.Set("mtd.png",DataConsts.Config_OutputFile);
        c.Set("logreading.properties", DataConsts.Config_InputFile);
        c.Set("mychart",DataConsts.Config_chartname);
        c.Set("1000", "g");
        c.Set("1", "m");
        return c;
    }
    public static Config Read(String filepath)
    {
        Config c = new Config();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(filepath));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return CreateDefaultConfig();
        }
        Properties p = new Properties();
        try{
            p.load(fis);
        }
        catch(Exception e)
        {
            return CreateDefaultConfig();
        }
        Enumeration<Object> e = p.keys();
        while(e.hasMoreElements())
        {
            Object t = e.nextElement();
            c.Set(p.getProperty(t.toString()),t.toString());
        }
        return c;
    }
    public static void Write(String filepath,Config c) throws IOException
    {
        File f = new File(filepath);
        FileOutputStream fos = new FileOutputStream(f);
        Properties p = new Properties();
        for(int i = 0; i < DataConsts.Str_stable_properties.length; i ++)
        {
            Object obj = c.Get(DataConsts.Str_stable_properties[i]);
            if(obj != null)
            {
                p.put(DataConsts.Str_stable_properties[i],obj.toString());
            }
        }
        for(int i = 0; i < DataConsts.Double_stable_properties.length; i ++)
        {
            Object obj = c.Get(DataConsts.Double_stable_properties[i]);
            if(obj!= null)
            {
                p.put(DataConsts.Double_stable_properties[i],obj.toString());
            }
        }
        for(int i = 0; i < DataConsts.Integer_stable_properties.length; i ++)
        {
            Object obj = c.Get(DataConsts.Integer_stable_properties[i]);
            if(obj != null)
            {
                p.put(DataConsts.Integer_stable_properties[i], obj.toString());   
            }
        }
        List<UnitGroupItem> l = c.ugc.ListUnits();
        for(int i = 0; i < l.size(); i ++)
        {
            p.put(l.get(i).name,new Double(l.get(i).value).toString());
        }
        for(int i = 0; i < c.datanames.length; i ++)
        {
            if(c.datanames[i] != null)
            p.put("dataname_" + new Integer(i).toString(),c.datanames[i].toString());   
        }
        
        p.store(fos,"Config_for_Scanner");
    }
}