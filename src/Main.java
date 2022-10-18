import entity.Task;
import entity.TaskGraph;
import service.Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the tasks number: ");
        int task_number = input.nextInt();
        System.out.println("Please input the instances number: ");
        int instance_number = input.nextInt();
        Task[] tasks = new Task[task_number + 2];
        for (int i = 0; i <= task_number + 1; i++) {
            tasks[i] = new Task(i);
        }
        Agent.tasks = tasks;

    }
}
