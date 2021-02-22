package com.demo.FamilyTree;

public class Edge {
    public Node source;
    public Node destination;
    public String relation;
    public int weight; //derived element
     Edge(Node source,Node destination,String relation)
    {
        this.source = source;
        this.destination = destination;
        this.relation = relation;
        if(relation == "Son")
            this.weight = 2;
        if(relation == "Daughter")
            this.weight = 2;
        if(relation == "Mom")
            this.weight = 2;
        if(relation == "Dad")
            this.weight = 2;
        if(relation == "Partner")
            this.weight = 1;

    }
}
