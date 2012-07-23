package DataStructures;

import java.util.ArrayList;

public class LineData extends BaseLineData
{
    public ArrayList<Number> y_valuenum;
    public Number x_value = null;
    public String timskil_place = null;
    public String date_place = null;
    public LineData()
    {
        y_valuenum = new ArrayList<Number>();
    }
}