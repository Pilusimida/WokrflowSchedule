package test_banch;
import service.Agent;

import java.util.Arrays;
import java.util.Random;

public class TopologicalTest {
    public static void main(String[] args) {
        int[] arr=Agent.getTopologicalTaskArrByFile("C:\\Users\\徐璟源\\Desktop\\Test.txt");
        Arrays.stream(arr).forEach(System.out::println);
    }
}
