public class UnionFind {
    private int size;   // The number of elements in this union find
    private int[] sz;   // Used to track the size of each of the component
    private int[] id;   // id[i] points to the parent of i, if id[i] = i then i is a root node
    private int numComponents;  // Tracks the number of components in the union find

    public UnionFind(int size) {
        if(size <=0) throw new IllegalArgumentException("Size <= 0 is not allowed");
        this.size = numComponents = size;
        sz = new int[size];
        id = new int[size];

        for (int i = 0; i < size; i++) {
            id[i] = i;  // Link to itself (self root)
            sz[i] = 1;  // Each component is originally of size one
        }

    }

    // Find which components/set 'p' belongs to
    public int find(int p) {
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }

        // Path compression
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }
        return root;
    }

    // This is an alternative recursive formulation for the find method
    // public int find(int p) {
    //   if (p == id[p]) return p;
    //   return id[p] = find(id[p]);
    // }

    // Return whether or not the elements 'p' and
    // 'q' are in the same components/set.
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Return the size of the components/set 'p' belongs to
    public int componentSize(int p) {
        return sz[find(p)];
    }

    // Return the number of elements in this UnionFind/Disjoint set
    public int size() {
        return size;
    }

    // Returns the number of remaining components/sets
    public int components() {
        return numComponents;
    }

    public void unify(int p, int q) {

        // These elements are already in the same group!
        if (connected(p, q)) return;

        int root1 = find(p);
        int root2 = find(q);

        // Merge smaller component/set into the larger one.
        if (sz[root1] < sz[root2]) {
            sz[root2] += sz[root1];
            id[root1] = root2;
        } else {
            sz[root1] += sz[root2];
            id[root2] = root1;
        }

        // Since the roots found are different we know that the
        // number of components/sets has decreased by one
        numComponents--;
    }


}
