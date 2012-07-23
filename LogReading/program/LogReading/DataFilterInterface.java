package LogReading;

import DataStructures.BaseCoorConf;
import DataStructures.BaseLineData;
import DataStructures.BaseLineFilter;

public interface DataFilterInterface
{
   // public  BaseLineFilter AnalysisFilter(String str);
    public  BaseLineData CollectLineData(String line);
}