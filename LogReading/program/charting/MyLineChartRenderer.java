package charting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bitagentur.chart.JChartLibBaseChart;
import com.bitagentur.data.JChartLibSerie;
import com.bitagentur.renderer.JChartLibLinechartRenderer;
import com.bitagentur.renderer.Java2d_Draw_Util;

public class MyLineChartRenderer extends JChartLibLinechartRenderer
{
    private JChartLibBaseChart jlbc;
    public MyLineChartRenderer(JChartLibBaseChart chart) {
        super(chart);
        jlbc = chart;
        // TODO Auto-generated constructor stub
    }
    public List<String> generateTicksDatevalues(int t,JChartLibSerie jls)
    {
        ArrayList<String> res = new ArrayList<String>();
        if(jls.getClass().equals(new MyChartSerie("123").getClass()))
        {
            MyChartSerie mct = (MyChartSerie)jls;
            if(mct.IsExtended)
            {
            int size = super.generateTicks(t).size();
            System.out.println(size);
            List<Object> x_v = mct.getXValues();
            LinkedList<Object> copy = new LinkedList<Object>();
            copy.addAll(x_v);
            int x_sizes = copy.size();
            System.out.println(x_sizes);
            int counts = mct.getValues().size();
            double step = (double)x_sizes / (double)size;
            double index = 0;
            for(int i = 0; i < 3 ; i ++)
            {
                res.add(String.valueOf(x_v.get((int)index)));
                index += step;
                System.out.println(index);
            }
            return res;
            }
            else
            {
                System.out.println("from here");
                return super.generateTicksDatevalues(t,jls);
            }
        }
        else
            return super.generateTicksDatevalues(t,jls);
    }
}