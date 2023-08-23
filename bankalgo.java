//Java Program for bankalgo Algorithm

import java.util.Scanner;

public class bankalgo
{
	int n = 5; // Number of processes
	int m = 3; // Number of resources
	int need[][] = new int[n][m];
	int [][]max;
	int [][]alloc;
	int avail[][];
	int safeSequence[] = new int[n];

	void initializeValues()
    {
        Scanner scanner = new Scanner(System.in);
     // Allocation Matrix
     alloc = new int[n][m];
     System.out.println("Enter the Allocation Matrix:");
     for (int i = 0; i < n; i++) {
         for (int j = 0; j < m; j++) {
             alloc[i][j] = scanner.nextInt();
         }
     }

     // MAX Matrix
     max = new int[n][m];
     System.out.println("Enter the MAX Matrix:");
     for (int i = 0; i < n; i++) {
         for (int j = 0; j < m; j++) {
             max[i][j] = scanner.nextInt();
         }
     }
    

     // Available Resources
     avail = new int[1][m];
     System.out.println("Enter the Available Resources:");
     for (int i = 0; i < m; i++) {
         avail[0][i] = scanner.nextInt();
	}
     scanner.close();
    }

	void isSafe()
	{
	int count=0;
	
	//visited array to find the already allocated process
	boolean visited[] = new boolean[n];
	for (int i = 0;i < n; i++)
	{
		visited[i] = false;
	}
		
	//work array to store the copy of available resources
	int work[] = new int[m];
	for (int i = 0;i < m; i++)
	{
		work[i] = avail[0][i];
	}

	while (count<n)
	{
		boolean flag = false;
		for (int i = 0;i < n; i++)
		{
			if (visited[i] == false)
			{
			int j;
			for (j = 0;j < m; j++)
			{	
				if (need[i][j] > work[j])
				break;
			}
			if (j == m)
			{
			safeSequence[count++]=i;
			visited[i]=true;
			flag=true;
						
				for (j = 0;j < m; j++)
				{
				work[j] = work[j]+alloc[i][j];
				}
			}
			}
		}
		if (flag == false)
		{
			break;
		}
	}
	if (count < n)
	{
		System.out.println("The System is UnSafe!");
	}
	else
	{
		//System.out.println("The given System is Safe");
		System.out.println("Following is the SAFE Sequence");
				for (int i = 0;i < n; i++)
		{
			System.out.print("P" + safeSequence[i]);
			if (i != n-1)
			System.out.print(" -> ");
		}
	}
	}
	
	void calculateNeed()
	{
  
	for (int i = 0;i < n; i++)
	{
		for (int j = 0;j < m; j++)
		{
		need[i][j] = max[i][j]-alloc[i][j]; 
       
		}
	}	
     
	}
    public static void main(String[] args) {
        bankalgo bal = new bankalgo();

        bal.initializeValues();
        // Calculate the Need Matrix
        bal.calculateNeed();

        System.out.println("Need resources:");
        for (int i = 0; i < bal.n; i++) {
            for (int j = 0; j < bal.m; j++) {
                System.out.print(bal.need[i][j] + " ");
            }
            System.out.println();
        }

        // Check whether system is in safe state or not
        bal.isSafe();
    }
}
