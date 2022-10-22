package entity;

import java.util.ArrayList;
import java.util.List;

public class Task {
    public int index;
    public double datasize;
    public double referTime;
    public double startTime;
    public double finalTime;
    public List<Task> successor = new ArrayList<>();
    public List<Task> predecessor = new ArrayList<>();
    public Task(int index){
        this.index = index;
    }
}
