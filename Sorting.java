/*********************************************************
* Sorting.java (Project 2: Sorting Algorithms)
* Author: Robert Payne
* Date: 10/14/2012
* Class: ITCS 2215

* Purpose: To compare the complexity of the quicksort and
bubblesort sorting algorithms. This program uses a median
of three approach to the selection of the pivot for the
quicksort. The program uses arrays of sized by 2^n where
n is from 3 to 15 and fills them with a random number.
For consistency, the same numbers random numbers that
are put into the quicksort array are placed inside the
bubblesort array. The arrays with random numbers are then
sorted and the number of logical comparison in each sort
are counted to measure the complexity. These measurements
as well as array size are outputed for comparison.
/*********************************************************/
import java.util.Random;

public class Sorting {

static int 		qSortCounter = 0;			//number of quick sort comparisons
		
static int 		bSortCounter = 0;			//number of bubble sort comparisons
/*	main()	 
===========================================================

---------------------------------------------------------*/ 
	public static void main(String[] args) {	
	 
	 	int[] qSortArray = new int[32786]; 		//the array for quicksort to be filled
															//with random numbers

		int[] bSortArray = new int[32786];		//the array for bubblesort to be filled
															//with the same numbers quicksort is
															//is filled with
		
		Random randomNumber = new Random();		//random number object

		int powOfTwo = 3;								//placeholder for which
															//power of two currently on

		int currentSize = 0;							//current size of array
															//being analyzed
		
		//This loop iterates for each power of 2 from 3 to 15
		while(powOfTwo <= 15) {
			
			currentSize =(int) Math.pow(2, powOfTwo); //set the currentSize
			
			//initialize the quicksort array with random numbers
			//and copy those numbers into the bubblesort array
			for(int i = 0; i < currentSize; i++) {
			
				qSortArray[i] = randomNumber.nextInt(currentSize);
				bSortArray[i] = qSortArray[i];
			}
			
			qSortCounter = 0;	//reset the complexity counter to 0
			bSortCounter = 0;	//reset the complexity counter to 0
			QuickSort(0, currentSize - 1, qSortArray);
			BubbleSort(bSortArray, currentSize);				
				
			//output for the measurements, if/else statements just for
			//making the columns line up nicely on the output				
			if(currentSize == 8)
				System.out.println("Size: " + currentSize + "    \t\tQuickSort: " + qSortCounter
			 		+ "\t\tBubbleSort: " + bSortCounter); //output		
			else if(currentSize <= 512)
				System.out.println("Size: " + currentSize + "    \tQuickSort: " + qSortCounter
			 		+ "\t\tBubbleSort: " + bSortCounter); //output
			
			else
				System.out.println("Size: " + currentSize + "    \tQuickSort: " + qSortCounter
			 		+ "\tBubbleSort: " + bSortCounter); //output
			powOfTwo++;	//increment the search array size for the next iteration
		}	
	}
	
/*	int MedianOfThree(int, int, int, int[])	 
===========================================================
This function takes the left, middle, right and the array
as input arguments and checks the elements for the median
value. This returns a 1, 2, 3 corresponding to middle,
right, and left respectively. Then the partition function
will use that value to set up the pivot value to be used. 
---------------------------------------------------------*/
		public static int MedianOfThree(int a, int m, int b, int array[]){
		
		//middle is median case
		if((array[m] <= array[a] && array[m] >= array[b])
			|| (array[m] <= array[b] && array[m] >= array[a])){
				
			qSortCounter++;
			return 1;
		}
		
		//right is median case
		else if((array[b] <= array[a] && array[b] >= array[m])
			|| (array[b] <= array[m] && array[b] >= array[a])){
			
			qSortCounter+=2;
			return 2;
		}
		
		//left is median case	
		else {
		
			qSortCounter+=2;
			return 3;
		}
	}
/*	Partition()	 
===========================================================
This function uses the partition element to put the lesser
values on the left side and greater on the right side. It
then sends the index of the partion element, which is now
in the correct place, back to the quicksort function.
---------------------------------------------------------*/	
	public static int Partition(int left, int right, int array[]){
					
		int pivot = MedianOfThree(left, (left + right)/2, right, array); //setting up
																							//to get the pivot
		int pivotIndex;				//used to keep track of the pivot value
		int i = left, j = right;	//setting i to left and j to right
		
		//check to see what the pivot and pivot index should be set to based
		//on the result of MedianOfThree
		if(pivot == 1){
			
			qSortCounter++;
			pivot = array[(left + right)/2];
			pivotIndex = (left+right)/2;
		}
		
		else if(pivot == 2){
			
			qSortCounter+=2;
			pivot = array[right];
			pivotIndex = right;
		}
		
		else{
			
			qSortCounter +=2;
			pivot = array[left];
			pivotIndex = left;
		}
		
		//i and j move from the left and right towards each other until
		//they get to an element that is on the wrong side of the pivot.
		//then they swap elements
		while(i<j){
			
			qSortCounter++;
			
			//increment i to the right until i passes j or the element
			//is larger than the pivot
			while(array[i]<= pivot && i < j){
			
				qSortCounter++;
				i++;
			}
			
			//incremeant j to the left until the element is smaller than
			//the pivot
			while(array[j]> pivot){
			
				qSortCounter++;
				j--;
			}
			
			if(i < j){
				
				//change the pivot's index if needed	
				if(j == pivotIndex){
				
					pivotIndex = i; 
				}
				
				else if(i == pivotIndex){
				
					qSortCounter ++;
					pivotIndex = j;
				}
				qSortCounter += 2;
				Swap(i,j,array); 			//swap the elements in i and j

			}		
		}
		
		//swaps the pivot's position with j
		Swap(pivotIndex,j,array);
		return j;

	}
/* void QuickSort(int, int, int[])	 
===========================================================
This function takes the right, left and array as input
and then finds the partition index by calling Partition.
QuickSort is then called recursively by using the elements
to the left and to the right of the partition.
---------------------------------------------------------*/	
	public static void QuickSort(int left, int right, int array[]){

		//calls partition and quicksort(left/right) if the size
		//of the current partition is greater than 1
		if(right - left > 1){
		
			qSortCounter++;
			int i = Partition(left, right, array);
			QuickSort(left, i-1, array);
			QuickSort(i+1, right, array);
		}
		//swaps elements in size 2 partition
		//if it is needed
		else if(right - left == 1) {
		
			qSortCounter += 3;
			if(array[left] > array[right])
				Swap(left,right,array);
		}
		
	}
/*	void BubbleSort(int[], int)	 
===========================================================
This function takes the array and the size of the array
and performs a bubblesort by comparing all of the adjacent
elements starting from the left and working right. After
the first iteration the greatest element will be in the
correct place and the second iteration the second 
greatest element will be in the correct place.
---------------------------------------------------------*/
   public static void BubbleSort(int array[], int size)
	{

		for(int i = 1; i < size; i++)
		{
			for(int j = 0; j < size-i; j++)
			{
				if(array[j] > array[j+1])
				 	Swap(j,j+1,array);
				bSortCounter++;
			}
		}
	}
	
/*	void Swap(int, int, int[])	 
===========================================================
This function takes two indexes and an array and swaps
the element values.
---------------------------------------------------------*/
	public static void Swap(int i, int j, int array[])
	{
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}