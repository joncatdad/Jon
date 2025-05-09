import java.util.NoSuchElementException;

/**
 *	SinglyLinkedList -(description)
 *
 *	@author	Jonathan Chen
 *	@since	April 29, 2025
 */
public class SinglyLinkedList<E extends Comparable<E>>{
	/* Fields */
	private ListNode<E> head, tail; // head and tail pointers to list
	/* No-args Constructors */
	public SinglyLinkedList(){}
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<E> oldList){
		if(oldList.head == null){
			this.head = this.tail = null;
		}
		else{
			ListNode<E> currentOld = oldList.head;
			this.head = new ListNode<>(currentOld.getValue());
			ListNode<E> currentNew = this.head;
	
			currentOld = currentOld.getNext();
			while(currentOld != null){
				ListNode<E> newNode = new ListNode<>(currentOld.getValue());
				currentNew.setNext(newNode);
				currentNew = newNode;
				currentOld = currentOld.getNext();
			}
			this.tail = currentNew;
		}
	}
	/**	Clears the list of elements */
	public void clear(){
		head = null;
		tail = null;
	}
	/**	Add the object to the end of the list
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(E obj){
		ListNode<E> newNode = new ListNode<>(obj);
		if(head == null){
			head = tail = newNode;
		}
		else{
			tail.setNext(newNode);
			tail = newNode;
		}
		return true;
	}
	/**	Add the object at the specified index
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, E obj){
		if(index < 0 || index > size()){
			throw new NoSuchElementException();
		}
		ListNode<E> newNode = new ListNode<>(obj);
		if(index == 0){
			newNode.setNext(head);
			head = newNode;
			if(tail == null) tail = head;
		}
		else{
			ListNode<E> prev = get(index - 1);
			newNode.setNext(prev.getNext());
			prev.setNext(newNode);
			if(newNode.getNext() == null){
				tail = newNode;
			}
		}
		return true;
	}
	/**	@return the number of elements in this list */
	public int size(){
		int count = 0;
		ListNode<E> current = head;
		while(current != null){
			count++;
			current = current.getNext();
		}
		return count;
	}
	/**	Return the ListNode at the specified index
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<E> get(int index){
		if(index < 0){
			throw new NoSuchElementException();
		}
		ListNode<E> current = head;
		for(int i = 0; i < index; i++){
			if(current == null){
				throw new NoSuchElementException();
			}
			current = current.getNext();
		}
		if(current == null){
			throw new NoSuchElementException();
		}
		return current;
	}
	/**	Replace the object at the specified index
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E set(int index, E obj){
		ListNode<E> target = get(index);
		E oldValue = target.getValue();
		target.setValue(obj);
		return oldValue;
	}
	/**	Remove the element at the specified index
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public E remove(int index){
		if(index < 0 || head == null){
			throw new NoSuchElementException();
		}
		if(index == 0){
			E value = head.getValue();
			head = head.getNext();
			if(head == null) tail = null;
			return value;
		}
		ListNode<E> prev = get(index - 1);
		ListNode<E> toRemove = prev.getNext();
		if(toRemove == null){
			throw new NoSuchElementException();
		}
		prev.setNext(toRemove.getNext());
		if(toRemove == tail){
			tail = prev;
		}
		return toRemove.getValue();
	}
	/**	@return	true if list is empty; false otherwise */
	public boolean isEmpty(){
		return head == null;
	}
	/**	Tests whether the list contains the given object
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(E object){
		ListNode<E> current = head;
		while(current != null){
			if(current.getValue().equals(object)) return true;
			current = current.getNext();
		}
		return false;
	}
	/**	Return the first index matching the element
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(E object){
		int index = 0;
		ListNode<E> current = head;
		while(current != null){
			if(current.getValue().equals(object)) return index;
			current = current.getNext();
			index++;
		}
		return -1;
	}
	/**	Prints the list of elements */
	public void printList(){
		ListNode<E> ptr = head;
		while(ptr != null){
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
}
