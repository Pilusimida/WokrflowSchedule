package entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is entity.Chromosome in EMO, which has 3 Strings:\n
 * 1. Order: a vector containing a permutation of all task indexes.
 * 2. Ind:
 * 3. Type:
 */
public class Chromosome implements MyCloneable{
    public int[] order;
    public int[] task2ins;
    public int[] ins2type;

    public List<Chromosome> better;
    public List<Chromosome> worse;

    public double makespan;
    public double cost;
    public Chromosome(int[] Order, int[] Ins, int[] Type){
        this.order = Order;
        this.task2ins = Ins;
        this.ins2type = Type;
        better=new ArrayList<>();
        worse=new ArrayList<>();
    }
    public Chromosome(){
        better=new ArrayList<>();
        worse=new ArrayList<>();
    }

    @Override
    public boolean equals(Object o){
        Chromosome chromosome1=(Chromosome) o;
        for(int i=0;i<order.length;++i){
            if(!(order[i]==chromosome1.order[i]
            && ins2type[i]==chromosome1.ins2type[i]
            && task2ins[i]==chromosome1.task2ins[i]))
                return false;
        }
        return true;
    }


    @Override
    public Chromosome cloneObject(int k) {
        Chromosome newc = new Chromosome();
        int[] norder=order.clone();
        int[] ntask2ins = task2ins.clone();
        int[] nins2type = ins2type.clone();
        newc.better=new ArrayList<>();

        newc.order=norder;
        newc.task2ins=ntask2ins;
        newc.ins2type=nins2type;
        newc.makespan=makespan;
        newc.cost=cost;

        if(k>2) return newc;
        k++;
        for(Chromosome chromosome:better){
            newc.better.add(chromosome.cloneObject(k));
        }
        newc.worse=new ArrayList<>();
        for(Chromosome chromosome:worse){
            newc.worse.add(chromosome.cloneObject(k));
        }

        return newc;
    }
}
