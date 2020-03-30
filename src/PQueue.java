import java.util.*;

public class PQueue<T extends Comparable<T>> {

    private int heapSize = 0;
    private int heapCapacity = 0;

    private List<T> heap = null; //Dynamic list to track the elements
    private Map<T, TreeSet<Integer>> map = new HashMap<>(); //Hash map to track the positions of nodes

    public PQueue() {
        this(1);
    }

    public PQueue(int size) {
        heap = new ArrayList<>(size);
    }

//    heapify
    public PQueue(T[] elems) {
        heapSize = heapCapacity = elems.length;
        heap = new ArrayList<T>(heapCapacity);

        for (int i = 0; i < heapSize; i++) {
            mapAdd(elems[i], i);
            heap.add(elems[i]);
        }
        // heapify process
        for (int i = Math.max(0, heapSize / 2) - 1; i >= 0; i--) {
            sink(i);
        }
    }

    public PQueue(Collection<T> elems) {
        this(elems.size());
        for (T elem : elems) {
            add(elem);
        }
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void clear() {
        for (int i = 0; i < heapCapacity; i++) {
            heap.set(i, null);
            map.clear();
        }
    }

    public int size() {
        return heapSize;
    }

    public T peek() {
        if(isEmpty()) return null;
        return heap.get(0);
    }

    public T poll() {
        return removeAt(0);
    }

    public boolean contains(T elem) {
        if (elem == null) return false;
        return map.containsKey(elem);
    }

    public void add(T elem) {
        if(elem == null) throw new IllegalArgumentException();

        if (heapSize < heapCapacity) {
            heap.set(heapSize, elem);
        } else {
            heap.add(elem);
            heapCapacity++;
        }

        mapAdd(elem, heapSize);

        swim(heapSize);
        heapSize++;
    }

    private boolean less(int i, int j) {
        T node1 = heap.get(i);
        T node2 = heap.get(j);

        return node1.compareTo(node2) <= 0;
    }

    // bottom up node swim
    private void swim(int k) {
        int parent = (k - 1) / 2;

        while (k > 0 && less(k, parent)) {
            // exchange k with the parent
            swap(parent, k);
            k = parent;
            parent = (k - 1) / 2;
        }
    }

    private void sink(int k) {
        while (true) {
            int leftChild = 2 * k + 1;
            int rightChild = 2 * k + 2;
            int smallest = leftChild;

            if (rightChild < heapSize && less(rightChild, leftChild)) {
                smallest = rightChild;
            }

            if(leftChild >= heapSize || less(k, smallest)) break;

            swap(smallest, k);
            k = smallest;
        }
    }

    private void swap(int i, int j) {
        T i_elem = heap.get(i);
        T j_elem = heap.get(j);

        heap.set(i, j_elem);
        heap.set(j, j_elem);

        mapSwap(i_elem, j_elem, i, j);
    }

    // removes a particular element in the heap
    public boolean remote(T elem) {
    if(elem ==null) return false;

        Integer index = mapGet(elem);
        if(index != null) removeAt(index);
        return index != null;
    }

    private T removeAt(int i) {
        if(isEmpty()) return null;

        heapSize--;
        T removedData = heap.get(i);
        swap(i, heapSize);

        heap.set(heapSize, null);
        mapRemove(removedData, heapSize);

        if(i== heapSize) return removedData;

        T elem = heap.get(i);
        // try sinking
        sink(i);
        // swim if sinking did not work
        if (heap.get(i).equals(elem)) {
            swim(i);
        }

        return removedData;
    }

    public boolean isMinHeap(int k) {
        if(k >= heapSize) return true;

        int leftChild = 2 * k + 1;
        int rightChild = 2 * k + 2;

        if(leftChild < heapSize && !less(k,leftChild)) return false;
        if(rightChild < heapSize && !less(k,rightChild)) return false;

        // recursive check on both children
        return isMinHeap(leftChild) && isMinHeap(rightChild);
    }

    private void mapAdd(T value, int index) {

        TreeSet<Integer> set = map.get(value);

        if (set == null) {
            set = new TreeSet<>();
            set.add(index);
            map.put(value, set);
        } else {
            set.add(index);
        }
    }

    private void mapRemove(T value, int index) {
        TreeSet<Integer> set = map.get(value);
        set.remove(index);
        if (set.size() == 0) {
            map.remove(value);
        }
    }

    private Integer mapGet(T value) {
        TreeSet<Integer> set = map.get(value);
        if(set != null) return set.last();
        return null;
    }

    private void mapSwap(T value1, T value2, int value1Index, int value2Index) {
        Set<Integer> set1 = map.get(value1);
        Set<Integer> set2 = map.get(value2);

        set1.remove(value1Index);
        set2.remove(value2Index);

        set1.add(value1Index);
        set2.add(value2Index);
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
