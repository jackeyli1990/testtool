package LogReading;

import java.awt.image.BufferedImage;
import java.io.IOException;

import LogReading.WritePic.Info_To_Return;

public class LogReading
{
    public static void  main(String[] args) throws IOException
    {
       /* Info_To_Return itr = new Info_To_Return();
        itr.value_x.add(Double.valueOf(0));
        itr.value_y.add(Double.valueOf(4.667));
        itr.value_x.add(Double.valueOf(1));
        itr.value_y.add(Double.valueOf(14.667));
        itr.counts = itr.value_x.size();
        itr.y_max = Double.valueOf(14.667);
        itr.y_min = Double.valueOf(4.667);*/
        BufferedImage bi = WritePic.CreatePic(null,500,500);
        WritePic.ImageToFile("newimg.png", bi);
    }
}