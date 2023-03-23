package com.nighthawk.hacks.classDataStruct;
import java.util.Iterator;

/**
 * TeamQueue: custom implementation
 * @author     John Mortensen
 *
 * 1. Uses custom LinkedList of Generic type T
 * 2. Implements Iterable
 * 3. "has a" LinkedList for head and tail
 */
public class TeamQueue<T> implements Iterable<T> {
    LinkedList<T> head = null, tail = null;

    /**
     *  Add a new object at the end of the TeamQueue,
     *
     * @param  data,  is the data to be inserted in the TeamQueue.
     */
    public void add(T data) {
        // add new object to end of TeamQueue
        LinkedList<T> tail = new LinkedList<>(data, null);

        if (this.head == null)  // initial condition
            this.head = this.tail = tail;
        else {  // nodes in TeamQueue
            this.tail.setNextNode(tail); // current tail points to new tail
            this.tail = tail;  // update tail
        }
    }

    /**
     *  Returns the data of head.
     *
     * @return  data, the dequeued data
     */
    public T delete() {
        T data = this.peek();
        if (this.tail != null) { // initial condition
            this.head = this.head.getNext(); // current tail points to new tail
            if (this.head != null) {
                this.head.setPrevNode(tail);
            }
        }
        return data;
    }

    /**
     *  Returns the data of head.
     *
     * @return  this.head.getData(), the head data in TeamQueue.
     */
    public T peek() {
        return this.head.getData();
    }

    /**
     *  Returns the head object.
     *
     * @return  this.head, the head object in TeamQueue.
     */
    public LinkedList<T> getHead() {
        return this.head;
    }

    /**
     *  Returns the tail object.
     *
     * @return  this.tail, the last object in TeamQueue
     */
    public LinkedList<T> getTail() {
        return this.tail;
    }

    /**
     *  Returns the iterator object.
     *
     * @return  this, instance of object
     */
    public Iterator<T> iterator() {
        return new QueueIterator1<>(this);
    }
}

/**
 * TeamQueue Iterator
 *
 * 1. "has a" current reference in TeamQueue
 * 2. supports iterable required methods for next that returns a data object
 */
class QueueIterator1<T> implements Iterator<T> {
    LinkedList<T> current;  // current element in iteration

    // Returns the head of the list
    public QueueIterator1(TeamQueue<T> q) {
        current = q.getHead();
    }

    // hasNext informs if next element exists
    public boolean hasNext() {
        return current != null;
    }

    // next returns data object and advances to next position in TeamQueue
    public T next() {
        T data = current.getData();
        current = current.getNext();
        return data;
    }
}

/**
 * TeamQueue Manager
 * 1. "has a" TeamQueue
 * 2. support management of TeamQueue tasks (aka: titling, adding a list, printing)
 */
class QueueManager1<T> {
    // TeamQueue data
    static public boolean DEBUG = false;
    private final String name; // name of TeamQueue
    private long count = 0; // number of objects in TeamQueue
    private final TeamQueue<T> TeamQueue = new TeamQueue<>(); // TeamQueue object

    /**
     *  TeamQueue constructor
     *  Title with empty TeamQueue
     */
    public QueueManager1(String name) {
        this.name = name;
    }

    /**
     *  TeamQueue constructor
     *  Title with series of Arrays of Objects
     */
    @SafeVarargs
    public QueueManager1(String name, T[]... seriesOfObjects) {
        this.name = name;
        this.addList(seriesOfObjects);
    }

    /**
     *  TeamQueue constructor
     *  Title with empty TeamQueue
     */
    public long size() {
        return count;
    }

    /**
     * Add a list of objects to TeamQueue
     */
    @SafeVarargs
    public final void addList(T[]... seriesOfObjects) {
        for (T[] objects: seriesOfObjects)
            for (T data : objects) {
                this.add(data);
            }
    }

    /**
     * Delete or dequeue all objects
     */
    public void deleteList() {
        while (this.TeamQueue.getHead() != null) {
            this.delete();
        }
    }

    /**
     * Add an object to TeamQueue
     */
    public void add(T data) {
        this.TeamQueue.add(data);
        this.count++;
        if (DEBUG) {
            System.out.println("Enqueued data: " + data);
            printQueue();
        }
    }

    /**
     * Remove an object from the TeamQueue
     */
    public T delete() {
        T data = this.TeamQueue.delete();
        this.count--;
        if (DEBUG) {
            System.out.println("Dequeued data: " + data);
            printQueue();
        }
        return data;
    }

    /**
     * Print objects from TeamQueue
     */
    public void printQueue() {
        System.out.print(this.name + " count: " + this.size());
        System.out.print(", data: ");
        if (this.TeamQueue.getHead() == null) {
            System.out.println( "null");
        } else {
            for (Object o : this.TeamQueue)
                System.out.print(o + " ");
            System.out.println();
        }
    }
}

/**
 * Driver Class
 * Tests TeamQueue with string, integers, and mixes of Classes and types
 */
class QueueTester1 {
    public static void main(String[] args)
    {
        // Create iterable TeamQueue of Words
        QueueManager1.DEBUG = false;
        String[] words = new String[] { "mads", "slimy", "snakes", "sallying", "slowly", "slithered", "southward"};
        QueueManager1<String> qWords = new QueueManager1<>("Words", words );
        qWords.printQueue();
        qWords.deleteList();

        // Create iterable TeamQueue of Integers
        QueueManager1.DEBUG = false;
        Object[] pointvals = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        QueueManager1<Object> qNums = new QueueManager1<>("Integers", pointvals );
        qNums.printQueue();
        qNums.deleteList();

        // Create iterable TeamQueue of NCS Generics
        QueueManager1.DEBUG = false;
        Animal.setOrder(Animal.KeyType.name);
        Alphabet.setOrder(Alphabet.KeyType.letter);
        Cupcakes.setOrder(Cupcakes.KeyType.flavor);
        // Illustrates use of a series of repeating arguments
        QueueManager1<Generics> qGenerics = new QueueManager1<>("Custom Generics",
                Alphabet.alphabetData(),
                Animal.animalData(),
                Cupcakes.cupCakeData()
        );
        qGenerics.printQueue();
        qGenerics.deleteList();

        // Create iterable TeamQueue of Mixed types of data
        QueueManager1.DEBUG = false;
        QueueManager1<Object> qMix = new QueueManager1<>("Mixed");
        qMix.add("Start");
        qMix.addList(
                words,
                Alphabet.alphabetData(),
                Animal.animalData(),
                Cupcakes.cupCakeData()
        );
        qMix.add("End");
        qMix.printQueue();
        qMix.deleteList();
    }
}
