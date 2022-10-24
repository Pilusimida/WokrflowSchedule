package start;

import entity.Chromosome;
import entity.Task;
import entity.TaskGraph;
import entity.Type;
import service.Agent;
import service.PopulationManager;

import java.util.*;

import static service.Agent.availableTime;
import static service.Agent.insNumber;


public class Main {
    public static Type[] types = new Type[8];
    public static Task[] tasks;
    public static int instance_number;
    public static int task_number;
    public static void main(String[] args) {
        types[0] = new Type(0, 1.7f, 39321600, 0.06f);
        types[1] = new Type(1, 3.75f, 85196800, 0.12f);
        types[2] = new Type(2, 3.75f, 85196800, 0.113f);
        types[3] = new Type(3, 7.5f, 85196800, 0.24f);
        types[4] = new Type(4, 7.5f, 85196800, 0.225f);
        types[5] = new Type(5, 15f, 131072000, 0.48f);
        types[6] = new Type(6, 15f, 131072000, 0.45f);
        types[7] = new Type(7, 30f, 131072000, 0.9f);
        Random random = new Random();
        Scanner input = new Scanner(System.in);
        System.out.println("Please input the tasks number: ");
        task_number = 6;
//        task_number = input.nextInt();
        System.out.println("Please input the instances number: ");
        instance_number = 6;
//        instance_number = input.nextInt();

        Agent.insNumber=instance_number;
        Agent.typeNumber= 8;
        tasks = new Task[task_number];
        for (int i = 0; i < task_number; i++) {
            tasks[i] = new Task(i);
            tasks[i].datasize = 3.43;
            tasks[i].referTime = 8.44;
        }
        Agent.tasks = tasks;
        Agent.Types = types;
        availableTime = new double[instance_number];
        for (int i = 0; i < instance_number; i++) {
            availableTime[i] = 0;
        }
        TaskGraph graph=new TaskGraph(task_number,tasks);
        graph.addEdge(1,3);
        graph.addEdge(2,3);
        graph.addEdge(3,5);
        graph.addEdge(0,5);
        int[] topologicalArr = graph.TopologicalSorting();

        List<List<Chromosome>> start = PopulationManager.start(topologicalArr);

        int rank=0;
        for(List<Chromosome> list:start){
            System.out.println("----------rank"+(rank++)+"----------");
            for(Chromosome chromosome:list){
                System.out.println("makespan: "+chromosome.makespan+"; cost:" +chromosome.cost);
            }
        }


        System.out.println();


//        int[] instance = new int[task_number];
//        int[] instance2 = new int[task_number];
//        int[] type = new int[task_number];
//        int[] type2 = new int[task_number];
//        for (int i = 0; i < task_number; i++) {
//            instance[i] = random.nextInt(instance_number);
//            instance2[i] = random.nextInt(instance_number);
//        }
//        for (int i = 0; i < task_number; i++) {
//            type[i] = random.nextInt(type_number);
//            type2[i] = random.nextInt(type_number);
//        }
//
//        Chromosome chromosome = new Chromosome(topologicalArr.clone(), instance, type);
//        print(chromosome);
//
//
//        Chromosome chromosome1 = Agent.mutateOrder(chromosome, 0);
//        print(chromosome1);




//        print(chromosome);
//        System.out.println(Agent.makespan(chromosome));
//        System.out.println(Agent.cost(chromosome));
//        clear();
//
//        print(chromosome1);
//        System.out.println(Agent.makespan(chromosome1));
//        System.out.println(Agent.cost(chromosome1));
//        clear();
//
//        System.out.println("After crossover:");
//        Agent.crossoverOrder(chromosome,chromosome1);
//        Agent.crossoverIns(chromosome, chromosome1);
//
//        print(chromosome);
//        System.out.println(Agent.makespan(chromosome));
//        System.out.println(Agent.cost(chromosome));
//        clear();
//
//        print(chromosome1);
//        System.out.println(Agent.makespan(chromosome1));
//        System.out.println(Agent.cost(chromosome1));
//        clear();

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
    public static void clear(){
        availableTime = new double[instance_number];
        for(Task task:tasks){
            task.startTime = 0;
            task.finalTime = 0;
        }
    }
}
