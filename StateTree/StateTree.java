import java.util.Scanner;

/**
 *	The driver program for creating and manipulating
 *	a binary tree of state information.
 *
 *	@author	Jonathan Chen
 *	@since	May 21, 2025
 */
public class StateTree{
	// Fields
	private BinaryTree<State> bTree;
	private final String IN_FIlE = "states2.txt";	// input file
	
	public StateTree(){ }
	
	public static void main(String [] args){
		StateTree treeOrder = new StateTree();
		treeOrder.mainMenu();
	}
	public void mainMenu(){
		String choice;
		do{
			System.out.println("Binary Tree algorithm menu\n");
			System.out.println("(1) Read Data from a file");
			System.out.println("(2) Print the list");
			System.out.println("(3) Search the list");
			System.out.println("(4) Delete node");
			System.out.println("(5) Count nodes");
			System.out.println("(6) Clear the list");
			System.out.println("(7) Print the level");
			System.out.println("(8) Print depth of tree");
			System.out.println("(Q) Quit\n");
			choice = Prompt.getString("Choice");
			System.out.println();
			if('1' <= choice.charAt(0) && choice.charAt(0) <= '8'){
				switch(choice.charAt(0)){
					case '1' :	
						loadData();		
						break;
					case '2' :
						System.out.println();
						System.out.println("The tree printed inorder\n");
						if(bTree != null){
							bTree.printInorder();
						}
						System.out.println();
						break;
					case '3' :
						find();
						break;
					case '4' :
						delete();
						break;
					case '5' :
						System.out.println("Number of nodes = " + size(bTree.getRoot()));
						System.out.println();
						break;
					case '6' :
						clear();
						break;
					case '7' :
						printLevel();
						System.out.println();
						break;
					case '8' :
						if(depth(bTree.getRoot(), -1) > -1)
							System.out.println("Depth of tree = " + depth(bTree.getRoot(), -1));
						else
							System.out.println("Tree empty");
						System.out.println();
						break;
				}
			}
		}
		while(choice.charAt(0) != 'Q' && choice.charAt(0) != 'q');
	}
	/**	Load the data into the binary tree */
	public void loadData(){
		bTree = new BinaryTree<>();
		Scanner input = FileUtils.openToRead(IN_FIlE);
		while(input.hasNextLine()){
			String name = input.next();
			String abbr = input.next();
			int population = input.nextInt();
			int area = input.nextInt();
			int reps = input.nextInt();
			String capital = input.next();
			int month = input.nextInt();
			int day = input.nextInt();
			int year = input.nextInt();
			State state = new State(name, abbr, population, area, reps, capital, month, day, year);
			bTree.add(state);
		}
		input.close();
		System.out.println("Database " + IN_FIlE + " is loaded!!\n");
	}
	/**	Find the node in the tree */
	public void find(){
		boolean tOrF = true;
		while(tOrF){
			String name = Prompt.getString("Enter state name to search(Q to quit)").toLowerCase();
			State target = new State(name); // temporary for comparison
			State found = findRecursive(bTree.getRoot(), target);
			if(found != null){
				System.out.println("\n" + found + "\n");
			}
			else{
				System.out.println("\n" + name + " not found\n");
				if(name.equals("q")) tOrF = false;
			}
		}
	}
	private State findRecursive(TreeNode<State> node, State target){
		if(node == null){
			return null;
		}
		int cmp = target.compareTo(node.getValue());
		if(cmp == 0){
			return node.getValue();
		}
		else if(cmp < 0){
			return findRecursive(node.getLeft(), target);
		}
		else{
			return findRecursive(node.getRight(), target);
		}
	}
	/** Delete a node */
	public void delete(){
		boolean tOrF = true;
		while(tOrF){
			String name = Prompt.getString("Enter state name to delete(Q to quit)").toLowerCase();
			State temp = new State(name);
			if(findRecursive(bTree.getRoot(), temp) != null){
				bTree.remove(temp);
				System.out.println();
				System.out.println(name.substring(0, 1).toUpperCase() + name.substring(1) + " has been deleted!!\n");
			}
			else{
				System.out.println();
				System.out.println(name + " not found\n");
				if(name.equals("q")){
					tOrF = false;
				}
			}
		}
	}
	/**	Returns the number of nodes in the subtree - recursive
	 *	@param node		the root of the subtree
	 *	@return			the number of nodes in the subtree
	 */
	public int size(TreeNode<State> node){
		if(node == null){
			return 0;
		}
		return 1 + size(node.getLeft()) + size(node.getRight());
	}
	/**	Clear out the binary tree */
	public void clear(){
		bTree = new BinaryTree<>();
		System.out.println("Tree has been cleared!\n");
	}
	/**	Print the level requested */
	public void printLevel(){
		boolean tOrF = true;
		while(tOrF){
			int level = Prompt.getInt("Enter level value to print(-1 to quit)");
			if(level < 0){
				tOrF = false;
			}
			System.out.println("\nLevel " + level);
			printLevelRecursive(bTree.getRoot(), 0, level);
			System.out.println();
			System.out.println();
		}
	}
	private void printLevelRecursive(TreeNode<State> node, int current, int target){
		if(node == null) return;
		if(current == target){
			System.out.print(node.getValue().getName() + "  ");
		}
		else{
			printLevelRecursive(node.getLeft(), current + 1, target);
			printLevelRecursive(node.getRight(), current + 1, target);
		}
	}
	/**	Returns the depth of the subtree - recursive
	 *	@param node		the root of the subtree
	 *	@return			the depth of the subtree
	 */
	public int depth(TreeNode<State> node, int depth){
		if(node == null) return depth;
		int leftDepth = this.depth(node.getLeft(), depth + 1);
		int rightDepth = this.depth(node.getRight(), depth + 1);
		return Math.max(leftDepth, rightDepth);
	}
}
