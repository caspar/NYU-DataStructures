/**
 * My implementation of a reference-based Stack ADT.
 * The Stack class controls the deployment and organization of Node objects.
 * Methods are detailed in the assignment guidelines.
 * @author Caspar Lant
 * @see github.com/caspar
 * @see casparlant.com
 */
public class Stack<E>{

    private Node<E> head;

    public Stack(){
    }

    /**
     * Tests if the stack this instance of Stack is empty.
     * @return A boolean representing the status of the stack's emptiness
     */
    public boolean empty(){
        return head == null;
    }

    /**
     * Returns the element at the top of this stack without removing it from the stack.
     * @return The element at the top of the stack, found easily using the head reference of type Node
     */
    public E peek(){
        if (head == null)
            return null;
        return head.getData();
    }

    /**
     * Removes the element at the top of this stack and returns that element as the value of this function.
     * @return The element at the top of the stack, found easily using the head reference of Node - type.
     */
    public E pop(){
        E data = head.getData();
        head = head.getNext();
        return data;
    }

    /**
     * Pushes an item onto the top of this stack. Returns the item itself
     * @param  item Genereic object to be positioned on top of stack
     * @return      The same generic object.
     */
    public E push(E item){
        Node<E> n = new Node<E>(item);
        n.setNext(head);
        head = n;
        return item;
    }
}
