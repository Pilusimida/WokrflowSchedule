package entity;

import java.util.LinkedList;
import java.util.List;

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

    public List<Chromosome> better = new LinkedList<>();
    public List<Chromosome> worse = new LinkedList<>();

    public double makespan;
    public double cost;
    public Chromosome(int[] Order, int[] Ins, int[] Type){
        this.order = Order;
        this.task2ins = Ins;
        this.ins2type = Type;
    }
    public Chromosome(){

    }

    @Override
    public Chromosome cloneObject() {
        Chromosome newc = new Chromosome();
        int[] norder=order.clone();
        int[] ntask2ins = task2ins.clone();
        int[] nins2type = ins2type.clone();
        List<Chromosome> better = better.;
        List<Chromosome> worse = ;
        double nmakwspan = makespan;
        double ncost = cost;

    }
}
