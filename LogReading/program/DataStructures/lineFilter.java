package DataStructures;

import java.util.ArrayList;


public class lineFilter extends BaseLineFilter
{
    public lineFilter()
    {
        this.al = new ArrayList<BaseLineFilterItem>();
    }
    public int Size()
    {
        return al.size();
    }
    public BaseLineFilterItem get(int index)
    {
        return al.get(index);
    }
    public int GetRequiredSize()
    {
        int res = 0;
        for(int i = 0; i < al.size(); i ++)
        {
            if(((lineFilterStruct)al.get(i)).IsRequiredData == true)
                res ++;
        }
        return res;
    }
}
