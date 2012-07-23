package DataStructures;
public class lineFilterStruct extends BaseLineFilterItem
{
    public String content;
    public boolean IsData;
    public boolean IsRequiredData;
    public lineFilterStruct(String c,boolean id,boolean ird)
    {
        content = c;
        IsData = id;
        IsRequiredData = ird;
    }
    
}