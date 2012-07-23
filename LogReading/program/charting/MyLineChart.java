package charting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import DataStructures.DataConsts;

import com.bitagentur.chart.JChartLibLinechart;
import com.bitagentur.data.JChartLibDataSet;
import com.bitagentur.data.JChartLibSerie;
import com.bitagentur.renderer.Java2d_Draw_Util;

public class MyLineChart extends JChartLibLinechart
{
    public MyChartDataSet copySet;
    private Number num_max_y;
    private Number num_min_y;
    private Number Max_X_number;
    private List<Object> lo;
    private List<Number> ln;
    private Color[] bcolor = {Color.BLUE,Color.GRAY,Color.RED,Color.YELLOW};
    private BufferedImage bi = null;
    private Rectangle2D Axisarea;
    private Rectangle2D[] DataNameareas;
    private Rectangle2D titlearea;
    private String[] titles;
    private int margin_x_l = 50,margin_y_u = 50
            ,margin_x_r = 50,margin_y_b= 50;
    private String Style = "bottom";
    private int Width,Height;
    public MyLineChart(String title, String xAxis, String yAxis, MyChartDataSet dataSet) {
        super(title, xAxis, yAxis, dataSet);
        copySet = dataSet;
        DataNameareas = new Rectangle2D.Double[DataConsts.max_Cur];
        titlearea = new Rectangle2D.Double();
        titles = new String[DataConsts.max_Cur];
        for(int i = 0; i < titles.length; i ++)
        {
            titles[i] = null;
        }
        // TODO Auto-generated constructor stub
    }
    public Rectangle2D GetAxisRectangle2D(int width,int height)
    {
        Rectangle2D ret = new Rectangle2D.Double();
        if(width > 500)
        {
            margin_x_l = width / 10;
            margin_x_r = width / 10;
        }
        if(height > 500)
        {
            margin_y_u = margin_y_b = width / 10;
        }
        if(width > 1000)
            margin_x_l = margin_x_r = 100;
        if(height > 1000)
            margin_y_u = margin_y_b = 100;
        Style = "bottom";
        if(width > 1000 && height < 500)
        {
            Style = "right";
        }
        if(width < 500 && height > 1000)
        {
            Style = "bottom";
        }
        List<JChartLibSerie> l = copySet.getSeries();
        for(int i = 0; i < l.size(); i ++)
        {
            if(i >= titles.length)
                break;
            titles[i] = l.get(i).getTitle();
        }
        ret.setRect(margin_x_l, margin_y_u, width - margin_x_l 
                - margin_x_r, height - margin_y_u - margin_y_b);
        //Set DataName Area
        if(Style.equals("right"))
        {
            int total_height = 0;
            for(int i = 0; i < titles.length; i ++)
            {
                if(titles[i] != null)
                {
                    DataNameareas[i] = new Rectangle2D.Double();
                    DataNameareas[i].setRect(ret.getMaxX(),ret.getMinY() + total_height, 
                            width - ret.getMaxX(), 20 + 30);
                    total_height += 50;
                }
            }
        }
        if(Style.equals("bottom"))
        {
            int total_width = 0;
            for(int i = 0; i < titles.length; i ++)
            {
                if(titles[i] != null)
                {
                    DataNameareas[i] = new Rectangle2D.Double();
                    DataNameareas[i].setRect(ret.getX() + total_width
                            , ret.getMaxY() + 20,titles[i].length() * 12 
                            + 30,30);
                    total_width += titles[i].length() * 12 + 30;
                }
            }
        }
        titlearea.setRect(0,0,width,ret.getMinY());
        return ret;
    }
    public void DrawNotes(Graphics2D g)
    {
        for(int i = 0;i < DataConsts.max_Cur; i ++)
        {
            if(DataNameareas[i] != null)
            {
                Java2d_Draw_Util.drawFilledRectangleWithShadow
                (g, DataNameareas[i].getX(),DataNameareas[i].getY(),
                        DataNameareas[i].getWidth(),DataNameareas[i].getHeight(),Color.WHITE);
                if(Style.equals("bottom"))
                {
                    Java2d_Draw_Util.drawFilledTopRoundedRectangleWithShadow
                    (g,DataNameareas[i].getX(),DataNameareas[i].getY() + 10,30,20,bcolor[i]);
                    Rectangle2D rct = new Rectangle2D.Double();
                    rct.setRect(DataNameareas[i].getX() + 30, 
                            DataNameareas[i].getY(),DataNameareas[i].getWidth() - 30,
                            DataNameareas[i].getHeight());
                    Java2d_Draw_Util.drawTextCenteredBottom
                    (g,rct,0.0f,titles[i],Font.getFont("Arial"),Color.BLACK);
                }
                else
                {
                    Java2d_Draw_Util.drawFilledTopRoundedRectangleWithShadow
                    (g,DataNameareas[i].getX() + 5,DataNameareas[i].getY(),
                            DataNameareas[i].getWidth(),20,bcolor[i]);
                    Rectangle2D rct = new Rectangle2D.Double();
                    rct.setRect(DataNameareas[i].getX(), 
                            DataNameareas[i].getY() + 20,DataNameareas[i].getWidth(),
                            DataNameareas[i].getHeight() - 20);
                    Java2d_Draw_Util.drawTextCenteredBottom
                    (g,rct,0.0f,titles[i],Font.getFont("Arial"),Color.BLACK);
                }
                
            }
        }
        Java2d_Draw_Util.drawTextCenteredBottom(g,titlearea,0,this.getTitle(),
                Font.getFont("Arial"),Color.BLACK);
    }
    public void DrawAxisLineY(Graphics2D g,Rectangle2D area)
    {
        Java2d_Draw_Util.drawLine(g,area.getX(),area.getY(),area.getX(),area.getY() + area.getHeight(),Color.BLACK);
    }
    public void DrawAxisLineX(Graphics2D g,Rectangle2D area)
    {
        Java2d_Draw_Util.drawLine(g,area.getX(),area.getY()
                + area.getHeight(),area.getX() + area.getWidth(),area.getY() + area.getHeight(),Color.BLACK);
    }
    public void GenerateTicks_X()
    {
        List<Object> ret = new ArrayList<Object>();
        List<JChartLibSerie> lj = copySet.getSeries();
        double step = 0;
        double index = 0;
        if(lj.size() > 0)
        {
            int size = 0;
            MyChartSerie jls = (MyChartSerie)lj.get(0);
            size = jls.getValues().size();
            if(size < 10)
            {
                step = 1;
            }
            else
            {
                step = size / 5;
                size = 6;
            }
            if(!jls.IsExtended)
            {
                for(int i = 0; i < size; i ++)
                {
                    ret.add((int)index);
                    index += step;
                }
            }
            else
            {
                if(jls.getXValues().size() > 0)
                {
                    for(int i = 0; i < size;i ++)
                    {
                        ret.add(jls.getXValues().get((int)index));
                        index += step;
                        if(index > jls.getXValues().size() - 1)
                            index  =  jls.getXValues().size() - 1;
                    }
                }
                else
                {
                    for(int i = 0; i < size;i ++)
                    {
                       
                        ret.add((Object)jls.getTextValues().get((int)index));
                        index += step;
                        if(index > jls.getTextValues().size() - 1)
                            index  =  jls.getTextValues().size() - 1;
                    }
                    
                }
            }
        }
   //     System.out.println("lo :" + new Integer(ret.size()).toString());
        lo = ret;
    }
    public void GenerateTicks_Y()
    {
        List<Number> ret = new ArrayList<Number>();
        List<JChartLibSerie> lj = copySet.getSeries();
        double biggest = 0;
        double smallest = Double.MAX_VALUE;
        System.out.println(smallest);
        for(int i = 0; i < lj.size(); i ++)
        {
            double highest = lj.get(i).highestValue();
            double lowest = ((MyChartSerie)lj.get(i)).lowest();
            if(highest > biggest)
                biggest = highest;
            if(lowest < smallest)
                smallest = lowest;
    //        if(smallest == 0)
   //             System.out.println("zero smallest");
        }
        double range = biggest - smallest;
        double begin_time = smallest - 0.1 * Math.abs(range);
        double end_time = biggest + 0.1 * Math.abs(range);
        int steps = 6;
        double range_l = end_time - begin_time;
        double range_step = range_l / ((double)steps - 1);
        for(int i = 0; i < steps; i ++)
        {
            ret.add(begin_time + range_step * (double)i);
   //         System.out.println(begin_time + range_step * (double)i);
        }
        num_max_y = begin_time + range_step * (steps - 1);
        num_min_y = begin_time;
      //  System.out.println("xxx");
     //   System.out.println(num_min_y);
        ln = ret;
    }
    public void DrawAxisMarkY(Graphics2D g,Rectangle2D rect)
    {
       // public int s_margin = 
        int YSize = ln.size();
        double width = rect.getX();
        double cur_d = rect.getY() + rect.getHeight();
        double step = rect.getHeight() / (YSize - 1);
   //     System.out.println(step);
        Rectangle2D tmprct = new Rectangle2D.Double();
  //      tmprct.setRect(0, rect.getY() + rect.getHeight() - 12, rect.getY(), 12);
        for(int i = 0; i < YSize; i ++)
        {
            tmprct.setRect(0,rect.getY() + rect.getHeight() - 12
                    - step * i,rect.getY(), 12);
            Java2d_Draw_Util.drawLine(g,tmprct.getX() - 5,tmprct.getY() + 
                    tmprct.getHeight(),tmprct.getX() + 5,tmprct.getY() 
                    + tmprct.getHeight(),Color.BLACK);
            Java2d_Draw_Util.drawLine(g, tmprct.getX(),tmprct.getMaxY(),rect.getX() + rect.getWidth(),
                    tmprct.getMaxY(), Color.LIGHT_GRAY);
            g.setColor(Color.BLACK);
            g.drawString(new Integer(ln.get(i).intValue()).toString(),(int)tmprct.getX(),(int)(tmprct.getY() + tmprct.getHeight() - 6));
        }
    }
    public void DrawAxisMarkX(Graphics2D g,Rectangle2D rect)
    {
        int XSize = lo.size();
   //     System.out.println(XSize);
        Rectangle2D tmprct = new Rectangle2D.Double();
        
        double step = rect.getWidth() / (double)(XSize - 1);
        for(int i = 0; i < XSize; i ++)
        {
            tmprct.setRect(rect.getX() + i * step,rect.getY() + rect.getHeight(),
                    step,12);    
            Java2d_Draw_Util.drawLine(g,tmprct.getX(),tmprct.getY() - 5, tmprct.getX(),
                    tmprct.getY() + 5,Color.BLACK);
            Java2d_Draw_Util.drawLine(g,tmprct.getX(),rect.getMinY() ,tmprct.getX(),rect.getMaxY(),Color.LIGHT_GRAY);
            g.setColor(Color.BLACK);
            g.drawString(lo.get(i).toString(),(int)tmprct.getX(),
                    (int)tmprct.getY() + 12);
        }
    }
    public double Convert_Point_X(Rectangle2D rct,int index_x,MyChartSerie mcs)
    {
        double range = rct.getWidth();
        double percent = (double)index_x / (double)mcs.numberOfValues();
        return percent * rct.getWidth() + rct.getX();
    }
    public double Convert_Point_Y(Rectangle2D rct,double YValue)
    {
        double range = (Double)num_max_y - (Double)num_min_y;
        double percent = ((YValue - (Double)num_min_y) / range);
        return rct.getY() + rct.getHeight() - percent * rct.getHeight();
    }
    public void DrawPlot(Graphics2D g,Rectangle2D rect,MyChartSerie mcs,Color color)
    {
        int DataCounts = mcs.numberOfValues();
        int curnum = 0;
        Iterator<Number> it = mcs.getValues().iterator();
        double last_x = -1,last_y = -1;
        while(it.hasNext())
        {
            double x = Convert_Point_X(rect,curnum,mcs);
            double y = Convert_Point_Y(rect,it.next().doubleValue());
            if(last_x >= 0)
            {
                Java2d_Draw_Util.drawLine(g,last_x,last_y,x, y, color);
            }
            last_x = x;
            last_y = y;
            curnum ++;           
        }
    }
    public void DrawChart(Graphics2D g,Rectangle2D rect)
    {
        List<JChartLibSerie> l = copySet.getSeries();
        for(int i = 0;i < l.size(); i ++)
        {
            if(i >= DataConsts.max_Cur)
                break;
   //         System.out.println("afdds");
            DrawPlot(g,rect,(MyChartSerie)l.get(i),bcolor[i]);
        }
    }
    public void SaveAsPic(String filepath,int width,int height,String type) throws IOException
    {
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g= (Graphics2D) bi.getGraphics();
        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Axisarea = GetAxisRectangle2D(width,height);
        DrawAxisLineY(g,Axisarea);
        DrawAxisLineX(g,Axisarea);
        GenerateTicks_Y();
        GenerateTicks_X();
        DrawAxisMarkY(g, Axisarea);
        DrawAxisMarkX(g,Axisarea);
        DrawChart(g,Axisarea);
        DrawNotes(g);
        File f = new File(filepath);
        ImageIO.write(bi,type,f); 
    }
}