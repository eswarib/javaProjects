package com.example;

public class AVLTree {

    private static final AVLTree mInst = new AVLTree();
    public Node root;
    private int numOfNodes;



    private AVLTree(){root = null;numOfNodes=0;}

    public static final AVLTree getInstance(){return mInst;}
    public Node getRoot(){ return root;}
    public int getNumOfNodes(){return numOfNodes;}

    private int max(int x,int y){
        return((x > y) ? x:y);
    }

    private int getBalance(Node node){
        return (getHeight(node.leftNode) - getHeight(node.rightNode));
    }

    private int getHeight(Node node){
        //System.out.println("Entering getHeight");

        return ((node == null)?0:node.height);
    }
    private Node leftRotate(Node x)
    {
        Node y = x.rightNode;
        Node T = y.leftNode;

        y.leftNode = x;
        x.rightNode = T;

        if(root.value == x.value)
        {
            System.out.println("changing root to y " + y.value + " from root " + root.value);
            root = y;
        }
        //update the heights of x and y
        x.setHeight(1 + max(getHeight(x.leftNode), getHeight(x.rightNode)));
        y.setHeight(1 + max(getHeight(y.leftNode), getHeight(y.rightNode)));

        return x;
    }

    private Node rightRotate(Node y)
    {
        Node x = y.leftNode;
        Node T = x.rightNode;

        x.rightNode = y;
        y.leftNode = T;

        if(root.value == y.value)
        {
            root = x;
        }

        //update the heights
        x.setHeight(1 + max(getHeight(x.leftNode), getHeight(x.rightNode)));
        y.setHeight(1 + max(getHeight(y.leftNode), getHeight(y.rightNode)));

        return y;
    }

    //insert method. returns the inserted node
    Node insert(Node node,int key)
    {
        //check if the input node is null
        if (node == null)
        {
            System.out.println("null node given as input");

            return new Node(key);
        }

        System.out.println("Node value : " + node.getValue() + "key to enter : " + key );
        //if value is less than root value insert to left
        if (key < node.getValue())
        {
            node.leftNode = insert(node.leftNode,key);
            //System.out.println("SubTree leftNode Value " + node.leftNode.getValue());
            numOfNodes++;
        }
        else if(key > node.value )
        {
            node.rightNode = insert(node.rightNode,key);
            numOfNodes++;
        }
        else
        {
            //duplicate values are not allowed
            return node;
        }

        //now calculate the height
        node.setHeight(1 + max(getHeight(node.leftNode), getHeight(node.rightNode)));

        //check if the tree is balanced. get the (height of left tree - height of right tree)
        int balance = getBalance(node);
        if (balance > 1 && key < node.leftNode.value) //not balanced and is a LL
        {
            //rotate node to right
            return rightRotate(node);


        }
        if(balance > 1 && key > node.leftNode.value) // LR case
        {
            node.rightNode = leftRotate(node.rightNode);
            return rightRotate(node);

        }
        if (balance < -1 && key > node.rightNode.value) //not balanced and is a RR
        {
                return leftRotate(node);
        }
        if (balance < -1 && key < node.rightNode.value) //not balanced and is a RL case
        {
                node.leftNode = rightRotate(node.leftNode);
                return leftRotate(node);
        }
        //update the heights of node
        return node;

    }

    void delete(Node node,int key)
    {
        if(root == null)
        {
            System.out.println("node passed to Delete is null ");
        }
        if(key < node.value)
        {
            //search in the leftTree
            delete(node.leftNode,key);
        }
        else if(key > node.value)
        {
            delete(node.rightNode,key);
        }
        else if(key == node.value)
        {
            //This is the node to be deleted.
            //check if the node has one or zero child
            if(node.leftNode == null && node.rightNode == null)
            {
                ;//nothing to be done
            }
            else if (node.leftNode == null) //left node does not exists
            {
                //copy the value from right node to this node
                node.value = node.rightNode.value;
                node.leftNode = node.rightNode.leftNode;
                node.rightNode = node.rightNode.rightNode;
            }
            else if (node.rightNode == null) //right node does not exist
            {
                //copy the value from left node to this node
                node.value = node.leftNode.value;
                node.leftNode = node.leftNode.leftNode;
                node.rightNode = node.leftNode.rightNode;
            }
            else //both left child and right child exists
            {

            }
        }
    }

    //preOrder traversal
    public void preOrderTraversal(Node node)
    {
        if(node == null)
            return;
        //print the nodes in pre order (left,root,right)
        //this should be a recursive function
        //System.out.println("Left traversal of node " + node.getValue());
        preOrderTraversal(node.leftNode);
        //print this node
        System.out.println(" " + node.value + " ");
        //print right node
        //System.out.println("Right traversal of node " + node.getValue());
        preOrderTraversal(node.rightNode);
    }

    public void inOrderTraversal(Node node)
    {
        if(node == null)
            return;
        //print the nodes in pre order (left,root,right)
        //this should be a recursive function
        //print this node
        System.out.println(" " + node.value + " ");
        //print left node
        inOrderTraversal(node.leftNode);
        //print right node
        inOrderTraversal(node.rightNode)                                  ;
    }

    public void postOrderTraversal(Node node)
    {
        if(node == null)
            return;
        //print the nodes in pre order (left,root,right)
        //this should be a recursive function
        //print left node
        postOrderTraversal(node.leftNode);
        //print right node
        postOrderTraversal(node.rightNode);
        //print this node
        System.out.println(" " + node.value + " ");
    }
    //delete method
    private Node getLeftMostNode(Node node)
    {
        if(node == null)
            return node;
        Node leftMostNode = getLeftMostNode(node.leftNode);
        return leftMostNode;
    }
    public String computeMaxPath(Node node,String s)
    {
        if(node == null)
            return s;
        Node left = node.leftNode;
        Node right = node.rightNode;
        s = computeMaxPath(node.leftNode,s);
        s = computeMaxPath(node.rightNode,s);
        //s = node.value;

        if(left != null && right != null) {
            if (left.value > right.value) {
                s = s + left.value;
            } else if (right.value > left.value){
                s = s + right.value;
            }
        }
        else if(left != null)
        {
            s = s + left.value;
        }
        else if(right != null)
        {
            s = s + right.value;
        }

        return s;
    }
    public void ToDLL()
    {
        //left most node will be the head
        //in the in-order traversal left, root, right,
        //left node will become the prev, right node will be next for the root node
        ;

    }
    //get method
    public static void main(String[] args)
    {
        //tree implementation
        AVLTree myTree = AVLTree.getInstance();
        System.out.println("Creating the AVL Tree....");

        myTree.root = myTree.insert(myTree.getRoot(),30);
        Node node = myTree.insert(myTree.getRoot(),5);
        node = myTree.insert(myTree.getRoot(),35);
        node = myTree.insert(myTree.getRoot(),32);
        node = myTree.insert(myTree.getRoot(),40);
        node = myTree.insert(myTree.getRoot(),45);
        System.out.println("Successfully created AVL Tree....");
        System.out.println("Number of nodes : " + myTree.getNumOfNodes());
        System.out.println("Printing the tree using preorder traversal...");
        myTree.preOrderTraversal(myTree.getRoot());

        System.out.println("Printing the tree using inorder traversal...");
        myTree.inOrderTraversal(myTree.getRoot());

        System.out.println("Printing the tree using postorder traversal...");
        myTree.postOrderTraversal(myTree.getRoot());

        System.out.println("Trying to get max sum path ");
        String s = " ";
        s = myTree.computeMaxPath(myTree.root,s);
        System.out.println(s);

    }
}
