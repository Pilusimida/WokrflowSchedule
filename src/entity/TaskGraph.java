package entity;

import java.util.*;

/**
 * @Description: A class to construct input tasks' DAG, which can also do Topological sorting
 */
public class TaskGraph {
    private int size;
    Vertex start;
    Vertex end = new Vertex(Integer.MAX_VALUE);
    Vertex[] vertices;

    public TaskGraph(int n) {
        vertices = new Vertex[n + 1];
        for (int i = 0; i < n + 1; ++i) {
            vertices[i] = new Vertex(i);
        }
        start = vertices[0];
        size = n;
    }

    //添加有向边：ver1->ver2
    public void addEdge(int ver1, int ver2) {
        vertices[ver1].next.add(vertices[ver2]);
        vertices[ver2].inner++;
        vertices[ver1].outer++;
    }

    public int[] TopologicalSorting() {
        //起始节点与终止节点分别对入度为0，出度为0的节点进行连接
        for (Vertex vertex : vertices) {
            if (vertex == null || vertex == start) continue;
            if (vertex.inner == 0) {
                start.next.add(vertex);
                start.outer++;
                vertex.inner++;
            }
            if (vertex.outer == 0) {
                vertex.next.add(end);
                vertex.outer++;
                end.inner++;
            }
        }
        int[] ans = new int[size + 1];
        int k = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int index=queue.poll();
            if(index==end.id) break;
            Vertex temp = vertices[index];
            ans[k++] = temp.id;
            for (Vertex vertex : temp.next) {
                //伪删除前驱节点
                vertex.inner -= 1;
                if (!vertex.isVisited) {
                    if (vertex.inner == 0) {
                        queue.add(vertex.id);
                        vertex.isVisited = true;
                    }
                }
            }
        }
        return Arrays.copyOfRange(ans,1,ans.length);
    }


}

class Vertex {
    //节点入度
    int id;
    int inner = 0;
    int outer = 0;
    boolean isVisited = false;
    List<Vertex> next = new ArrayList<>();

    public Vertex(int id) {
        this.id = id;
    }
}
