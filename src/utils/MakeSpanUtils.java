package utils;

import com.sun.tools.javac.Main;
import entity.Chromosome;
import entity.Task;
import entity.Type;

public class MakeSpanUtils {
    public static Type[] types;

    public static double getCompTime(double referTime,double cu){
        return referTime/cu;
    }

    public static double getCommTime(double dataSize,double brandWidth){
        return dataSize/brandWidth;
    }

    public static double getStartTime(double availableTime, Task task, double dataSize, double brandWidth, Chromosome chromosome){
        double max = 0;
        for(Task temp:task.predecessor){
            double bw = Math.min(brandWidth,types[task.index].bw);
            double commTime = getCommTime(dataSize,bw);
            max = Math.max(max,commTime+temp.finalTime);
        }
        return Math.max(availableTime,max);
    }


}
