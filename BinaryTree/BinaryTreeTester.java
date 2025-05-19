import java.util.ArrayList;

/**
 *	Binary Tree Tester
 *	Tests adding to a tree, inorder, preorder, postorder, and building a balanced tree.
 *
 *	@author	Mr Greenstein
 */
public class BinaryTreeTester{
	private BinaryTree<Integer> tree; // the binary tree
	private final int ARR_SIZE = 20; // number of elements for tree
	private final int SAMPLE_SIZE = 100; // range of numbers for tree, 1 to SAMPLE_SIZE
	public BinaryTreeTester(){
		tree = new BinaryTree<Integer>();
	}
	public static void main(String[] args){
		BinaryTreeTester btt = new BinaryTreeTester();
		btt.run();
	}
	public void run(){
		int[] arr = createRandomNumbers();
		System.out.println("Random input:");
		printArray(arr);
		for(int a = 0; a < arr.length; a++){
			tree.add(arr[a]);
		}
		System.out.println("Tree:");
		tree.printTree();
		System.out.println("\nInorder:");
		tree.printInorder();
		System.out.println();
		System.out.println("\nPreorder:");
		tree.printPreorder();
		System.out.println();
		System.out.println("\nPostorder:");
		tree.printPostorder();
		System.out.println();
		System.out.println("\n**** Building balanced tree ****");
		BinaryTree<Integer> balTree = tree.makeBalancedTree();
		balTree.printTree();
		System.out.println();
	}
	/*******************************************************************************/	
	/*************************** Utilities for Testing *****************************/	
	/*******************************************************************************/	
	/**
	 *	Create an array of random integer numbers that has no duplicates.
	 *	@return			array of random numbers
	 */
	private int[] createRandomNumbers(){
		int[] arr = new int[ARR_SIZE];
		// create ArrayList of Integer numbers 1 to SAMPLE_SIZE
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < SAMPLE_SIZE; i++){
			numbers.add(i + 1);
		}
		// select numbers randomly, no duplicates
		for(int i = 0; i < ARR_SIZE; i++){
			arr[i] = numbers.remove((int)(Math.random() * numbers.size()));
		}
		return arr;
	}
	/**
	 *	print an integer array
	 *	@param arr		the integer array
	 */
	private void printArray(int[] arr){
		for(int i = 0; i < arr.length; i++){
			System.out.print(arr[i] + " ");
		}
		System.out.println("\n");
	}
}
