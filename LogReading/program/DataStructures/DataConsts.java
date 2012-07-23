package DataStructures;
public class DataConsts
{
    public static final String[] Str_stable_properties = 
        {"style","x_value_type",
        "InputFile","OutputFile","BeginMark"
        ,"linelayout","chartname"
        };
    public static final String[] Double_stable_properties = 
        {"XStep","YStep"};
    public static final String[] Integer_stable_properties = 
        {"x_value_column","Width","Height","BeginLine","EndLine"
        };
    public static final String dataname_lable = "dataname_";
    public static final String Value_Type_Number = "number";
    public static final String Value_Type_timestamp = "timestamp";
    public static final String Value_Type_Date = "Date";
    public static final String Config_Style = Str_stable_properties[0];
    public static final String Config_x_value_type = Str_stable_properties[1];
    public static final String Config_InputFile = Str_stable_properties[2];
    public static final String Config_OutputFile = Str_stable_properties[3];
    public static final String Config_BeginMark = Str_stable_properties[4];
    public static final String Config_linelayout = Str_stable_properties[5];
    public static final String Config_chartname = Str_stable_properties[6];
    public static final String Config_XStep = Double_stable_properties[0];
    public static final String Config_YStep = Double_stable_properties[1];
    public static final String Config_x_value_column = Integer_stable_properties[0];
    public static final String Config_Width = Integer_stable_properties[1];
    public static final String Config_Height = Integer_stable_properties[2];
    public static final String  Config_BeginLine = Integer_stable_properties[3];
    public static final String Config_EndLine = Integer_stable_properties[4];
    public static final int max_Cur = 4;
    public static final String DefaultConfigPath="logreading.properties";
    public static class HelpInfomations
    {
        public static final String help = "welcome to help!";
        public static final String command_1 = "command -- 1"; 
        public static final String command_1_info = " java -jar eplot.jar  String(properties path) this will use the given " +
        		"propertie file,must write the InputFile Path and OutputFile Path";
        public static final String command_2 = "command -- 2";
        public static final String command_2_info = 
                "java -jar eplot.jar String(InputFile) String(OutputFile)" +
        		"this will use the default propertie file";
        public static final String command_3 = "command -- 3";
        public static final String command_3_info = "java -jar eplot.jar String(InputFile) String(OutputFile) String(properties path)" +
        		"this will use the given propertie file and the given Input/Output File Path";
        public static final String missunit = "undefined unit";
        public static final String missIn_OutputFile = "missing Input/OutputFile Path,please confirm whether it is" +
        		"written in the propertie file";
        public static final String CannotFindFile = "cannot find file";
        public static final String TomuchDirtyData = "TomuchDirtyData maybe there is wrong linelayout";
        public static final String watchpropertie = "to know the layout of propertie file please look at the readme" +
        		"or type readme";
        public static final String readme="#the property elements:\n"
+"#style=(png or jpg)the pic formate of given logs\n" +
"#InputFile=(filepath)the Ordered InputFilePath(when there is only propertie path given)\n" +
"#OutputFile=(filepath)the Ordered OutputFilePath(when there is only propertie path given)\n"+
"#dataname_(0~2)=(name)the name of one group data;\n" +
"#Width=(width)the width of output pic\n"+
"#Height=(height)the height of output pic\n"+
"#BeginLine=(begin)the startline of scan(given default 0)\n"+
"#EndLine=(end)the endline of scan (-1 means scanning until the endof file),given default -1\n"+
"#linelayout=(pattern)the linelayout of every line of data\n"+
"#(dt) mean the data,(*dt) mean the data which is required to be shown\n"+
"#for example\n"+
"#linelayout=(*dt) (dt) (*dt)S\n"+
"#if the linedata is 'java 7.665 1123S'\n"+
"#then the data collected will be 'java' and '1123'\n"+
"#the continues blank in the linedata will be seen as one\n"+
"#for example\n"+
"#linelayout = (*dt) (dt) (*dt)\n"+
"#if the linedata is 'java          8889     11237'\n"+
"#then the data collected will be 'java' and '11237'\n"+
"#other chars will not be seen as one\n"+
"#for example\n"+
"#linelayout = '(*dt)t(dt)y(*dt)'\n"+
"#if the line data is 'java ttt 8889 yyy 11237'\n"+
"#then the data collected will be 'java' 'tt 8889' and 'yy 11237'\n"+
"#the default XAxis Mark is defined by the sequence index of data\n"+
"#if want to use one row as XAxis Mark then set\n"+
"#x_value_column in properties\n"+
"#for example\n"+
"#if x_value_column=1and the linelayout=(*dt) (dt) (*dt)S\n"+
"#then we will use the second (*dt) as XAxisMark and It will not be shown in the digram\n"+
"#only numbers or numbers + unit are supported in YAxis such as'190' or '190T'\n"+
"#for XAxis,strings are supported such as'1:0:12' and '2012/07/8' or even '1a','1b'..anything else\n"+
"#the define the units\n"+
"#for example\n"+
"#g=1000\n"+
"#if the data collected is '1.0g' it will be seen as '1000.0'\n"+
"#if there is unit that doesn't define we will ignore it\n"+
"#for example\n"+
"#if the data collected is '24.0kg' and didn't define kg in properties\n"+
"#it will be seen as '24.0' and a warnning will be thrown when scanning.\n";
    }
}