package service;

import entity.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class PopulationManager {

    private static int quantity;
    private static int generation;
    private static List<Chromosome> bank = new ArrayList<>();

    static {
        ResourceBundle bundle=ResourceBundle.getBundle("config");
        quantity = Integer.parseInt(bundle.getString("evolution.population"));
        generation = Integer.parseInt(bundle.getString("evolution.generation"));
    }

    public static void initialPopulation(){
        int[] taskOrder = Agent.getTopologicalTaskArrByFile("");
        int[] instance = Agent.getRandomIns();
        int[] type = Agent.getRandomType();
        Random random=new Random();
        Chromosome chromosome=new Chromosome();
        chromosome.task2ins = instance;
        chromosome.ins2type = type;
        chromosome.order = taskOrder;
        bank.add(chromosome);
        for(int i=0;i<quantity-1;++i){
            chromosome = Agent.mutateOrder(chromosome,random.nextInt());
            instance = Agent.getRandomIns();
            type = Agent.getRandomType();
            chromosome.task2ins = instance;
            chromosome.ins2type = type;
            bank.add(chromosome);
        }
    }





}
