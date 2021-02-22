public class Maze {
    public int N;
    public void Maze(int n){ N=n; }
    public void printSolutionMaze(int[][] sol)
    {
        System.out.println("Printing the solution matrix ");
        for(int i=0;i<N;i++)
        {
            for(int j=0;j<N;j++)
            {
                System.out.print(sol[i][j] + " ");
            }
            System.out.println();
        }
    }
    public boolean solveMazeUtil(int[][] maze,int x,int y, int[][] sol)
    {
        System.out.println("Entering solveMazeUtil with x= " + x + " y = " + y + ".......");
        //check if we are at the goal, i.e x= N-1, y=N-1
        if(x==N-1 && y==N-1 && maze[x][y]==1)
        {
            //set the solution matrix
            sol[x][y] = 1;
            System.out.println("Reached the goal !!!!");
            return true;
        }

        //we are not at goal. we can move forward or downward. Before moving
        //lets check if we are in safe position
        if(x>=0 && x<N && y>=0 && y<N && maze[x][y] == 1)
        {
            //we are in safe position. Include this position in solution
            sol[x][y]=1;
            System.out.println("We are in safe position.");

            //try to move forward recursively
            if(solveMazeUtil(maze,x,y+1,sol) == true)
                return true;

            //we cannot move forward further, lets move downward
            if(solveMazeUtil(maze,x+1,y,sol))
                return true;

            //if can't go till the end from here, remove this position from the solution
            sol[x][y]=0;
            //return false;
        }
        //default one false
        return false;
    }
    public void solveMaze(int[][] maze)
    {
        //define the solution matrix of N*N
        int sol[][] = new int[N][N];

        //call util function to find the solution
        if(solveMazeUtil(maze,0,0,sol) == true)
            printSolutionMaze(sol);
    }
    public static void main(String[] args){
        int[][] inMaze= {{1,0,0,0},
                {1,1,0,1},
                {0,1,0,0},
            {1,1,1,1}};

        Maze myMaze = new Maze();
        myMaze.N = 4;
        myMaze.solveMaze(inMaze);
    }
}
