package service;

import entity.Chromosome;
import entity.Task;
import entity.TaskGraph;
import entity.Type;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import utils.MakeSpanUtils;

public class Agent {
    public static int insNumber;
    public static int typeNumber;
    public static Random random = new Random();
    public static Task[] tasks;
    public static Type[] Types;
    public static double[] availableTime;

    public static int[] getTopologicalTaskArrByConsole() {
        Scanner input = new Scanner(System.in);
        System.out.println("--------------------Topological Sorting--------------------");
        System.out.println("Please input the task quantity:");
        TaskGraph graph = new TaskGraph(input.nextInt(), tasks);
        System.out.println("Please input the task priority (x to finish the input):");
        while (true) {
            String str1 = input.next();
            if (str1.equals("x")) break;
            String str2 = input.next();
            input.nextLine();
            System.out.println("Edge from " + str1 + " to " + str2 + " has been built.");
            graph.addEdge(Integer.parseInt(str1), Integer.parseInt(str2));
        }
        return graph.TopologicalSorting();
    }


    /**
     * description:
     *
     * @Format: File should contain total Num in the first line, and for each line has a "x y" means there is an edge from x -> y
     * @Param: [path]
     * @Return: int[]
     */
    public static int[] getTopologicalTaskArrByFile(String path) {
        System.out.println("--------------------Topological Sorting--------------------");
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File is not found!");
            return null;
        }
        TaskGraph graph;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int num = Integer.parseInt(reader.readLine());
            graph = new TaskGraph(num, tasks);
            String edges;
            while ((edges = reader.readLine()) != null) {
                String[] e = edges.split(" ");
                graph.addEdge(Integer.parseInt(e[0]), Integer.parseInt(e[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("-------------------------Finished--------------------------");
        return graph.TopologicalSorting();
    }

    public static void crossoverOrder(Chromosome A, Chromosome B) {
        //n is the number of tasks
        int n = A.order.length;
        //random is a random number generator
        //p is the cut position
        int p = random.nextInt(n);
        int cursorA = p;
        int cursorB = p;
        int[] orderA = new int[n];
        int[] orderB = new int[n];
        //
        for (int i = 0; i <= p; i++) {
            orderA[i] = B.order[i];
            orderB[i] = A.order[i];
        }
        //这里我产生了一个问题，会不会在Chromosome里面把int[]改成List会更好
        for (int num : A.order) {
            if (!isContains(orderA, 0, p, num)) orderA[cursorA++] = num;
        }

        for (int num : B.order) {
            if (!isContains(orderB, 0, p, num)) orderB[cursorB++] = num;
        }
        A.order = orderA;
        B.order = orderB;
    }

    public static boolean isContains(int[] arr, int start, int end, int num) {
        for (int i = start; i <= end; ++i) {
            if (arr[i] == num) return true;
        }
        return false;
    }

    public static void crossoverIns(Chromosome A, Chromosome B) {
        int n = A.order.length;
        int p = random.nextInt(n);

        for (int i = 0; i < p; i++) {
            int task = A.order[i];
            decideType(A, B, task, p);
            decideType(B, A, task, p);
            int temp = A.task2ins[task];
            A.task2ins[task] = B.task2ins[task];
            B.task2ins[task] = temp;
        }
    }

    public static void crossoverType() {
        // From my perspective the type according to a instance can't be easily changed or crossed over.
    }

    public static Chromosome mutateOrder(Chromosome X, int pos) {
        Chromosome nc = new Chromosome(X.order.clone(), X.task2ins.clone(), X.ins2type.clone());
        int n = nc.order.length;
        Task task = tasks[nc.order[pos]];
        int start = pos;
        int end = pos;
        while (start >= 0 && !task.predecessor.contains(tasks[nc.order[start]])) {
            start--;
        }
        while (end < n && !task.successor.contains(tasks[nc.order[end]])) {
            end++;
        }
        int posN = random.nextInt(end - start - 1) + start + 1;
        int temp = nc.order[pos];
        if (posN < pos) {
            for (int i = pos; i > posN; i--) {
                nc.order[i] = nc.order[i-1];
            }
        } else if (pos < posN) {
            for (int i = pos ; i < posN; i++) {
                nc.order[i] = nc.order[i+1];
            }
        }
        nc.order[posN] = temp;
        return nc;
    }

    public static int[] getRandomIns(){
        int[] instance = new int[insNumber];
        for (int i = 0; i < insNumber; i++) {
            instance[i] = random.nextInt(insNumber);
        }
        return instance;
    }

    public static int[] getRandomType(){
        int[] type = new int[typeNumber];
        for (int i = 0; i < typeNumber; i++) {
            type[i] = random.nextInt(typeNumber);
        }
        return type;
    }

    // im not sure if the [mutate rate] of genes is correct or not
    public static void mutateIns(Chromosome X) {
        int number = X.task2ins.length;
        int p = random.nextInt(number);//generate the position where mutate occurs
        int instance = random.nextInt(insNumber);//m is the number of instances available
        X.task2ins[p] = instance;
    }

    public static void mutateType(Chromosome X, int m) {
        int number = X.ins2type.length;
        int p = random.nextInt(number);//generate the position where mutate occurs
        int instance = random.nextInt(m);//m is the number of instances available
        X.ins2type[p] = instance;
    }

    public static void decideType(Chromosome A, Chromosome B, int task, int p) {
        int Instance = A.task2ins[task];
        int TypeA = A.ins2type[Instance];
        int TypeB = B.ins2type[Instance];
        for (int i = p; i < A.order.length; i++) {
            if (B.task2ins[task] == Instance) {
                if (TypeA != TypeB) {
                    int r = random.nextInt(2);
                    B.ins2type[Instance] = r == 0 ? TypeA : TypeB;
                    return;
                }
            }
        }
        B.ins2type[Instance] = TypeA;
        //mutate Pa with a small probability
        int r = random.nextInt(1000);
        if (r == 1) {
            TypeA = random.nextInt(typeNumber);
        }
    }

    public static double makespan(Chromosome chromosome) {
        Type[] types = new Type[tasks.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = Types[chromosome.ins2type[chromosome.task2ins[i]]];
        }
        MakeSpanUtils.types = types;
        double exitTime = 0;
        for (Task task : tasks) {
            int insIndex = chromosome.task2ins[task.index];
            int typeIndex = chromosome.ins2type[chromosome.task2ins[task.index]];
            if (task.predecessor.size() == 0) {
                task.startTime = Math.max(0, availableTime[insIndex]);
                task.finalTime = task.startTime + MakeSpanUtils.getCompTime(task.referTime, Types[typeIndex].cu);
                availableTime[insIndex] = task.finalTime;
            } else {
                task.startTime = MakeSpanUtils.getStartTime(availableTime[insIndex], task, task.datasize, Types[typeIndex].bw);
                task.finalTime = task.startTime + MakeSpanUtils.getCompTime(task.referTime, Types[typeIndex].cu);
                availableTime[insIndex] = task.finalTime;
            }
            if (task.successor.size() == 0) {
                exitTime = Math.max(exitTime, task.finalTime);
            }
        }
        return exitTime;

    }

    public static double cost(Chromosome chromosome) {
        double sum = 0;
        for (int i : chromosome.ins2type) {
            sum += Types[i].p;
        }
        return sum;
    }
}
