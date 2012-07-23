package charting;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bitagentur.data.JChartLibSerie;

public class MyChartSerie extends JChartLibSerie
{
    private List<Object> x_values;
    private List<String> Ovalues;
    public boolean IsExtended = true;
    public MyChartSerie(String title) {
        super(title);
        // TODO Auto-generated constructor stub
        x_values = new ArrayList<Object>();
        Ovalues = new ArrayList<String>();
    }
    public MyChartSerie(String title,int[] x)
    {
        super(title,x);
        x_values = new ArrayList<Object>();
        Ovalues = new ArrayList<String>();
    }
    public boolean hasDatevalues()
    {
        return true;
    }
    public void AddValues(Number x,Number y)
    {
        x_values.add(x);
       // super.addValue(new Date(100), y);
        super.addValue(y);
    }
    public void AddValues(String x,Number y)
    {
        Ovalues.add(x);
       // super.addValue(new Date(100), y);
        super.addValue(y);
    }
    public double lowest()
    {        
        double lowest = Double.MAX_VALUE;
        Iterator<Number> it = this.getValues().iterator();
        while(it.hasNext())
        {
            double value = it.next().doubleValue();
            if(lowest > value)
            {
                lowest = value;
            }
        }
        return lowest;
    }
    public List<Object> getXValues()
    {
        return x_values;
    }
    public List<String> getTextValues()
    {
        return Ovalues;
    }
}