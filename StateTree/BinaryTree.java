import java.util.List;
import java.util.ArrayList;
/**
 *	Binary Tree of Comparable values.
 *	The tree only has unique values. It does not add duplicate values.
 *	
 *	@author	Jonathan Chen
 *	@since	May 14, 2025
 */
public class BinaryTree<E extends Comparable<E>>{
	private TreeNode<E> root; // the root of the tree
	private final int PRINT_SPACES = 3;	// print spaces between tree levels
										// used by printTree()
	/**	constructor for BinaryTree */
	public BinaryTree(){}
	
	/**	Field accessors and modifiers */
	
	/**	Add a node to the tree
	 *	@param value		the value to put into the tree
	 */
	public void add(E value){
        root = addRecursive(root, value);
    }
    // Recursive helper for add
    private TreeNode<E> addRecursive(TreeNode<E> node, E value){
        if(node == null){
            return new TreeNode<>(value);
        }
        if(value.compareTo(node.getValue()) < 0){
            node.setLeft(addRecursive(node.getLeft(), value));
        }
        else{
            node.setRight(addRecursive(node.getRight(), value));
        }
        return node;
    }
	/**
	 *	Print Binary Tree Inorder
	 */
	public void printInorder(){
        printInorder(root);
    }
    private void printInorder(TreeNode<E> node){
		if(node != null){
			printInorder(node.getLeft());
			System.out.println(node.getValue());
			printInorder(node.getRight());
		}
	}
	/**
	 *	Print Binary Tree Preorder
	 */
	public void printPreorder(){
        printPreorder(root);
    }
    private void printPreorder(TreeNode<E> node){
        if(node != null){
            System.out.print(node.getValue() + " ");
            printPreorder(node.getLeft());
            printPreorder(node.getRight());
        }
    }
	/**
	 *	Print Binary Tree Postorder
	 */
	public void printPostorder(){
        printPostorder(root);
    }
    private void printPostorder(TreeNode<E> node){
        if(node != null){
            printPostorder(node.getLeft());
            printPostorder(node.getRight());
            System.out.print(node.getValue() + " ");
        }
    }
	/**	Return a balanced version of this binary tree
	 *	@return		the balanced tree
	 */
	public BinaryTree<E> makeBalancedTree(){
        List<E> sorted = new ArrayList<>();
        fillInorderList(root, sorted);
        BinaryTree<E> balanced = new BinaryTree<>();
        balanced.root = buildBalancedFromList(sorted, 0, sorted.size() - 1);
        return balanced;
    }
    private void fillInorderList(TreeNode<E> node, List<E> list){
	    if (node != null){
	        fillInorderList(node.getLeft(), list);
	        list.add(node.getValue());
	        fillInorderList(node.getRight(), list);
	    }
	}
	// Recursive helper to build balanced tree from sorted list
    private TreeNode<E> buildBalancedFromList(List<E> list, int start, int end){
        if(start > end){
			return null;
		}
        int mid =(start + end) / 2;
        TreeNode<E> node = new TreeNode<>(list.get(mid));
        node.setLeft(buildBalancedFromList(list, start, mid - 1));
        node.setRight(buildBalancedFromList(list, mid + 1, end));
        return node;
    }
	/**
	 *	Remove value from Binary Tree
	 *	@param value		the value to remove from the tree
	 *	Precondition: value exists in the tree
	 */
	public void remove(E value){
		root = remove(root, value);
	}
	/**
	 *	Remove value from Binary Tree
	 *	@param node			the root of the subtree
	 *	@param value		the value to remove from the subtree
	 *	@return				TreeNode that connects to parent
	 */
	public TreeNode<E> remove(TreeNode<E> node, E value){
        if(node == null){
			return null;
		}
        int cmp = value.compareTo(node.getValue());
        if(cmp < 0){
            node.setLeft(remove(node.getLeft(), value));
        }
        else if(cmp > 0){
            node.setRight(remove(node.getRight(), value));
        }
        else{
            // Node to remove found
            if(node.getLeft() == null){
				return node.getRight();
			}
            if(node.getRight() == null){
				return node.getLeft();
			}
            // Node has two children: replace with smallest from right subtree
            TreeNode<E> successor = findMin(node.getRight());
            node = new TreeNode<>(successor.getValue(), node.getLeft(), remove(node.getRight(), successor.getValue()));
        }
        return node;
    }
    // Helper to find the minimum value in a subtree
    private TreeNode<E> findMin(TreeNode<E> node){
        while(node.getLeft() != null){
            node = node.getLeft();
        }
        return node;
    }
	/*******************************************************************************/	
	/********************************* Utilities ***********************************/	
	/*******************************************************************************/	
	/**
	 *	Print binary tree
	 *	@param root		root node of binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example Integer tree:
	 *			  11
	 *			/	 \
	 *		  /		   \
	 *		5			20
	 *				  /	  \
	 *				14	   32
	 *
	 *	would be output as:
	 *
	 *				 32
	 *			20
	 *				 14
	 *		11
	 *			5
	 ***********************************************************************/
	public void printTree(){
        printTree(root, "", true);
    }
    private void printTree(TreeNode<E> node, String prefix, boolean isRight){
	    if (node != null){
	        printTree(node.getRight(), prefix + "     ", false);
	        System.out.println(prefix + node.getValue());
	        printTree(node.getLeft(), prefix + "     ", true);
	    }
	}
	/**
	 *	Recursive node printing method
	 *	Prints reverse order: right subtree, node, left subtree
	 *	Prints the node spaced to the right by level number
	 *	@param node		root of subtree
	 *	@param level	level down from root(root level = 0)
	 */
	private void printLevel(TreeNode<E> node, int level){
		if(node == null) return;
		// print right subtree
		printLevel(node.getRight(), level + 1);
		// print node: print spaces for level, then print value in node
		for(int i = 0; i < PRINT_SPACES * level; i++){
			System.out.print(" ");
		}
		System.out.println(node.getValue());
		// print left subtree
		printLevel(node.getLeft(), level + 1);
	}
	public TreeNode<E> getRoot(){
        return root;
    }
}
