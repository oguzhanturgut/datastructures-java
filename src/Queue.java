import java.util.Iterator;
import java.util.LinkedList;

public class Queue<T> implements Iterable<T> {
    private LinkedList<T> list = new LinkedList<>();

    public Queue() {
    }

    public Queue(T elem) {
        offer(elem);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T peek() {
        if(isEmpty()) throw new RuntimeException("Queue is empty");
        return list.peekFirst();
    }

    public T poll() {
        if(isEmpty()) throw new RuntimeException("Queue is empty");
        return list.removeFirst();
    }

    public void offer(T elem) {
        list.addLast(elem);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}

