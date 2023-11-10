package comp2402a4;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class UltraFast implements UltraStack {
    private List<Integer> ds;
    private PriorityQueue<Integer> maxdata;
    private HashMap<Integer, Integer> indexMap;

    public UltraFast() {
        ds = new ArrayList<>();
        maxdata = new PriorityQueue<>(Collections.reverseOrder());
        indexMap = new HashMap<>();
    }

    public void push(int x) {
        ds.add(x);
        maxdata.offer(x);
        indexMap.put(x, ds.size() - 1);
    }

    public Integer pop() {
        if (ds.isEmpty())
            return null;

        int item = ds.remove(ds.size() - 1);
        maxdata.remove(item);
        indexMap.remove(item);
        return item;
    }

    public Integer get(int i) {
        if (i < 0 || i >= ds.size())
            return null;
        return ds.get(i);
    }

    public Integer set(int i, int x) {
        if (i < 0 || i >= ds.size()) {
            return null;
        }

        int old = ds.get(i);

        // Remove old value from maxdata and indexMap
        maxdata.remove(old);
        indexMap.remove(old);

        // Set new value in the list and update maxdata and indexMap
        ds.set(i, x);
        maxdata.offer(x);
        indexMap.put(x, i);

        return old;
    }

    public Integer max() {
        return maxdata.peek();
    }

    public long ksum(int k) {
        long sum = 0;
        for (int i = 0; i < k && i < ds.size(); i++)
            sum += ds.get(ds.size() - 1 - i);
        return sum;
    }

    public int size() {
        return ds.size();
    }

    public Iterator<Integer> iterator() {
    return ds.iterator();
  }
}
