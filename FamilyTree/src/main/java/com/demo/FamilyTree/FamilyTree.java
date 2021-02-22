package com.demo.FamilyTree;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashMap;
import java.io.File;

import com.demo.FamilyTree.Edge;

public class FamilyTree {
    //this is going to be a weighted graph
    private int N = 0; //number of family members
    private LinkedList<Node> V; //vertices or persons
    private HashMap<String,LinkedList<Edge>> adj;
    private HashMap<String,Boolean> visited;
    private static final String MALE = "Male";
    private static final String FEMALE = "Female";
    private static final String PERSON_NOT_FOUND_ERR = "PERSON_NOT_FOUND";
    private static final String CHILD_ADDITION_FAILED_ERR = "CHILD_ADDITION_FAILED";
    private static final String NONE = "NONE";

    FamilyTree(){

        V = new LinkedList<Node>();
        adj = new HashMap<String,LinkedList<Edge>>();
        visited = new HashMap<String,Boolean>();
    }
    private void resetVisitedHashMap()
    {
        for(int i = 0; i<N; i++) {
            visited.replace(V.get(i).name, false);
        }
    }
    private void addNode(Node member)
    {
        //System.out.println("Adding node " + member.name);
        V.add(member);
        //everytime we add a node, lets increment N
        N++;
        //create a linked list in adj map;
        adj.put(member.name, new LinkedList<Edge>());
        visited.put(member.name,false); //setting visited to false
    }

    private Node GetNode(String name)
    {
        Iterator<Node> i = V.listIterator();
        while(i.hasNext())
        {
            Node node = i.next();
            if(node.name == name) //later == method will be implemented for Node
                return node;
        }
        return null;
    }
   // public void addEdge(Node source,Node dest,String relation){
   public void addEdge(String srcName,Node dest,String relation){
        //first check if the srcName exists
       Node source = GetNode(srcName);
       if(source == null)
       {
           System.out.println("Cannot add Edge " + PERSON_NOT_FOUND_ERR);
           return;
       }
        //get the adj[parent]. add to it
        LinkedList<Edge> srcRelations= adj.get(srcName);
        LinkedList<Edge> destRelations= adj.get(dest.name);

        if(srcRelations != null)
        {

            //add the relationship
            srcRelations.add(new Edge(source,dest,relation));
            srcRelations = adj.replace(source.name,srcRelations);
        }
        //add an edge in the other way also
        if(destRelations != null) {
            //System.out.println("Adding edge " + dest.name + " to " + source.name);
            String newRelation;
            if((relation == "Son") || (relation == "Daughter")){
                newRelation = "Mom";
            }
            else {
                newRelation = relation;
            }
            destRelations.add(new Edge(dest, source, newRelation));
            destRelations = adj.replace(dest.name, destRelations);

        }

    }
    public boolean isExists(String m){
        Iterator<Node> i = V.listIterator();
        while(i.hasNext())
        {
            Node node = i.next();
            //System.out.println("node name="+node.name+" m="+m);
            //if(node.name == m) //later == method will be implemented for Node
            if(m.equals(node.name))
                return true;
        }
        return false;
    }
    public void addChild(String mom,String child,String gender)
    {
        //check if mother name is valid.
        Node cNode = new Node(child,gender);
        if(isExists(mom) == true){

            //need to check the gender also
            addNode(cNode);
            //in addition to adding a connection to the child and parent,
            //we may need to add some more edges
            // add maternal uncle aunts and paternal uncle aunts
            String relation = (gender == MALE) ? "Son" : "Daughter";
            //System.out.println("Adding relationship " + relation + " to " + mom);
            addEdge(mom, cNode, relation);
            //System.out.println("Added Child " + cNode.name + " to " + mom);
        }
        else {
            System.out.println(CHILD_ADDITION_FAILED_ERR);
        }
    }
    private void GetChildren(String parent,String gender,LinkedList<String> children)
    {
        String v = parent;
        resetVisitedHashMap();
        Iterator<Edge> i = adj.get(v).listIterator();
        while (i.hasNext()) {
            Edge link = i.next();
            //check if new node is visited
            if (!visited.get(link.destination.name)) {
                visited.put(link.destination.name,true) ;
                //check if the given relationship exists
                if((gender == "all") && (link.weight == 2))
                {
                    children.add(link.destination.name);
                }
                else if((link.weight == 2) && link.destination.gender == gender) {
                    children.add(link.destination.name);
                }
            }
        }
    }

    private String GetPartner(String name){
        resetVisitedHashMap();
        Iterator<Edge> i = adj.get(name).listIterator();
        while (i.hasNext()) {
            Edge link = i.next();
            //System.out.println(name +" has link to "+link.destination.name);
            //check if new node is visited
            if (!visited.get(link.destination.name)) {
                visited.put(link.destination.name,true) ;
                //check if the given relationship exists
                if((link.weight == 1)) {
                    return link.destination.name;
                }
            }
        }
        return null;
    }

    private String GetDad(String name){
        //to get dad, get mom first, then get her partner
        String mom = GetMom(name);
        return GetPartner(mom);
    }
    private String GetMom(String name){
        //System.out.println("Getting mom of " + name);
          //find the edge with weight 2
        resetVisitedHashMap();
        Iterator<Edge> i = adj.get(name).listIterator();
        while (i.hasNext()) {
            Edge link = i.next();
            //System.out.println(name +" has link to "+link.destination.name);
            //check if new node is visited
            if (!visited.get(link.destination.name)) {
                visited.put(link.destination.name,true) ;
                //check if the given relationship exists
                if((link.weight == 2) && link.destination.gender == FEMALE) {
                    return link.destination.name;
                }
            }
        }
        return null;
    }
    public void GetSiblings(String name, LinkedList<String> queue,String gender){

        String mom = GetMom(name);
        //System.out.println("Mom of "+name + " "+mom);
        resetVisitedHashMap();
        Iterator<Edge> i = adj.get(mom).listIterator();
        while (i.hasNext()) {
            Edge link = i.next();
            //check if new node is visited
            if (!visited.get(link.destination.name)) {
                visited.put(link.destination.name,true) ;
                //check if the given relationship exists
                if(link.destination.name.equals(name))
                {
                    //we need to ignore this.
                    ;
                }
                else if((link.weight == 2)) {
                    if(gender == "A") {
                        queue.add(link.destination.name);
                    }
                    else if(link.destination.gender == gender) {
                        //System.out.println("given gender :" + gender + ", gender in the link : " + link.destination.gender);
                        //add only boys or only girls depending on input
                        queue.add(link.destination.name);
                    }
                }
            }
        }

    }
    private void GetPaternalUncle(String name,LinkedList<String> queue){
        //Get dad and then get his sibings
        //System.out.println("Getting dad of " + name);
        String dad = GetDad(name);
        //System.out.println("Dad of " + name + " is " + dad);
        if(dad != null) {
            GetSiblings(dad, queue, MALE);
        }
    }
    private void GetPaternalAunt(String name,LinkedList<String> queue)
    {
        //get dad and then get his siblings
        String dad = GetDad(name);
        if(dad != null) {
            GetSiblings(dad, queue, FEMALE);
        }
    }
    private void GetMaternalUncle(String name,LinkedList<String> queue){
        //Get dad and then get his sibings
        String mom = GetMom(name);
        if(mom != null) { //this condition will never arise
            GetSiblings(mom, queue, MALE);
        }
    }
    private void GetMaternalAunt(String name,LinkedList<String> queue)
    {
        //get dad and then get his siblings
        String mom = GetMom(name);
        if(mom !=null) {
            GetSiblings(mom, queue, FEMALE);
        }
    }
    private void GetSisterInLaw(String name,LinkedList<String> queue)
    {
        //get partner
        String partner = GetPartner(name);
        //Get partner's sisters
        if(partner != null) {
            GetSiblings(partner, queue, FEMALE);
        }
        //now let's get siblings wives
        LinkedList<String> siblings = new LinkedList<String>();
        GetSiblings(name,siblings,MALE);
        //now lets get their wives
        Iterator<String> i = siblings.listIterator();
        while(i.hasNext())
        {
            String bro = i.next();
            //System.out.println("Trying to get wife of bro " + bro);
            String sisInLaw = GetPartner(bro);
            //System.out.println("wife of bro " + bro + " is " + sisInLaw);
            if(sisInLaw != null) {
                queue.add(sisInLaw);
            }
        }

    }

    private void GetBrotherInLaw(String name,LinkedList<String> queue)
    {
        //get partner
        String partner = GetPartner(name);
        //Get partner's brothers
        if(partner != null) {
            GetSiblings(partner, queue, MALE);
        }
        //now let's get siblings husbands
        LinkedList<String> siblings = new LinkedList<String>();
        GetSiblings(name,siblings,FEMALE);
        //now lets get their wives
        Iterator<String> i = siblings.listIterator();
        while(i.hasNext())
        {
            String sis = i.next();
            //System.out.println("Trying to get husband of sis " + sis);
            String sisInLaw = GetPartner(sis);
            //System.out.println("wife of bro " + sis + " is " + sisInLaw);
            if(sisInLaw != null) {
                queue.add(sisInLaw);
            }
        }

    }

    public void GetRelation(String name, String relation)
    {
        //System.out.println("Getting relation "+ name + relation);
        //first check if the name exists in the family tree
        if(isExists(name) == false)
        {
            System.out.println(PERSON_NOT_FOUND_ERR);
            return;
        }
        //traversing from the given node
        //we need to mark the traversed node. creating an array for it
        resetVisitedHashMap();

        //create a queue for BFS. that's basically linked list
        LinkedList<String> queue = new LinkedList<String>();

        //lets start adding the nodes and marking them as visited
        //start with current node
        visited.replace(name,true);
        String v=name;
        int weight = 0;
        switch(relation){
            case "Son":
                GetChildren(name,MALE,queue);
                break;
            case "Daughter":
                GetChildren(name,FEMALE,queue);
                break;
            case "Siblings":
                //Get Mom of this person, get her children
                //here assumption is unique partner pair. all the siblings
                //will have the same mom and same dad.
                
                GetSiblings(name,queue,"A");
                break;
            case "Paternal-Uncle":
                GetPaternalUncle(name,queue);
                break;
            case "Paternal-Aunt":
                GetPaternalAunt(name,queue);
                break;
            case "Maternal-Uncle":
                GetMaternalUncle(name,queue);
                break;
            case "Maternal-Aunt":
                GetMaternalAunt(name,queue);
                break;
            case "Sister-In-Law": //spouse's sisters or sibling's wives
                GetSisterInLaw(name,queue);
                break;
            case "Brother-In-Law":
                GetBrotherInLaw(name,queue);
                break;
            default:
        }
        if(queue.size() == 0){
            System.out.println(NONE);
        }
        else {
            while (queue.size() != 0) {
                //iterate thro v
                v = queue.poll();
                System.out.print(v + " ");
            }
        }
    }
    public void BFS(String v)
    {
        //traversing from the given node
        //we need to mark the traversed node. creating an array for it
        resetVisitedHashMap();

        //create a queue for BFS. that's basically linked list
        LinkedList<String> queue = new LinkedList<String>();

        //lets start adding the nodes and marking them as visited
        //start with current node
        visited.replace(v,true);
        queue.add(v);

        while(queue.size() != 0) {
            //iterate thro v
            v = queue.poll();

            System.out.println("v : " + v);
            Iterator<Edge> i = adj.get(v).listIterator();
            while (i.hasNext()) {
                Edge node = i.next();
                //check if new node is visited
                if (!visited.get(node.destination.name)) {
                    visited.put(node.destination.name,true) ;
                    queue.add(node.destination.name);
                }
            }
        }
        //now reset
    }
    public static void main(String args[])
    {

        String fileName = args[0];
//        Scanner s = new Scanner(System.in);
//        System.out.println("Number of nodes in the graph ");
//        int n = s.nextInt();

        FamilyTree ft = new FamilyTree();
        //prepopulating the family tree

        Node KingShan,QueenAnga,Chit,Amba,Ish,Vich,Lika,Aras,Chitra,Satya, Vyan,
        Dritha,Jaya,Tritha,Vritha,Vila,Chika,Arit,Jnki,Ahit,Satvy,Asva,Krpi,Vyas, Atya,
        Yodhan, Laki, Lavnya,Vasa,Kriya, Krithi;


//        a = new Node("A",FEMALE);
//        b = new Node("B",MALE);
//        c = new Node("C",MALE);
//        d = new Node("D",FEMALE);
//        e = new Node("E",MALE);
//        f = new Node("F",FEMALE);
//        g = new Node("G",MALE);
//        i = new Node("I",FEMALE);


        KingShan = new Node("KingShan",MALE);
        QueenAnga = new Node("QueenAnga",FEMALE);

        ft.addNode(KingShan);
        ft.addNode(QueenAnga);
        ft.addEdge("KingShan",QueenAnga,"Partner");


        ft.addChild("QueenAnga","Chit",MALE);
        ft.addChild("QueenAnga","Ish",MALE);
        ft.addChild("QueenAnga","Vich",MALE);
        ft.addChild("QueenAnga","Aras",MALE);
        ft.addChild("QueenAnga","Satya",FEMALE);

        Amba = new Node("Amba",FEMALE);
        ft.addNode(Amba);
        //node for Chit would have been added already
        ft.addEdge("Chit",Amba,"Partner");

        Lika = new Node("Lika",FEMALE);
        ft.addNode(Lika);
        ft.addEdge("Vich",Lika,"Partner");

        Chitra = new Node("Chitra",FEMALE);
        ft.addNode(Chitra);
        ft.addEdge("Aras",Chitra,"Partner");

        Vyan = new Node("Vyan",MALE);
        ft.addNode(Vyan);
        ft.addEdge("Satya",Vyan,"Partner");

        //adding next generation
        ft.addChild("Amba","Dritha",FEMALE);
        ft.addChild("Amba","Tritha",FEMALE);
        ft.addChild("Amba","Vritha",MALE);

        ft.addChild("Lika","Vila",FEMALE);
        ft.addChild("Lika","Chika",FEMALE);

        ft.addChild("Chitra","Jnki",FEMALE);
        ft.addChild("Chitra","Ahit",MALE);

        ft.addChild("Satya","Asva",MALE);
        ft.addChild("Satya","Vyas",MALE);
        ft.addChild("Satya","Atya",FEMALE);

        Jaya = new Node("Jaya",MALE);
        ft.addNode(Jaya);
        ft.addEdge("Dritha",Jaya,"Partner");

        Arit = new Node("Arit",MALE);
        ft.addNode(Arit);
        ft.addEdge("Jnki",Arit,"Partner");

        Satvy = new Node("Satvy",FEMALE);
        ft.addNode(Satvy);
        ft.addEdge("Asva",Satvy,"Partner");

        Krpi = new Node("Krpi",FEMALE);
        ft.addNode(Krpi);
        ft.addEdge("Vyas",Krpi,"Partner");

        //adding next generation
        ft.addChild("Dritha","Yodhan",MALE);

        ft.addChild("Jnki","Laki",MALE);
        ft.addChild("Jnki","Lavnya",FEMALE);

        ft.addChild("Satvy","Vasa",MALE);

        ft.addChild("Krpi","Kriya",MALE);
        ft.addChild("Krpi","Krithi",FEMALE);

        ft.GetRelation("Vasa","Siblings");



        //     ft.addNode(i);
   //     ft.addNode(g);

//        ft.addChild("A","C",MALE);
//        ft.addChild("A","D",FEMALE);
//        ft.addChild("A","E",MALE);
//        ft.addChild("A","F",FEMALE);
//        ft.addEdge(b,c,"Son");
//        ft.addEdge(b,d,"Son");
//        ft.addEdge(b,e,"Son");
//        ft.addEdge(b,f,"Daughter");
//        ft.addEdge(a,b,"partner");
//        ft.addEdge(f,g,"partner");
//        ft.addEdge(i,c,"partner");
//        ft.addChild("F","H",FEMALE);
//        ft.addChild("I","J",MALE);
//
//
//        System.out.println("Printing relationships A - sons");
//        ft.GetRelation("A","Son");
//        System.out.println("Printing relationships A - daughters");
//        ft.GetRelation("A","Daughter");
//        System.out.println("Printing relationships A - siblings");
//        ft.GetRelation("A","Siblings");
//        System.out.println("Printing relationships F - siblings");
//
//        ft.GetRelation("F","Siblings");
//
//   //     System.out.println("Getting Paternal aunt and uncles of J");
//   //     ft.GetRelation("J","paternal-uncle");
//   //     ft.GetRelation("J","paternal-aunt");
//
//        System.out.println("Getting Maternal aunt and uncles of H");
//        ft.GetRelation("H","maternal-uncle");
//        ft.GetRelation("H","maternal-aunt");
//
//        //Let's print sister in law of D,E,F
//        System.out.println("Printing sister in law of D");
//        ft.GetRelation("D","sister-in-law");

        //open the file
        try {
            File myFile = new File(fileName);
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {

                String command = myReader.nextLine();
                System.out.println(command);
                String[] arrOfStr = command.split(" ", 4);
                switch(arrOfStr[0])
                {
                    case "ADD_CHILD":
                        ft.addChild(arrOfStr[1],arrOfStr[2],arrOfStr[3]);
                        System.out.println();
                        break;
                    case "GET_RELATIONSHIP":
                        ft.GetRelation(arrOfStr[1],arrOfStr[2]);
                        System.out.println();
                        break;
                    default:
                        System.out.println("Unsupported command");
                }
            }
            myReader.close();
        } catch(Exception ex) {
            System.out.println("Following error occurred");
            ex.printStackTrace();
        }



    }
}
