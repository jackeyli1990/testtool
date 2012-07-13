package LogReading;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class WritePic 
{
    public static class Info
    {
        public ArrayList<Object> value;
        public ArrayList<Object> timestamp;
        public int counts;
        public Info()
        {
            value = new ArrayList<Object>();
            timestamp = new ArrayList<Object>();
            counts = 0;
        }
    }
    public static class Info_To_Return
    {
        public ArrayList<Double> value_x;
        public ArrayList<Double> value_y;
        public int counts;
        public Double y_max;
        public Double y_min;
        public Info_To_Return()
        {
            value_x = new ArrayList<Double>();
            value_y = new ArrayList<Double>();
            y_max = Double.valueOf(0);
            y_min = Double.valueOf(0);
        }
        public void AddPoint(Double x,Double y)
        {
            if(x != null)
                value_x.add(x);
            value_y.add(y);
            counts = value_y.size();
        }
    }
    public static class Line_Return
    {
        public double start_x;
        public double start_y;
        public double end_x;
        public double end_y;
        public boolean Isline;
        public Line_Return(double x1,double y1,double x2,double y2,boolean line)
        {
            start_x = x1;start_y = y1;
            end_x = x2;end_y = y2;
            Isline = line;
        }
    }
   public static Line_Return ToLine(Info_To_Return inf,int from,int to)
   {
       long size = to - from + 1;
       double sxy = 0.0;
       double sy = 0.0;
       double sxx = 0.0;
       double sx = 0.0;
       if(size <= 0)
           return null;
       if(size == 1)
           return new Line_Return(inf.value_x.get(from),inf.value_y.get(from),0,0,false);
       if(size == 2)
           return new Line_Return(inf.value_x.get(from),
                   inf.value_y.get(from),
                   inf.value_x.get(to),
                   inf.value_y.get(to),true);
       for(int i = from; i <= to; i ++)
       {
           sxy += inf.value_x.get(i) * inf.value_y.get(i);
           sy += inf.value_y.get(i);
           sx += inf.value_x.get(i);
           sxx += inf.value_x.get(i) * inf.value_x.get(i);
       }
       double b1 = (((double)size) * sxy - sx * sy) / (((double)size) * sx * sx - sxx);
       double b0 = (sy / (double)size) - b1 * (sx / (double)size);
       return new Line_Return(inf.value_x.get(from),
               inf.value_x.get(from) * b1 + b0,
               inf.value_x.get(to),
               inf.value_y.get(to) * b1 + b0,
               true);
   }
   public static Info_To_Return ReduceInfo (Info_To_Return itr,int newcounts)
   {
       int percount = itr.counts / newcounts;
       Info_To_Return nitr = new Info_To_Return();
       nitr.y_max = Double.valueOf(0);
       nitr.y_min = Double.valueOf(0);
       if(percount < 2)
       {
           itr.y_max = itr.value_y.get(0);
           itr.y_min = itr.value_y.get(0);
           for(int i = 0;i < itr.counts;i ++)
           {
               if(itr.y_max < itr.value_y.get(i))
                   itr.y_max = itr.value_y.get(i);
               if(itr.y_min > itr.value_y.get(i))
                   itr.y_min = itr.value_y.get(i);
           }
           return itr;
       }
       else
       {
           int i = 0;
           while(i < itr.counts)
           {   
               int range = itr.counts - i < percount ? itr.counts - i : percount;
               Line_Return lr = ToLine(itr,i,i + range);
               nitr.value_x.add(Double.valueOf(lr.start_x));
               nitr.value_y.add(Double.valueOf(lr.start_y));
               nitr.value_x.add(Double.valueOf(lr.end_x));
               nitr.value_y.add(Double.valueOf(lr.end_y));
               if(lr.start_y > nitr.y_max)
                   nitr.y_max = lr.start_y;
               if(lr.end_y > nitr.y_max)
                   nitr.y_max = lr.end_y;
               i += percount;
           }
       }
       return nitr;
   }
   public static BufferedImage CreatePic(Info_To_Return itr,int wsize,int hsize)
   {
      //  int width = itr.counts * wsize;
      //  System.out.println(width);
     //   System.out.println((int)((itr.y_max - itr.y_min) * (double)(hsize)));
        //BufferedImage bi = new BufferedImage(width, (int)((itr.y_max - itr.y_min) * (double)(hsize)), BufferedImage.TYPE_4BYTE_ABGR);
        BufferedImage bi = new BufferedImage(wsize,hsize,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = bi.getGraphics();
       /* g.setColor(Color.BLUE);
        for(int i = 0; i < itr.counts - 1; i ++)
        {
            int start_x = i * wsize;
            int start_y = (int)((itr.value_y.get(i) - itr.y_min) / (itr.y_max - itr.y_min) * (double)hsize);
            int end_x = start_x + wsize;
            int end_y = (int)((itr.value_y.get(i + 1) - itr.y_min) / (itr.y_max - itr.y_min) * (double)hsize);
            g.drawLine(start_x, start_y, end_x, end_y);
        }*/
        WriteCoordinate(g,wsize,hsize);
        g.dispose();
        return bi;    
   }
   public static void WriteCoordinate(Graphics g,int width,int height)
   {
       g.setColor(Color.BLACK);
       Rectangle rt = new Rectangle(20,20,width - 40,height - 40);
       g.drawLine(rt.x,rt.y + rt.width ,rt.x + rt.width, rt.y + rt.width);
       g.drawLine(rt.x,rt.y,rt.x, rt.y + rt.width);
       Polygon p = new Polygon();
       int[] x = new int[3];
       x[0] = 20;
       x[1] = 10;
       x[2] = 30;
       int[] y = new int[3];
       y[0] = 0;
       y[1] = 20;
       y[2] = 20;
       p.xpoints = x.clone();
       p.ypoints = y.clone();
       x[0] = width;
       x[1] = 20 + rt.width;
       x[2] = 20 + rt.width;
       y[0] = 20 + rt.width;
       y[1] = 10 + rt.width;
       y[2] = 30 + rt.width;
       Polygon p1 = new Polygon();
       p1.xpoints = x.clone();
       p1.ypoints = y.clone();
       p.npoints = p1.npoints = 3;
       g.drawPolygon(p);
       g.drawPolygon(p1);
   }
   public static void ImageToFile(String Path,BufferedImage bi) throws IOException
   {
       File f = new File(Path);
       if(!f.exists())
       {
           f.createNewFile();
       }
       ImageIO.write(bi,"png",f);
   }
}