package LogReading;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.bitagentur.chart.JChartLibLinechart;
import com.bitagentur.data.JChartLibSerie;

import charting.MyChartDataSet;
import charting.MyChartSerie;
import charting.MyLineChartRenderer;
import DataStructures.Config;
import DataStructures.CoorConf;
import DataStructures.DataConsts;
import DataStructures.UnitConvertStruct.UnitGroup;
import DataStructures.UnitConvertStruct.UnitGroupItem;
import DataStructures.UnitGroupHelper;
import DataStructures.lineFilter;
import DataStructures.DataConsts.HelpInfomations;

//eliyife
public class LogReading
{
    public static void  main(String[] args) throws IOException
    {
        File f = new File("logreading.properties");
        if(!f.exists())
        {
            if(!f.exists())
                f.createNewFile();
        }        
        ConfigStreamer cs = new ConfigStreamer();
        Config c = null;
        if(args.length == 1)
        {
            if(args[0].equals("help"))
            {
                System.out.println(DataConsts.HelpInfomations.help);
                System.out.println(DataConsts.HelpInfomations.command_1);
                System.out.println(DataConsts.HelpInfomations.command_1_info);
                System.out.println(DataConsts.HelpInfomations.command_2);
                System.out.println(DataConsts.HelpInfomations.command_2_info);
                System.out.println(DataConsts.HelpInfomations.command_3);
                System.out.println(DataConsts.HelpInfomations.command_3_info);
                System.out.println(DataConsts.HelpInfomations.watchpropertie);
                return;
            }
            else if(args[0].equals("readme"))
            {
                System.out.println(DataConsts.HelpInfomations.readme);
                return;
            }
            else
            {
            c = cs.Read(args[0]);
            System.out.println(args[0]);
            if(c.Get(DataConsts.Config_InputFile) == null 
                    || c.Get(DataConsts.Config_OutputFile) == null)
            {
                System.out.println(DataConsts.HelpInfomations.missIn_OutputFile);
            }
            }
        }
        else if(args.length == 2)
        {
            c = cs.CreateDefaultConfig();
            c.Set(args[0],DataConsts.Config_InputFile);
            c.Set(args[1],DataConsts.Config_OutputFile);
            cs.Write(DataConsts.DefaultConfigPath, c);
        }
        else if(args.length == 3)
        {
            c = cs.Read(args[2]);
            c.Set(args[0],DataConsts.Config_InputFile);
            c.Set(args[1],DataConsts.Config_OutputFile);
        }
        else
        {
            System.out.println("no args");
            return;
        }
          LogReadingController lrc = new LogReadingController();
          lrc.CollectData(c);
          lrc.OutputData(c);
        }
}