package service;

import entity.Chromosome;
import start.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


public class PopulationManager {

    private static int quantity;
    private static int generation;

    private static double mutateProbability;
    private static List<Chromosome> bank;
    private static List<List<Chromosome>> rank;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        quantity = Integer.parseInt(bundle.getString("evolution.population"));
        generation = Integer.parseInt(bundle.getString("evolution.generation"));
        mutateProbability = Double.parseDouble(bundle.getString("evolution.mutate"));
        rank = new ArrayList<>();
        bank = new ArrayList<>();
    }

    public static void initialPopulation(int[] task) {
        int[] instance = Agent.getRandomIns();
        if (instance.length == 0) {
            System.out.println();
        }
        int[] type = Agent.getRandomType();
        Random random = new Random();
        Chromosome chromosome = new Chromosome();
        chromosome.task2ins = instance;
        chromosome.ins2type = type;
        chromosome.order = task;
        bank.add(chromosome);
        for (int i = 0; i < quantity - 1; ++i) {
            chromosome = Agent.mutateOrder(chromosome, random.nextInt(task.length));
            instance = Agent.getRandomIns();
            type = Agent.getRandomType();
            chromosome.task2ins = instance;
            chromosome.ins2type = type;
            bank.add(chromosome);
        }
    }

    public static void evaluate() {
        bank.forEach(x -> {
            x.makespan = Agent.makespan(x);
            x.cost = Agent.cost(x);
        });
    }

    public static void reproduce() {
        List<Chromosome> newBank = cloneList(bank);
        for (int i = 0; i < newBank.size(); ++i) {
            for (int j = i + 1; j < newBank.size(); ++j) {
                Agent.crossoverOrder(newBank.get(i), newBank.get(j));
                Agent.crossoverIns(newBank.get(i), newBank.get(j));
                if (isMutate()) {
                    Agent.mutateOrder(newBank.get(i), new Random().nextInt(newBank.get(i).order.length));
                    Agent.mutateIns(newBank.get(i));
                }
            }
        }
        bank.addAll(newBank);
    }

    public static List<Chromosome> cloneList(List<Chromosome> list) {
        List<Chromosome> ans = new ArrayList<>();
        for (Chromosome chromosome : list) {
            ans.add(chromosome.cloneObject(0));
        }
        return ans;
    }

    public static void sort() {
        for (int i = 0; i < bank.size(); ++i) {
            Chromosome chromosome = bank.get(i);
            for (int j = i + 1; j < bank.size(); ++j) {
                Chromosome temp = bank.get(j);
                if (chromosome.makespan < temp.makespan && chromosome.cost < temp.cost) {
                    chromosome.worse.add(temp);
                    temp.better.add(chromosome);
                }
                if (chromosome.makespan > temp.makespan && chromosome.cost > temp.cost) {
                    chromosome.better.add(temp.cloneObject(0));
                    temp.worse.add(chromosome.cloneObject(0));
                }
            }
        }

        List<Chromosome> newBankTemp = cloneList(bank);
        ArrayList<Chromosome> newBank = new ArrayList<>(newBankTemp);
        int r = 0;
        while (!newBank.isEmpty()) {
            rank.add(new ArrayList<>());
            int i = 0;
            while (i < newBank.size()) {
                if (newBank.get(i).better.size() == 0) {
                    rank.get(rank.size() - 1).add(newBank.get(i));
                    newBank.remove(i);
                    i--;
                }
                i++;
            }

            int j = 0;
            while (j < rank.get(rank.size() - 1).size()) {
                for (Chromosome chromosome : newBank) {
                    delIfExist(chromosome.better, rank.get(rank.size() - 1).get(j));
                    int k = 0;
                    while (k < chromosome.better.size()) {
                        if (chromosome.better.get(k).equals(rank.get(rank.size() - 1).get(j))) {
                            chromosome.better.remove(k);
                            --k;
                        }
                        ++k;
                    }
                }
                j++;
            }

        }
    }

    public static void delIfExist(List<Chromosome> list, Chromosome chromosome) {

    }

    public static int contains(List<Chromosome> list, Chromosome chromosome) {
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).equals(chromosome)) return i;
        }
        return -1;
    }

    public static void eliminate() {
        List<Chromosome> newList = new ArrayList<>();
        int num;
        for (List<Chromosome> list : rank) {
            if (list.size() + newList.size() <= quantity) {
                newList.addAll(list);
            } else {
                num = quantity - (newList.size());
                for (int i = 0; i < num; ++i) {
                    newList.add(list.get(i));
                }
            }
        }
        bank = newList;
    }

    public static List<List<Chromosome>> start(int[] task) {
        initialPopulation(task);
        for (int i = 0; i < generation; ++i) {
            rank = new ArrayList<>();
            Main.clear();
            reproduce();
            evaluate();
            sort();
            eliminate();
        }
        return rank;
    }


    public static boolean isMutate() {
        Random random = new Random();
        int standard = random.nextInt(10000);
        return mutateProbability * 10000 > standard;
    }


}
