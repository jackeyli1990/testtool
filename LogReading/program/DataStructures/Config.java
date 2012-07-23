package DataStructures;

import java.util.ArrayList;
import java.util.List;

import DataStructures.UnitConvertStruct.UnitGroup;
import DataStructures.UnitConvertStruct.UnitGroupCenter;
import DataStructures.UnitConvertStruct.UnitGroupItem;
import LogReading.ConfigStreamer;

public class Config extends BaseConfig
{
    /*define the units of the line*/
    public UnitGroupCenter ugc;
    /*the datanames (easy to display)*/
    public String[] datanames;
    /*about the Input and OutputFile*/
    public String[] stable_configs;
    public Double[] double_values;
    public Integer[] Integervalues;
    private List<Integer> unitlengths = null;
    public Config()
    {
        stable_configs = new String[DataConsts.Str_stable_properties.length];
        double_values = new Double[DataConsts.Double_stable_properties.length];
        Integervalues = new Integer[DataConsts.Integer_stable_properties.length];
        datanames = new String[DataConsts.max_Cur];
        for(int i = 0; i < stable_configs.length; i ++)
        {
            stable_configs[i] = null;
        }
        for(int i = 0; i < double_values.length; i ++)
        {
            double_values[i] = null;
        }
        for(int i = 0; i < Integervalues.length; i ++)
        {
            Integervalues[i] = null;
        }
        for(int i = 0; i < datanames.length; i ++)
        {
            datanames[i] = null;
        }
        ugc = new UnitGroupCenter();
    }
    @Override
    public void Set(Object obj, Object arg) {
        // TODO Auto-generated method stub
        for(int i = 0; i < DataConsts.Str_stable_properties.length; i ++)
        {
            if(DataConsts.Str_stable_properties[i].equals(arg.toString()))
            {
                stable_configs[i] = obj.toString();
                return;
            }
        }
        for(int i = 0; i < DataConsts.Double_stable_properties.length; i ++)
        {
            if(DataConsts.Double_stable_properties[i].equals(arg.toString()))
            {
                double_values[i] = Double.parseDouble(obj.toString());
                return;
            }
        }
        for(int i = 0;i < DataConsts.Integer_stable_properties.length; i++)
        {
            if(DataConsts.Integer_stable_properties[i].equals(arg.toString()))
            {
                Integervalues[i] = Integer.parseInt(obj.toString());
                return;
            }
        }
        for(int i = 0; i < datanames.length; i ++)
        {
           if((DataConsts.dataname_lable + String.valueOf(i)).equals(arg.toString()))
           {
               datanames[i] = obj.toString();
               return;
           }
        }
        UnitGroupHelper.AddUnit(this,arg.toString(),Double.parseDouble(obj.toString()));
    }

    @Override
    public Object Get(Object obj) {
        // TODO Auto-generated method stub
        for(int i = 0; i < DataConsts.Str_stable_properties.length; i ++)
        {
            if(DataConsts.Str_stable_properties[i].equals(obj.toString()))
            {
                return stable_configs[i];
            }
        }
        for(int i = 0; i < DataConsts.Double_stable_properties.length; i ++)
        {
            if(DataConsts.Double_stable_properties[i].equals(obj.toString()))
            {
                return double_values[i];
            }
        }
        for(int i = 0; i < DataConsts.Integer_stable_properties.length; i ++)
        {
            if(DataConsts.Integer_stable_properties[i].equals(obj.toString()))
            {
                return Integervalues[i];
            }
        }
        for(int i = 0; i < datanames.length; i ++)
        {
            if(datanames[i] != null)
            {
                if((DataConsts.dataname_lable + String.valueOf(i)).equals(obj.toString()))
                {
                    return datanames[i];
                }
            }
        }
        return UnitGroupHelper.GetUnit(this, obj.toString());
    }
    public CoorConf GetCoorConf()
    {
        Integer x_value_column = (Integer)Get(DataConsts.Config_x_value_column);
        if(x_value_column == null)
        {
            return new CoorConf(-1,-1,-1);
        }
        else
        {
            String type = (String)Get((Object)(DataConsts.Config_x_value_type));
            if(type == null ||
                    type.equals(DataConsts.Value_Type_Number))
            {
                return new CoorConf(x_value_column,-1,-1);
            }
            else if(type.equals(DataConsts.Value_Type_Date))
            {
                return new CoorConf(-1,-1,x_value_column);
            }
            else
            {
                return new CoorConf(-1,x_value_column,-1);
            }
        }
    }
    protected List<Integer> GetUnitLengthes()
    {
        if(unitlengths == null)
            RefreshUnitLengthes();
        return unitlengths;
    }
    private void RefreshUnitLengthes()
    {
        unitlengths  = ugc.UnitLengths();
    }
}