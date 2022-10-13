package entity;

/**
 * This class is entity.Chromosome in EMO, which has 3 Strings:\n
 * 1. Order: a vector containing a permutation of all task indexes.
 * 2. Ind:
 * 3. Type:
 */
public class Chromosome {
    public int[] order;
    public int[] task2ins;
    public int[] ins2type;
    public Chromosome(int[] Order, int[] Ins, int[] Type){
        this.order = Order;
        this.task2ins = Ins;
        this.ins2type = Type;
    }
    public Chromosome(){

    }

}
