package comp2402a2;

import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class DuperFast implements DuperDeque {
  private ArrayList<Integer> fstack;
  private ArrayList<Integer> fstackmax;
  private ArrayList<Long> fstacksum;
  
  private ArrayList<Integer> bstack;
  private ArrayList<Integer> bstackmax;
  private ArrayList<Long> bstacksum;
  private ArrayList<Integer> o = new ArrayList<>();

  private Iterator<Integer> dequeIterator;
  private int size = 0;
  public DuperFast() {
    fstack = new ArrayList<>();
    fstackmax = new ArrayList<>();
    fstacksum = new ArrayList<>();
   

    bstack = new ArrayList<>();
    bstackmax = new ArrayList<>();
    bstacksum = new ArrayList<>();
    

    DequeIterator<Integer> iter = new DequeIterator<>(fstack, bstack);
  }

  public void addFirst(Integer x) {
    if (fstack.size() == 0) { //empty stack
      Long x_long = new Long(x);
      fstacksum.add(x_long);
      fstackmax.add(x);
      fstack.add(x);
    }
    

    else {
      if (fstack.size() == 1) {
        Long value = new Long(fstacksum.get(fstacksum.size() - 1) + x);
        fstacksum.add(value);
      }
      else {
        Long value = new Long(fstacksum.get(fstacksum.size() - 1) + x);
        fstacksum.add(value);
      }
       
      Integer tmp = fstackmax.get(fstackmax.size() - 1);
      if (x >= tmp ) {
        tmp = x;
      }
      fstackmax.add(tmp);
      fstack.add(x);
      
    }
    size++;
    rebalance();
  }

  public void addLast(Integer x) {
    if (bstack.size() == 0) { //empty stack
      Long x_long = new Long(x);
      bstacksum.add(x_long);
      bstackmax.add(x);
      bstack.add(x);
      return;
    }
    

    else {

      if (bstack.size() == 1) {
        Long value = new Long(bstacksum.get(0) + x);
        bstacksum.add(value);
        bstack.add(x);

        Integer tmp = bstackmax.get(bstackmax.size() - 1);
      if (x >= tmp) {
        tmp = x;
      }
      bstackmax.add(tmp);
        return;
      }
      // else {
      //   Long value = new Long(bstacksum.get(bstacksum.size() - 1) + x);
      //   bstacksum.add(value);
      // }
       
      
      }
      Integer tmp = bstackmax.get(bstackmax.size() - 1);
      if (x >= tmp) {
        tmp = x;
      }
      bstackmax.add(tmp);
      Long value = new Long(bstacksum.get(bstacksum.size() - 1) + x);
        bstacksum.add(value);
      bstack.add(x);
      size++;
        rebalance();
    }
        

  

  public Integer removeFirst() {
    if (fstack.isEmpty() && bstack.isEmpty()) { //empty list
      return null;
    }

    if (fstack.isEmpty()) {
      Integer saved = bstack.remove(0);
      bstackmax.remove(0);
      bstacksum = new ArrayList<>();
      
      for (int i = 0; i < bstack.size(); i++) {
        if (i == 0) {
          bstacksum.add(new Long(bstack.get(i)));
        }
        else {
          bstacksum.add(new Long(bstack.get(i)) + bstacksum.get(i-1));
        }


      }
      size--;
      rebalance();
      return saved;

    }
    else{

      Integer saved = fstack.remove(fstack.size() - 1);
      fstackmax.remove(fstackmax.size() - 1);
      fstacksum.remove(fstacksum.size() - 1);
      
      size --;
      rebalance();
      return saved;

    }
  }

  public Integer removeLast() {
    if (fstack.isEmpty() && bstack.isEmpty()) {return null;}
    if (bstack.isEmpty()) {
      Integer saved = fstack.remove(0);
      fstackmax.remove(0);
      fstacksum = new ArrayList<>();
      
      for (int i = 0; i < bstack.size(); i++) {
        if (i == 0) {
          fstacksum.add(new Long(fstack.get(i)));
        }
        else {
          fstacksum.add(new Long(fstack.get(i)) + fstacksum.get(i-1));
        }


      }
      size--;
      rebalance();
      return saved;
    }
    if (size() <= 0) { //empty list
      return null;
    }
    else{

      Integer saved = bstack.remove(bstack.size() - 1);
      bstackmax.remove(bstackmax.size() - 1);
      bstacksum.remove(bstacksum.size() - 1);
      
      size--;
      rebalance();
      return saved;

    }
  }

  public Integer max() {
  //   System.out.println("FRONT STACK: " + fstack + " FRONT SUM: " + fstacksum);
  //  System.out.println("BACK STACK: " + bstack + " BACK SUM: " + bstacksum);
    //System.out.println("Front Stack: " + fstack);
    //System.out.println("Back Stack: " + bstack);
    if (fstack.isEmpty() && bstack.isEmpty()) {
      return null;
    }

    if (!fstack.isEmpty() && bstack.isEmpty()) {
      // System.out.println("front max stack: " + fstackmax);
      //   System.out.println("back max stack: empty");
      return  fstackmax.get(fstackmax.size() - 1);
    }

    if (fstack.isEmpty() && !bstack.isEmpty()) {
      // System.out.println("front max stack: empty ");
      //   System.out.println("back max stack: " + bstackmax);
      return bstackmax.get(bstackmax.size() - 1);
    }
    
    Integer r = null;
    Integer max_front = fstackmax.get(fstackmax.size() - 1);
    Integer max_back = bstackmax.get(bstackmax.size() - 1);
    // System.out.println("front max stack: " + fstackmax);
    //     System.out.println("back max stack: " + bstackmax);
    if (max_front >= max_back) {
      return max_front;
    }
    else {
      return max_back;
    }
    
  }

  public long ksumFirst(int k) {
    if (fstack.isEmpty() && bstack.isEmpty()) {
      return 0;
    }
    if (fstack.isEmpty()) {
      if (k == 0) {return 0;}
      int x = k;
      if (k > 0) {x = x -1;}
      return bstacksum.get(x);
    }
    // if (k == 0) {
    //   return new Long(0);
    // }
    if (k == 1) {
      return new Long(fstack.get(fstack.size() - 1));
    }
    
    else if (k == fstacksum.size()) {
      return fstacksum.get(k - 1); 
    }

    //what if k > front stack size() ?

    if (k > fstack.size()) {
      Long fstacktotalsum = fstacksum.get(fstacksum.size() - 1);
      int k_new = k - fstack.size();
      Long backstacksum = bstacksum.get(k_new - 1);
      return fstacktotalsum + backstacksum;
    }
    
    Long sum =  fstacksum.get(fstacksum.size() - 1) - fstacksum.get(fstacksum.size() - 1 - k);
    return sum;
  }

  public long ksumLast(int k) {
   
    /*NEW DESIGN */
    
    if (fstack.isEmpty() && bstack.isEmpty()) {
      return 0;
    }
    if (bstack.isEmpty()) {
      if (k == 0) {return 0;}
      if (k > 0) {k = k -1;}
      return fstacksum.get(k);
    }
    
    /*END */
    
    
    
    
    
    
    
    
    
    
    
    if (k == 0) {
      return new Long(0);
    }
    if (k == 1) {
      return new Long(bstack.get(bstack.size() - 1));
    }
    
    else if (k == bstacksum.size()) {
      return bstacksum.get(k - 1); 
    }

    //what if k > front stack size() ?

    if (k > bstack.size()) {
      Long bstacktotalsum = bstacksum.get(bstacksum.size() - 1);
      int k_new = k - bstack.size();
      Long frontstacksum = fstacksum.get(k_new - 1);
      return bstacktotalsum + frontstacksum;
    }
    // System.out.println(bstacksum.size() - 1);
    // System.out.println(bstack + " " + bstacksum);
    // System.out.println((bstacksum.size() - 1 - k));

    Long sum =  bstacksum.get(bstacksum.size() - 1) - bstacksum.get(bstacksum.size() - 1 - k);
    return sum;
  }

  public int size() {
    return fstack.size() + bstack.size();
  }

  public Iterator<Integer> iterator() {
    o.clear();
    for (int i = fstack.size() - 1; i >= 0; i--){
      o.add(fstack.get(i));
    }
    for (int j = 0; j < bstack.size(); j++) {
      o.add(bstack.get(j));
    }
    //System.out.println("FRONT: " + fstack + " BACK: " + bstack +"\n\n\n\n");
    //System.out.println("Size: " + size());
    return o.iterator();
    // // Check if the iterator is null or if it has no more elements
    // if (dequeIterator == null || !dequeIterator.hasNext()) {
    //     // Create a new iterator instance
    //     dequeIterator = new DequeIterator<>(fstack, bstack);
    // }
    // return dequeIterator;
}


  private void rebalance(){
    int n = size();
    if (n == 1) {return;}
    if (fstack.isEmpty() && !bstack.isEmpty()) {
      //System.out.println("REBALANCED --> FRONT IS EMPTY");
      //balance our main stacks
      int s = n/2 - fstack.size();
      ArrayList<Integer> l1 = new ArrayList<>();
      ArrayList<Integer> l2 = new ArrayList<>();
      l1.addAll(bstack.subList(0,s));
      Collections.reverse(l1);
      l1.addAll(fstack);
      l2.addAll(bstack.subList(s, bstack.size()));
      fstack = l1;
      bstack = l2;

      //balance our max stacks
      // int s_max = n/2 - fstackmax.size();
      // ArrayList<Integer> l1max = new ArrayList<>();
      // ArrayList<Integer> l2max = new ArrayList<>();
      // l1max.addAll(bstackmax.subList(0,s));
      // Collections.reverse(l1max);
      // l1max.addAll(fstackmax);
      // l2max.addAll(bstackmax.subList(s_max, bstackmax.size()));
      // fstackmax = l1max;
      // bstackmax = l2max;

      /*WORKING ON NEW WAY TO BALANCE MAX STACKS */

      
     
      


      fstacksum = new ArrayList<>();
      fstackmax = new ArrayList<>();

      bstacksum = new ArrayList<>();
      bstackmax = new ArrayList<>();
      for (int i = 0; i < fstack.size(); i++) {
        if (i == 0) {
          //fstack adding
          fstacksum.add(new Long(fstack.get(i)));

          //maxstack adding
          fstackmax.add(fstack.get(i));
        }
        else {
          //fstack adding
          
          fstacksum.add(new Long(fstack.get(i) + fstacksum.get(i-1)));

          //max adding
          if (fstack.get(i) >= fstackmax.get(fstackmax.size() - 1)) {
            fstackmax.add(fstack.get(i));
          }
          else {
            fstackmax.add(fstackmax.get(fstackmax.size() - 1));
          }
        }
      }
      for (int j = 0; j < bstack.size(); j++) {
        if (j == 0) {
          bstacksum.add(new Long(bstack.get(j)));

          bstackmax.add(bstack.get(j));
        }
        else {
          bstacksum.add(new Long(bstack.get(j)) + bstacksum.get(j-1));


          if (bstack.get(j) >= bstackmax.get(bstackmax.size() - 1)) {
            bstackmax.add(bstack.get(j));
          }
          else {
            bstackmax.add(bstackmax.get(bstackmax.size() - 1));
          }
        }
      }
    }

    else if (bstack.isEmpty() && !fstack.isEmpty()) {
            //System.out.println("REBALANCED --> BACK IS EMPTY");

      //balance our main stacks
      int s = fstack.size() - n/2;
      ArrayList<Integer> l1 = new ArrayList<>();
      ArrayList<Integer> l2 = new ArrayList<>();
      l1.addAll(fstack.subList(s, fstack.size()));
      l2.addAll(fstack.subList(0, s));
      Collections.reverse(l2);
      l2.addAll(bstack);
      fstack = l1;
      bstack = l2;

      // //balance our max stacks
      // int smax = fstackmax.size() - n/2;
      // ArrayList<Integer> l1max = new ArrayList<>();
      // ArrayList<Integer> l2max = new ArrayList<>();
      // l1max.addAll(fstackmax.subList(s, fstackmax.size()));
      // l2max.addAll(fstackmax.subList(0, s));
      // Collections.reverse(l2max);
      // l2max.addAll(bstackmax);
      // fstackmax = l1max;
      // bstackmax = l2max;

      



      fstacksum = new ArrayList<>();
      fstackmax = new ArrayList<>();

      bstacksum = new ArrayList<>();
      bstackmax = new ArrayList<>();
      for (int i = 0; i < fstack.size(); i++) {
        if (i == 0) {
          //fstack adding
          fstacksum.add(new Long(fstack.get(i)));

          //maxstack adding
          fstackmax.add(fstack.get(i));
        }
        else {
          //fstack adding
          //int tmp = fstack.
          fstacksum.add( new Long(fstack.get(i) + fstacksum.get(i-1)));

          //max adding
          if (fstack.get(i) >= fstackmax.get(fstackmax.size() - 1)) {
            fstackmax.add(fstack.get(i));
          }
          else {
            fstackmax.add(fstackmax.get(fstackmax.size() - 1));
          }
        }
      }
      for (int j = 0; j < bstack.size(); j++) {
        if (j == 0) {
          bstacksum.add(new Long(bstack.get(j)));

          bstackmax.add(bstack.get(j));
        }
        else {
          bstacksum.add(new Long(bstack.get(j)) + bstacksum.get(j-1));


          if (bstack.get(j) >= bstackmax.get(bstackmax.size() - 1)) {
            bstackmax.add(bstack.get(j));
          }
          else {
            bstackmax.add(bstackmax.get(bstackmax.size() - 1));
          }
        }
      }
    }
  }
}