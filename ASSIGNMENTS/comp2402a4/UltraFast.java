package comp2402a4;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class UltraFast implements UltraStack {
  List<Integer> ds;
  MaxTree max;
  kSumTree ksum;

  public UltraFast() {
    ds = new ArrayList<Integer>();
    max = new MaxTree();
    ksum = new kSumTree();
  }

  public void push(int x) {
      ds.add(x);
      max.push(x);
      ksum.push(x);
  }

  public Integer pop() {
    if(ds.size() == 0)
      return null;
    max.pop();
    ksum.pop();
    return ds.remove(ds.size()-1);
  }


  public Integer get(int i) {
    if(i < 0 || i >= ds.size())
      return null;
    return ds.get(i);
  }

  public Integer set(int i, int x) {
    if(i < 0 || i >= ds.size())
      return null;
    ksum.adjust(i,x);
    max.set(i,x);
    return ds.set(i, x);
  }

  public Integer max() {
    Integer m = null;
    if (!ds.isEmpty()) {
      return max.getMax();
    }
    return m;
  }

  public long ksum(int k) {
    if (this.ds.size() == 0) {return (long) 0;}
        if (k > ds.size()) {return ksum.cumulativeSums[0];}
        if (k == 0) {
            return (long) 0;
          }
          
          else if (k == ds.size()) {
            return ksum.cumulativeSums[0]; 
          }
      
          long sum =  ksum.cumulativeSums[0];
          return sum - ksum.computeSum(ds.size() - 1 - k);
  }

  public int size() {
    return ds.size();
  }

  public Iterator<Integer> iterator() {
    return ds.iterator();
  }
}
