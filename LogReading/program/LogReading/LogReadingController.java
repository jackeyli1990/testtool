package LogReading;

import java.io.IOException;
import java.util.ArrayList;

import com.bitagentur.chart.JChartLibLinechart;

import DataStructures.Config;
import DataStructures.DataConsts;
import charting.MyChartDataSet;
import charting.MyChartSerie;
import charting.MyLineChart;
import charting.MyLineChartRenderer;

public class LogReadingController
{
    private MyChartDataSet mcds = null;
    public LogReadingController()
    {
        
    }
    public void CollectData(Config Inputconfig) throws IOException
    {
        DataFilter df = new DataFilter();
        df.ApplyDataConfig(Inputconfig);
        DataCollector dc = new DataCollector();
        ArrayList<MyChartSerie> al =
                dc.ScanDatas(df,(String)Inputconfig.Get(DataConsts.Config_InputFile));
        mcds = new MyChartDataSet();
        for(int i = 0; i < al.size(); i ++)
        {
            mcds.addDataSerie(al.get(i));
        }
    }
    public void OutputData(Config Inputconfig) throws IOException
    {
        MyLineChart jllc = new MyLineChart(
                Inputconfig.Get(DataConsts.Config_chartname).toString(), "x","y" ,mcds);
        jllc.setRender(new MyLineChartRenderer(jllc));
        jllc.SaveAsPic((String)Inputconfig.Get(DataConsts.Config_OutputFile),
                (Integer)Inputconfig.Get(DataConsts.Config_Width),
                (Integer)Inputconfig.Get(DataConsts.Config_Height),
                (String)Inputconfig.Get(DataConsts.Config_Style));
        
        System.out.println("complete!");
        System.exit(0);
    }
}