import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;		// for testing purposes
/**
 *	SortMethods - Sorts an ArrayList of Strings in ascending order.
 *
 *	Requires FileUtils class to compile.
 *	Requires file randomWords.txt to execute a test run.
 *
 *	@author	Jonathan Chen
 *	@since	January 10, 2025
 */
public class SortMethods{
	/**
	 *	Merge Sort algorithm - in ascending order
	 *	@param arr		List of String objects to sort
	 */
	public void mergeSort(List<String> arr){
		mergeSortRecurse(arr, 0, arr.size() - 1);
	}
	/**
	 *	Recursive mergeSort method.
	 *	@param arr		List of String objects to sort
	 *	@param first	first index of arr to sort
	 *	@param last		last index of arr to sort
	 */
	public void mergeSortRecurse(List<String> arr, int first, int last){
		// insert your code here
		// one element
		if(first == last){
			return;
		}
		// two elements
		if(last - first == 1){
			if(arr.get(first).compareTo(arr.get(last)) > 0){
				swap(arr, first, last);
			}
			return;
		}
		// greater than two element
	    int mid = (first + last) / 2;
	    // Recursively sort the left and right halves
	    mergeSortRecurse(arr, first, mid);
	    mergeSortRecurse(arr, mid + 1, last);
	    // Merge the two sorted halves
	    merge(arr, first, mid, last);
	}
	/**
	 * Swap two elements of an ArrayList
	 * @param arr 	the Arraylist
	 * @param one	index of first element
	 * @param two	index of second element
	 */
	public void swap(List<String> arr, int one, int two){
		String temp = arr.get(one);
		arr.set(one, arr.get(two));
		arr.set(two, temp);
	}
	/**
	 *	Merge two lists that are consecutive elements in array.
	 *	@param arr		List of String objects to merge
	 *	@param first	first index of first list
	 *	@param mid		the last index of the first list;
	 *					mid + 1 is first index of second list
	 *	@param last		last index of second list
	 */
	public void merge(List<String> arr, int first, int mid, int last){
		// Insert your code here
		int left = first;
		int right = mid + 1;
		List<String> newArr = new ArrayList<String>();
		while(left <= mid && right <= last){
			if(arr.get(left).compareTo(arr.get(right)) < 0){
				newArr.add(arr.get(left));
				left++;
			}
			else{
				newArr.add(arr.get(right));
				right++;
			}
		}
		while(left <= mid){
			newArr.add(arr.get(left));
			left++;
		}
		while(right <= last){
			newArr.add(arr.get(right));
			right++;
		}
		//transfer newArr to arr
		for(int i = 0; i < newArr.size(); i ++){
			arr.set(first + i, newArr.get(i));
		}
	}
	/**
	 *	Print an List of Strings to the screen
	 *	@param arr		the List of Strings
	 */
	public void printArray(List<String> arr){
		if (arr.size() == 0) System.out.print("(");
		else System.out.printf("( %-15s", arr.get(0));
		for (int a = 1; a < arr.size(); a++){
			if (a % 5 == 0) System.out.printf(",\n  %-15s", arr.get(a));
			else System.out.printf(", %-15s", arr.get(a));
		}
		System.out.println(" )");
	}
	/*************************************************************/
	/********************** Test program *************************/
	/*************************************************************/
	private final String FILE_NAME = "randomWords.txt";
	public static void main(String[] args){
		SortMethods se = new SortMethods();
		se.run();
	}
	public void run(){
		List<String> arr = new ArrayList<String>();
		// Fill List with random words from file		
		fillArray(arr);
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();
	}
	// Fill String array with words
	public void fillArray(List<String> arr){
		Scanner inFile = FileUtils.openToRead(FILE_NAME);
		while(inFile.hasNext()){
			arr.add(inFile.next());
		}
		inFile.close();
	}
}
