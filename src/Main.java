import entity.Chromosome;
import entity.Task;
import entity.TaskGraph;
import service.Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the tasks number: ");
        int task_number = input.nextInt();
        System.out.println("Please input the instances number: ");
        int instance_number = input.nextInt();
        System.out.println("Please input the types number: ");
        int type_number = input.nextInt();
        Task[] tasks = new Task[task_number];
        for (int i = 0; i < task_number; i++) {
            tasks[i] = new Task(i);
        }
        Agent.tasks = tasks;
        TaskGraph graph=new TaskGraph(task_number,tasks);
        graph.addEdge(1,3);
        graph.addEdge(2,3);
        int[] topologicalArr = graph.TopologicalSorting();
        int[] instance = new int[task_number];
        int[] instance2 = new int[task_number];
        int[] type = new int[task_number];
        int[] type2 = new int[task_number];
        for (int i = 0; i < task_number; i++) {
            instance[i] = random.nextInt(instance_number);
            instance2[i] = random.nextInt(instance_number);
        }
        for (int i = 0; i < task_number; i++) {
            type[i] = random.nextInt(type_number);
            type2[i] = random.nextInt(type_number);
        }

        Chromosome chromosome = new Chromosome(topologicalArr.clone(), instance, type);
        Chromosome chromosome1 = new Chromosome(topologicalArr.clone(), instance2, type2);
        for (int i = 0; i < task_number; i++) {
            Agent.mutateOrder(chromosome1, i);
        }
        print(chromosome);
        print(chromosome1);
        System.out.println("After crossover:");
        Agent.crossoverOrder(chromosome,chromosome1);
        print(chromosome);
        print(chromosome1);





        Agent.tasks = tasks;

    }
    public static void print(Chromosome chromosome){
        System.out.println("-----------------------------");
        System.out.println("Order: ");
        Arrays.stream(chromosome.order).forEach(x->System.out.print(x+" "));
        System.out.println();
        System.out.println("task2ins: ");
        Arrays.stream(chromosome.task2ins).forEach(x->System.out.print(x+" "));
        System.out.println();
        System.out.println("ins2type: ");
        Arrays.stream(chromosome.ins2type).forEach(x->System.out.print(x+" "));
        System.out.println();
        System.out.println("-----------------------------");
    }
}
