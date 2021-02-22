import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {

    private int V; //number of vertices
    private LinkedList<Integer> adj[]; //array of list, adjacency list

    Graph(int v){
        //for each vertex a edge linkedlist need to be there
        V=v;
        adj = new LinkedList[v];
        for(int i=0;i<v;i++)
        {
            adj[i] = new LinkedList<Integer>();
        }
    }
    public void addEdge(int v,int w)
    {
        adj[v].add(w);
    }

    public void BFS(int v)
    {
        //traversing from the given node
        //we need to mark the traversed node. creating an array for it
        boolean visited[] = new boolean[V];
        //create a queue for BFS. that's basically linked list
        LinkedList<Integer> queue = new LinkedList<Integer>();

        //lets start adding the nodes and marking them as visited
        //start with current node
        visited[v]=true;
        queue.add(v);

        while(queue.size() != 0) {
            //iterate thro v
            v = queue.poll();
            System.out.println("v : " + v);
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                //check if new node is visited
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
    }

    public void DFS(int v,boolean[] visited)
    {
        if(visited[v] == true)
            return;
        //lets start adding the nodes and marking them as visited
        //start with current node
        visited[v]=true;
        System.out.println(v);
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            DFS(n,visited);
        }
    }

    public boolean isCyclicUtil(int curNode,boolean[] visited, int parent)
    {
        //first set the visited to true
        visited[curNode] = true;
Integer i;
        //get the iterator
        Iterator<Integer> it = adj[curNode].iterator();
        while(it.hasNext())
        {
            i = it.next();

            //recursively check
            if(visited[i] != true)
            {
                if(isCyclicUtil(i,visited,curNode))
                    return true;
            }
            //check if this node is visited and parent and this
            else if(i != parent) {
                return true;
            }
        }
        return false;
    }
    public boolean isCyclicGraph()
    {
        //create a visited array
        boolean[] visited = new boolean[V];
        //initialise to false
        for(int i=0; i<V; i++)
        {
            visited[i] = false;
        }
        //call the util function for all the nodes of the graph
        for(int u=0; u<V; u++)
        {
            if(!visited[u])
                if(isCyclicUtil(u, visited,-1)) //3rd argument is parent, for the first node we don't know the prarent
                    return true;
        }
        //if for all the nodes isCyclicUtil is returning false only we will return false
        return false;
    }
    public static void main(String args[]) {

        int x,y;
        Scanner s = new Scanner(System.in);
        System.out.println("Number of nodes in the graph ");
        int V = s.nextInt();
        Graph g = new Graph(V);
        System.out.println("Key in the node relations. When complete type #");
        while((x = s.next()) != '#')
        {
            y = s.nextInt();
            g.addEdge(x,y);
        }

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(3, 3);

        g.BFS(2);
        boolean visited[]=new boolean[4];
        System.out.println("DFS ");
        g.DFS(2,visited);

        if(g.isCyclicGraph())
            System.out.println("G1 is a cyclic graph");

        Graph g2 = new Graph(4);
        g2.addEdge(0,1);
        g2.addEdge(1,2);
        g2.addEdge(2,3);
        g2.addEdge(3,3);

        if(g2.isCyclicGraph())
            System.out.println("G2 is a cyclic graph");
        else
            System.out.println("G2 is not a cyclic group");
    }
}
