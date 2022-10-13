package entity;

import java.util.ArrayList;
import java.util.List;

public class Task {
    public int index;
    public float time2complete;
    public float startTime;
    public float finalTime;
    public List<Task> successor = new ArrayList<>();
    public List<Task> predecessor = new ArrayList<>();
    public Task(int index){
        this.index = index;
    }
}
