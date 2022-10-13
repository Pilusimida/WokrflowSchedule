import java.util.Arrays;
import java.util.Random;

public class Agent {
    public static Random random = new Random();
    public static Task[] tasks;

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
            orderA[i] = A.order[i];
            orderB[i] = B.order[i];
        }
        //这里我产生了一个问题，会不会在Chromosome里面把int[]改成List会更好
        for (int i : A.order) {
            if (!Arrays.asList(orderA).contains(i)) {
                cursorA++;
                orderA[cursorA] = i;
            }
        }
        for (int i : B.order) {
            if (!Arrays.asList(orderB).contains(i)) {
                cursorB++;
                orderB[cursorB] = i;
            }
        }

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

    public static void mutateOrder(Chromosome X, int pos) {
        int n = X.order.length;
        Task task = tasks[X.order[pos]];
        int start = pos;
        int end = pos;
        while (start >= 0 && !tasks[pos].predecessor.contains(tasks[X.order[start]])) {
            start--;
        }
        while (end < n && !tasks[pos].successor.contains(tasks[X.order[end]])) {
            end++;
        }
        int posN = random.nextInt(end - start - 1) + start + 1;
        int temp = X.order[pos];
        X.order[pos] = X.order[posN];
        X.order[posN] = temp;
    }
    // im not sure if the [mutate rate] of genes is correct or not
    public static void mutateIns(Chromosome X) {
        int number = X.task2ins.length;
        int p = random.nextInt(number);//generate the position where mutate occurs
        int instance = random.nextInt(m);//m is the number of instances available
        X.task2ins[p] = instance;
    }

    public static void mutateType(Chromosome X) {
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
    }

}
