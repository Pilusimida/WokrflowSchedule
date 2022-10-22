package test_banch;
import service.Agent;

import java.util.Arrays;
import java.util.Random;

public class TopologicalTest {
    public static void main(String[] args) {
        int[] arr=Agent.getTopologicalTaskArr();
        Arrays.stream(arr).forEach(System.out::println);

    }
}
