package test_banch;

import entity.TaskGraph;
import service.Agent;

import java.util.Arrays;

public class TopologicalTest {
    public static void main(String[] args) {
        int[] arr=Agent.getTopologicalTaskArr();
        Arrays.stream(arr).forEach(System.out::println);
    }
}
