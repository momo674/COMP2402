package comp2402a2;

import java.util.ArrayList;
import java.util.Stack;

import java.util.Iterator;

public class SuperFast implements SuperStack {
  
  public ArrayList<Integer> ds;
  public ArrayList<Integer> max_stack;
  private Integer max_value = null;
  public ArrayList<Long> sum_list;

  public SuperFast() {

    max_stack = new ArrayList<>();
    ds = new ArrayList<>();
    sum_list = new ArrayList<>();
    
  }


  // public void show_sum_list() {
  //   System.out.println(sum_list);
  // }

  public void push(Integer x) {
    if (ds.size() == 0) { //empty stack
      Long x_long = new Long(x);
      sum_list.add(x_long);
      max_stack.add(x);
      max_value = x;
      ds.add(x);
    }
    

    else {
      if (ds.size() == 1) {
        Long value = new Long(sum_list.get(sum_list.size() - 1) + x);
        sum_list.add(value);
      }
      else {
        Long value = new Long(sum_list.get(sum_list.size() - 1) + x);
        sum_list.add(value);
      }
       

      if (x >= max_value) {
        max_value = x;
      }
      max_stack.add(max_value);
      ds.add(x);
      
    }

    
  }

  public Integer pop() {

    if(size() <= 0){
        return null;
    }

    else{

      Integer saved = ds.remove(ds.size() - 1);
      max_stack.remove(max_stack.size() - 1);
      sum_list.remove(sum_list.size() - 1);
      if (ds.isEmpty()) {
        max_value = null;
      }
      else{
        max_value = max_stack.get(max_stack.size() - 1);

      }

      return saved;

    }
  }

  public Integer max() {

    return max_value;

  }

  public long ksum(int k) {
    if (k == 0) {
      return new Long(0);
    }
    if (k == 1) {
      return new Long(ds.get(ds.size() - 1));
    }
    
    else if (k == sum_list.size()) {
      return sum_list.get(k - 1); 
    }

    
    Long sum =  sum_list.get(sum_list.size() - 1) - sum_list.get(sum_list.size() - 1 - k);
    return sum;
  }

  public int size() {

    return ds.size();

  }

  public Iterator<Integer> iterator() {

    return ds.iterator();

  }
}