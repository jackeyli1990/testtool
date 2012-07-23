package LogReading;

import java.util.ArrayList;
import java.util.Properties;

import com.bitagentur.chart.JChartLibLinechart;

import DataStructures.BaseConfig;
import DataStructures.BaseCoorConf;
import DataStructures.BaseLineData;
import DataStructures.BaseLineFilter;
import DataStructures.Config;
import DataStructures.CoorConf;
import DataStructures.DataConsts;
import DataStructures.LineData;
import DataStructures.lineFilter;
import DataStructures.lineFilterStruct;
//eliyife
public class DataFilter implements DataFilterInterface
{
    private BaseConfig conf = null;
    private lineFilter lf = null;
    private CoorConf cc = null;
    //filter the line
    
    //separat ---------------------------
    private static DataFilter member;
    public DataFilter() {
        // TODO Auto-generated constructor stub
    }
    public BaseConfig GetConfig()
    {
        return conf;
    }
   private String ReplaceFirst(String source,String toreplace,String replaceto)
   {
       int i = source.indexOf(toreplace,0);
       if(i < 0)
           return source;
       else
       {
           int j = i + toreplace.length();
           if(j > source.length())
           {
               //shouldn't be here
               return null;
           }
           String part1 = source.substring(0,i);
           String part2 = source.substring(j,source.length());
           return part1 + replaceto + part2;
       }
   }
   public void ApplyDataConfig(BaseConfig bc)
   {
       /*conf = bc;
       cc = ((Config)conf).cc;
       lf = AnalysisFilter(((Config)conf).linelayout);*/
       conf = bc;
       cc = ((Config)conf).GetCoorConf();
       lf = AnalysisFilter((String)conf.Get(DataConsts.Config_linelayout));
   }
   public int GetLineYValueCount()
   {
       if(lf == null)
           return -1;
       return lf.GetRequiredSize();
   }
   private lineFilter AnalysisFilter(String layoutString)
    {
        String datas = "(dt)";
        String required_datas = "(*dt)";
        String remove_mark_begin = "(re)";
        String remove_mark_end = "(/re)";
        String remove_required_datas = "req_dts";
        String remove_datas = "dt";
        int i = layoutString.indexOf(datas,0);
        int j = layoutString.indexOf(required_datas,0);
        while(i >= 0 || j >= 0)
        {
            if(i >= 0)
            {
                //replace string
            layoutString = ReplaceFirst(layoutString,datas,remove_mark_begin + 
                    remove_datas + remove_mark_end);
                i = layoutString.indexOf(datas,0);
            }
            if(j >= 0)
            {
                layoutString = ReplaceFirst(layoutString,required_datas,remove_mark_begin
                        + remove_required_datas + remove_mark_end);
                j = layoutString.indexOf(required_datas,0);
            }
        }
    //    System.out.println(layoutString);
        i = 0;
        int last_remove_end = 0;
        lineFilter al = new lineFilter();
        while((i = layoutString.indexOf(remove_mark_begin,i)) >=0)
        {
           if(i > last_remove_end)
           {
               String tmp = layoutString.substring(last_remove_end,
                       i);
               lineFilterStruct lfs = new lineFilterStruct(tmp,false,false);
               al.al.add(lfs);
           }
           int content_begin = i + remove_mark_begin.length();
           if(content_begin >= layoutString.length())
           {
               // It's big mistake shouldn't be here
  //             System.out.println("null_1");
               return null;
           }
           int content_end = layoutString.indexOf(remove_mark_end,i);
           String tmp = layoutString.substring(content_begin,content_end);
           int find_require = tmp.indexOf(remove_required_datas,0);
           if(find_require < 0)
           {
               // it is data ,not required
               lineFilterStruct lfs = new lineFilterStruct("",true,false);
               al.al.add(lfs);
           }
           else
           {
                   // it data and required
                   lineFilterStruct lfs = new lineFilterStruct("",false,true);
                   al.al.add(lfs);
           }
           last_remove_end = content_end + remove_mark_end.length();
           if(last_remove_end > layoutString.length())
           {
               //It's big mistake
               System.out.println(last_remove_end);
               System.out.println(layoutString.length());
               System.out.println(layoutString.substring(0,last_remove_end));
               System.out.println("null_2");
               return null;
           }
           i = last_remove_end;
        }
        return al;
     }
private String[] SeparateLine(String lineSource, BaseLineFilter blf) {
    // TODO Auto-generated method stub
    lineFilter lf = (lineFilter)blf;
    String[] res = new String[lf.Size()];
    int last_sep_index = 0;
    int last_sep_index_end = 0;
    int total_values = 0;
    //kill the blankandtabsequence
    String[] rebuild = lineSource.split(" ");
    String nstr = "";
    for(int i = 0; i < rebuild.length; i ++)
    {
        if(!rebuild[i].equals(""))
        {
           nstr += rebuild[i] + " ";
        }
    }
    lineSource = nstr;
   // System.out.println(lineSource);
    for(int i = 0; i < res.length; i ++)
    {
        res[i] = null;
    }
 //   System.out.println(lineSource);
    for(int i = 0; i < lf.Size(); i ++)
    {
       lineFilterStruct lfs = (lineFilterStruct) lf.get(i);
       if(!lfs.content.equals(""))
       {
           last_sep_index = lineSource.indexOf(lfs.content,last_sep_index_end);
           last_sep_index_end = last_sep_index + lfs.content.length();
           if(last_sep_index < 0 || last_sep_index_end > lineSource.length())
           {
               // big mistake
 //              System.out.println(i);
               System.out.println("dirty_datas_ignore");
               return null;
           }
       }
       else if(lfs.IsRequiredData)
       {   
           //find next sep
          // System.out.println(lineSource.substring(0,last_sep_index_end));
           int next_sep_index = 0;
           lineFilterStruct lfstmp = null;
           if(i < res.length - 1)
           {
              // it must be the next sep
              lfstmp = (lineFilterStruct) lf.get(i + 1);
              if(lfstmp.content.equals(""))
              {
                  // big mistake
                  //System.out.println("null_2_2");
                  System.out.println("dirty_datas_ignore");
                  return null;
              }
              next_sep_index = lineSource.indexOf(lfstmp.content,last_sep_index_end);
              if(next_sep_index <= last_sep_index_end)
              {
                  //big mistake
                  //System.out.println("null_3");
                  System.out.println("dirty_datas_ignore");
                  return null;
              }
           }
           else
           {
               //to the end of line
               next_sep_index = lineSource.length();
           }
           res[i] = lineSource.substring(last_sep_index_end,next_sep_index);
           total_values ++;
           JChartLibLinechart jcllc;
       }         
    }
    int last_null_index = -1;
    String[] tres = new String[total_values];
    for(int i = 0,j = 0; i < res.length; i ++)
    {
       if(res[i] != null)
       {
           tres[j] = res[i];
           j++;
       }
    }
    return tres;
}
private BaseLineData __CollectLineData(String[] linedatas,BaseCoorConf bcc)
{
    LineData ld = new LineData();
    CoorConf cc = null;
    try
    {
        cc = (CoorConf)bcc;
    }
    catch(Exception e)
    {
        return null;
    }
    int index_x = -1;
    if(cc.x_place >= 0)
    {
        ld.x_value = DataCollector.StringToNumberic((Config)conf,linedatas[cc.x_place]);
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
        if(linedatas[i] != null && index_x != i)
        {   
//              System.out.println(linedatas[i]);
              ld.y_valuenum.add(DataCollector.StringToNumberic((Config)conf,linedatas[i]));
        }
    }
    return ld;
}
@Override
public BaseLineData CollectLineData(String line) {
    // TODO Auto-generated method stub
    String[] strtmp = SeparateLine(line,lf);
    if(strtmp == null)
        return null;
    return __CollectLineData(strtmp,cc);
}     
}