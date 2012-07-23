package charting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bitagentur.data.JChartLibSerie;

public class MyChartTimeSkilSerie extends JChartLibSerie
{
    public List<Number> number; 
    public MyChartTimeSkilSerie(String title) {
        super(title);
        // TODO Auto-generated constructor stub
        number = new ArrayList<Number>();
    }
    public MyChartTimeSkilSerie(String title,int[] x)
    {
        super(title,x);
        number = new ArrayList<Number>();
    }
    /*the data must be sorted
     * time skil pattern:
     * hour : minute : seconds
     */
    public void AddValues(String timeskil,Number data)
    {
        int hour = Integer.parseInt(timeskil.substring(0,
                timeskil.indexOf(":",0)));
       /* int minute = Integer.parseInt(timeskil.substring(0,
                timeskil.indexOf(":"),));*/
        
    }
}