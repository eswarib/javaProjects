import java.util.Scanner;

public class Merge {

    public static Merge mInst = new Merge();
    public static Merge getInstance(){ return mInst;}
    private void merge(int a[],int l,int r,int m)
    {
        //we need to copy to two temporary arrays of size n1,n2
        int n1 = m-l+1; // l to m array
        int n2 = r-m;   // m+1 to r array

        int L[] = new int[n1];
        int R[] = new int[n2];

        for(int i=0; i<n1; i++)
        {
            //starting from l need to copied
            L[i] = a[l+i];
        }
        for(int i=0; i<n2; i++)
        {
            R[i] = a[m+1+i];
        }

        //now merge
        //initial indexes of arrays L[] and R[]
        int i=0,j=0;
        //initial index of merged array
        int k = l;
        while (i<n1 && j<n2)
        {
            if(L[i] <= R[j])
            {
                a[k] = L[i];
                i++;
            }
            else
            {
                a[k] = R[j];
                j++;
            }
            k++;
        }
        //check if all of L traversed
        while(i < n1)
        {
            a[k] = L[i];
            i++;
            k++;
        }
        //check if all of R traversed
        while(j < n2)
        {
            a[k] = R[j];
            j++;
            k++;
        }
    }
    public void sort(int a[],int l,int r) //l - first index, r - last index
    {
        if(l < r) {
            int m = (l + r) / 2;
            sort(a, l, m);
            sort(a, m + 1, r);

            //now merge the sorted arrays
            merge(a, l, r, m);
        }
    }
    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        int arr[] = new int[7];
        for (int i=0; i < 7;i++)
        {
            arr[i] = s.nextInt();
        }
        Merge m = Merge.getInstance();
        m.sort(arr,0,6);
        //print the sorted array
        for(int i=0;i < 7;i++)
        {
            System.out.println(arr[i] + " ");
        }

    }
}
