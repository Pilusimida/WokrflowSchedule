package service;

import entity.Chromosome;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class PopulationManager {

    private static int quantity;
    private static int generation;

    private static double mutateProbability;
    private static List<Chromosome> bank = new ArrayList<>();
    private static List<List<Chromosome>> rank = new ArrayList<>();

    static {
        ResourceBundle bundle=ResourceBundle.getBundle("config");
        quantity = Integer.parseInt(bundle.getString("evolution.population"));
        generation = Integer.parseInt(bundle.getString("evolution.generation"));
        mutateProbability = Double.parseDouble(bundle.getString("evolution.mutate"));

    }

    /**
     * 初始化种群
     * @param task
     */
    public static void initialPopulation(int[] task){
        int[] instance = Agent.getRandomIns();
        if(instance.length==0){
            System.out.println();
        }
        int[] type = Agent.getRandomType();
        Random random=new Random();
        Chromosome chromosome=new Chromosome();
        chromosome.task2ins = instance;
        chromosome.ins2type = type;
        chromosome.order = task;
        bank.add(chromosome);
        for(int i=0;i<quantity-1;++i){
            chromosome = Agent.mutateOrder(chromosome,random.nextInt(task.length));
            instance = Agent.getRandomIns();
            type = Agent.getRandomType();
            chromosome.task2ins = instance;
            chromosome.ins2type = type;
            bank.add(chromosome);
        }
    }

    public static void evaluate(){
        bank.forEach(x->{
            x.makespan=Agent.makespan(x);
            x.cost = Agent.cost(x);
        });
    }
    public static void reproduce(){
        List<Chromosome> newBank=cloneList(bank);
        for(int i=0;i<newBank.size();++i){
            for(int j=i+1;j<newBank.size();++j){
                Agent.crossoverOrder(newBank.get(i),newBank.get(j));
                Agent.crossoverIns(newBank.get(i),newBank.get(j));
                if(isMutate()) {
                    Agent.mutateOrder(newBank.get(i),new Random().nextInt(newBank.get(i).order.length));
                    Agent.mutateIns(newBank.get(i));
                }
            }
        }
        bank.addAll(newBank);
    }

    public static List<Chromosome> cloneList(List<Chromosome> list){
        List<Chromosome> ans=new ArrayList<>();
        for(Chromosome chromosome:list){
            ans.add(chromosome.cloneObject());
        }
        return ans;
    }

    public static void sort(){
        for(int i=0;i<bank.size();i++){
            Chromosome chromosome=bank.get(i);
            for(int j=i+1;j<bank.size();j++){
                Chromosome temp=bank.get(j);
                if(chromosome.makespan<temp.makespan&&chromosome.cost<temp.cost){
                    chromosome.worse.add(temp);
                    temp.better.add(chromosome);
                }
                if(chromosome.makespan>temp.makespan&&chromosome.cost>temp.cost){
                    chromosome.better.add(temp);
                    temp.worse.add(chromosome);
                }
            }
        }
        List<Chromosome> newBankTemp=cloneList(bank);
        CopyOnWriteArrayList<Chromosome> newBank=new CopyOnWriteArrayList<>(newBankTemp);
        int r = 0;
        while (!newBank.isEmpty()) {
            rank.add(new LinkedList<>());
            for (Chromosome chromosome : newBank) {
                if (chromosome.better.size() == 0) {
                    newBank.remove(chromosome);
                    rank.get(r).add(chromosome);
                }
            }
            for(Chromosome chromosome:rank.get(r)){
                for(Chromosome worse:chromosome.worse){
                    worse.better.remove(chromosome);
                }
            }
            r++;
        }
    }

    public static void eliminate(){
        List<Chromosome> newList = new ArrayList<>();
        int num;
        for(List<Chromosome> list:rank){
            if(list.size()+newList.size()<=quantity){
                newList.addAll(list);
            }else {
                num = quantity - (list.size()+newList.size());
                for(int i=0;i<num;++i){
                    newList.add(list.get(i));
                }
            }
        }
        bank = newList;
    }

    public static List<List<Chromosome>> start(int[] task){
        initialPopulation(task);
        for(int i=0;i<generation;++i) {
            reproduce();
            System.out.println("Generation i: ");
            evaluate();
            for (Chromosome s : bank) {
                System.out.println(s.cost + ";" + s.makespan);
            }
            sort();
            System.out.println("Sorting finished.");
            eliminate();
            System.out.println(i);
        }
        return rank;
    }

    public static boolean isMutate(){
        Random random=new Random();
        int standard = random.nextInt(10000);
        return mutateProbability * 10000 > standard;
    }



}
