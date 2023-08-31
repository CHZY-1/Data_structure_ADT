package adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Chapter 5 Sample Code
 * LinkedList.java A class that implements the ADT list by using a chain of nodes,
 with the node implemented as an inner class.
 */
public class LinkedList<T> implements ListInterface<T> {

  private Node firstNode; // reference to first node
  private int numberOfEntries;  	// number of entries in list

  public LinkedList() {
    clear();
  }

  @Override
  public final void clear() {
    firstNode = null;
    numberOfEntries = 0;
  }

  @Override
  public boolean add(T newEntry) {
    Node newNode = new Node(newEntry);	// create the new node
//    System.out.println("LinkedList new Node: " + newEntry);
    if (isEmpty()) {
      firstNode = newNode;
    } else {                        // add to end of nonempty list
      Node currentNode = firstNode;	// traverse linked list with p pointing to the current node
      while (currentNode.next != null) { // while have not reached the last node
        currentNode = currentNode.next;
      }
      currentNode.next = newNode; // make last node reference new node
    }

    numberOfEntries++;

    return true;
  }

  @Override
  public boolean add(int newPosition, T newEntry) { // OutOfMemoryError possible
    boolean isSuccessful = true;

    if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
      Node newNode = new Node(newEntry);

      if (isEmpty() || (newPosition == 1)) { // case 1: add to beginning of list
        newNode.next = firstNode;
        firstNode = newNode;
      } else {								// case 2: list is not empty and newPosition > 1
        Node nodeBefore = firstNode;
        for (int i = 1; i < newPosition - 1; ++i) {
          nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
        }

        newNode.next = nodeBefore.next;	// make new node point to current node at newPosition
        nodeBefore.next = newNode;		// make the node before point to the new node
      }

      numberOfEntries++;
    } else {
      isSuccessful = false;
    }

    return isSuccessful;
  }

  @Override
  public T remove(int givenPosition) {
    T result = null;                 // return value

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      if (givenPosition == 1) {      // case 1: remove first entry
        result = firstNode.data;     // save entry to be removed
        firstNode = firstNode.next;
      } else {                         // case 2: givenPosition > 1
        Node nodeBefore = firstNode;
        for (int i = 1; i < givenPosition - 1; ++i) {
          nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
        }
        result = nodeBefore.next.data;  // save entry to be removed
        nodeBefore.next = nodeBefore.next.next;	// make node before point to node after the
      } 																// one to be deleted (to disconnect node from chain)

      numberOfEntries--;
    }

    return result; // return removed entry, or null if operation fails
  }

  @Override
  public boolean replace(int givenPosition, T newEntry) {
    boolean isSuccessful = true;

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      Node currentNode = firstNode;
      for (int i = 0; i < givenPosition - 1; ++i) {
        currentNode = currentNode.next;		// advance currentNode to next node
      }
      currentNode.data = newEntry;	// currentNode is pointing to the node at givenPosition
    } else {
      isSuccessful = false;
    }

    return isSuccessful;
  }

  @Override
  public T getEntry(int givenPosition) {
    T result = null;

    if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
      Node currentNode = firstNode;
      for (int i = 0; i < givenPosition - 1; ++i) {
        currentNode = currentNode.next;		// advance currentNode to next node
      }
      result = currentNode.data;	// currentNode is pointing to the node at givenPosition
    }

    return result;
  }

  @Override
  public boolean contains(T anEntry) {
    boolean found = false;
    Node currentNode = firstNode;

    while (!found && (currentNode != null)) {
      if (anEntry.equals(currentNode.data)) {
        found = true;
      } else {
        currentNode = currentNode.next;
      }
    }
    return found;
  }

  @Override
  public int getNumberOfEntries() {
    return numberOfEntries;
  }

  @Override
  public boolean isEmpty() {
    boolean result;

    result = numberOfEntries == 0;

    return result;
  }

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public String toString() {
    String outputStr = "";
    Node currentNode = firstNode;
    while (currentNode != null) {
      outputStr += currentNode.data + "\n";
      currentNode = currentNode.next;
    }
    return outputStr;
  }


  // Iterator Function
  public LinkedListIterator iterator() {
    return new LinkedListIterator();
  }

  private class LinkedListIterator implements Iterator<T> {
    private Node currentNode;            // Current node being pointed by the iterator
    private Node lastReturnedNode;      // Keep track of the last returned node

    /**
     * Constructor for the LinkedListIterator.
     * Initializes the iterator to start from the first node.
     */
    public LinkedListIterator() {
      currentNode = firstNode;
      lastReturnedNode = null;
    }

    /**
     * Checks if there is a next node in the linked list.
     *
     * @return true if there is a next node, false otherwise.
     */
    public boolean hasNext() {
      return currentNode != null;
    }

    /**
     * Moves the iterator to the next node and returns the data of the current node.
     *
     * @return the data of the current node.
     * @throws NoSuchElementException if there is no next node.
     */
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T data = currentNode.data;
      lastReturnedNode = currentNode; // Save the last returned node
      currentNode = currentNode.next;
      return data;
    }

    /**
     * Removes the last returned node from the linked list.
     * Can only be called after the next() method.
     *
     * @throws IllegalStateException if remove() is called without a preceding next() call.
     */
    public void remove() {
      if (lastReturnedNode == null) {
        throw new IllegalStateException("remove() can only be called after next()");
      }

      if (lastReturnedNode == firstNode) {
        firstNode = firstNode.next;
      } else {
        Node nodeBefore = firstNode;
        while (nodeBefore.next != lastReturnedNode) {
          nodeBefore = nodeBefore.next;
        }
        nodeBefore.next = lastReturnedNode.next;
      }

      lastReturnedNode = null; // Reset lastReturnedNode
    }
  }


  private class Node {

    private T data;
    private Node next;

    private Node(T data) {
      this.data = data;
      this.next = null;
    }

    private Node(T data, Node next) {
      this.data = data;
      this.next = next;
    }
  }

}
