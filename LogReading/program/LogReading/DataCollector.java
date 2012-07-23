package LogReading;

import java.awt.Paint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import charting.MyChartSerie;

import com.bitagentur.chart.JChartLibLinechart;
import com.bitagentur.data.JChartLibDataSet;
import com.bitagentur.data.JChartLibSerie;

import DataStructures.BaseLineData;
import DataStructures.Config;
import DataStructures.CoorConf;
import DataStructures.DataConsts;
import DataStructures.LineData;
import DataStructures.UnitGroupHelper;
import DataStructures.UnitConvertStruct.UnitGroup;
import DataStructures.UnitConvertStruct.UnitGroupItem;
import DataStructures.lineFilter;
import DataStructures.lineFilterStruct;
//eliyife
public class DataCollector
{
    private int dirtylines = 0;
    public  ArrayList<MyChartSerie> ScanDatas(DataFilter df,String filePath) throws IOException
    {
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            fr = new FileReader(new File(filePath));
            br = new BufferedReader(fr);
        }
        catch(FileNotFoundException fe)
        {
            System.out.println(DataConsts.HelpInfomations.CannotFindFile
                    + filePath);
            return null;
        }
        ArrayList<MyChartSerie> mcsal = CreateChartSeries(df);
        String line = null;
        int index = 0;
        int beginline = 0;
        int endline = -1;
        if(((Config)df.GetConfig()).Get(DataConsts.Config_BeginLine) != null)
           beginline = (Integer)((Config)df.GetConfig()).Get(DataConsts.Config_BeginLine);
        if(((Config)df.GetConfig()).Get(DataConsts.Config_EndLine) != null)
           endline = (Integer)((Config)df.GetConfig()).Get(DataConsts.Config_EndLine);
        while((line = br.readLine()) != null)
        {
            if(index > endline && endline >= 0)
                break;
            if(index >= beginline)
            {
                LineData ld = (LineData)df.CollectLineData(line);
                SerieAddLineData(ld,mcsal);
            }
            index ++;
        } 
        if((double)dirtylines / (double)index > 0.3)
        {
            System.out.println("warning" + ":"
          +  DataConsts.HelpInfomations.TomuchDirtyData);
        }         
        return mcsal;
    }
  /* public static LineData CollectLineData(String[] linedatas,CoorConf cc)
   {
       LineData ld = new LineData();
       int index_x = -1;
       if(cc.x_place >= 0)
       {
           ld.x_value = StringToNumberic(linedatas[cc.x_place]);
           index_x = cc.x_place;
       }
       else if(cc.date_place >= 0)
       {
           ld.date_place = linedatas[cc.date_place];
           index_x = cc.date_place;
       }
       else if(cc.timeskil_place >= 0)
       {
           ld.timskil_place = linedatas[cc.timeskil_place];
           index_x = cc.timeskil_place;   
       }
       else
       {
       }
       for(int i = 0; i < linedatas.length; i ++)
       {
           if(i != index_x && linedatas[i] != null)
           {   
 //              System.out.println(linedatas[i]);
               ld.y_valuenum.add(StringToNumberic(linedatas[i]));
           }
       }
       return ld;
   }*/
   public static Number StringToNumberic(Config c,String s)
   {
       UnitGroupItem ugi = UnitGroupHelper.AnalysisUnit(c, s);
       s = s.replaceAll(" ","");
       if(s.equals(""))
       {
           System.out.println("dirty_datas_ignore");
           return null;
       }
       String[] sp = s.split("[a-zA-z]+");
       String tmp = null;
       for(int i = 0; i  < sp.length; i ++)
       {
           if(!sp[i].equals(""))
           {
               tmp = sp[i];
           }
       }
       if(s.matches("[0-9]*[.]?[0-9]+[a-zA-z]+") && ugi == null)
       {
           System.out.println("warning :" + DataConsts.HelpInfomations.missunit + ":"
                   + s);
       }
       try
       {
           if(tmp.indexOf(".") >= 0)
           {
               if(ugi != null)
               {
  //             System.out.println(ugi.name);
                   return Double.parseDouble(tmp) * ugi.value;
               }
           else
               return Double.parseDouble(tmp);
           }
           else
           {
               if(ugi != null)
                   return Integer.parseInt(tmp) * ugi.value;
               else
                   return Double.parseDouble(tmp);
           }
       }
       catch(Exception e)
       {
           System.out.println("does not support data :" + s);
           return null;
       }
   }
   private ArrayList<MyChartSerie> CreateChartSeries(DataFilter df)
   {
         ArrayList<MyChartSerie> mcsal = new ArrayList<MyChartSerie>();
         Config c = (Config)df.GetConfig();
         CoorConf cc = c.GetCoorConf();
   //      System.out.println(df.GetLineYValueCount());
         for(int i = 0; i < df.GetLineYValueCount(); i ++)
         {
             String name = null;
             if(i < c.datanames.length &&
                     c.datanames[i] != null)
             {
                 name = c.datanames[i];
             }
             else
             {
                 name = "Data"+String.valueOf(i);
             }
             MyChartSerie mcs = new MyChartSerie(name);
             mcs.IsExtended = cc.date_place >= 0 ||
                     cc.x_place >= 0 || cc.timeskil_place >= 0 ?
                             true : false;
             mcsal.add(mcs);
         }
       return mcsal;
   }
   public void SerieAddLineData (LineData ld,ArrayList<MyChartSerie> mcsal)
   {
       if(ld == null)
       {
           dirtylines ++;
           return;
       }
       if(ld.x_value != null)
       {
           for(int i = 0; i < ld.y_valuenum.size(); i ++)
           {
               mcsal.get(i).AddValues(ld.x_value, ld.y_valuenum.get(i));
           }
       }
       else if(ld.date_place != null)
       {
           for(int i = 0; i < ld.y_valuenum.size(); i ++)
           {
               mcsal.get(i).AddValues(ld.date_place, ld.y_valuenum.get(i));
           }
       }
       else if(ld.timskil_place != null)
       {
           for(int i = 0; i < ld.y_valuenum.size(); i ++)
           {
               mcsal.get(i).AddValues(ld.timskil_place, ld.y_valuenum.get(i));
           }
       }
       else
       {
           for(int i = 0; i < ld.y_valuenum.size(); i ++)
           {
//                 System.out.println(ld.y_valuenum.get(i) + "xxxxx");
               mcsal.get(i).addValue(ld.y_valuenum.get(i));
           }
       }
   }
}