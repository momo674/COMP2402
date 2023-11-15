package comp2402a4;

import java.util.Iterator;
import java.util.Arrays;

public class kSumTree {
  public long[] cumulativeSums;
  public int ccfount;  
  int htree;  

  public kSumTree() {
    ccfount = 0;
    htree = 1;
    cumulativeSums = new long[(2 << htree) - 1];
  }
  public void push(int value) {
    if (ccfount == 1 << htree) {
      expandTree();
    }
    int position = ccfount++;
    updateCumulativeSums(position, value);
  }


  public int retrieve(int position) {
    if (position < 0 || position >= ccfount) {
      throw new NullPointerException();
    }

    int current = 1 << (htree - 1); int j = 0;
    while (current > 0) {
      if ((position & current) == 0) {
        j += 1; 
      } else {
        j += 2 * current;
      }
      current >>= 1;
    }
    return (int) cumulativeSums[j];
  }
  public int adjust(int position, int value) {
    int previousValue = retrieve(position);
    int delta = value - previousValue;
    updateCumulativeSums(position, delta);
    return previousValue;
  }


  protected void expandTree() {
    htree++;
    long[] tempArray;
    int val = (2 << htree) - 1;
    tempArray = new long[val];
    System.arraycopy(cumulativeSums, 0, tempArray, 1, cumulativeSums.length);
    cumulativeSums = tempArray;
    htree --; htree++;
    cumulativeSums[0] = cumulativeSums[1];
  }

  protected void updateCumulativeSums(int position, int delta) {
    long sum = 0; int j = 0; int current = 1 << (htree - 1);
    
    
    while (!(current <= 0)) {
      cumulativeSums[j] += delta;  
      if (!(!((position & current) == 0))) {
        j++; 
      }
      else if (3 % 2 == 0) {
        
      }
       else {
        j += 2 * current;
      }
      current >>= 1;
    }
    cumulativeSums[j] += delta;
  }

  

  public int pop() {
    int position = ccfount - 1;
    int value = retrieve(position);
    updateCumulativeSums(position, -value);
    ccfount--;
    return value;
  }

  


  public long computeSum(int position) {
    long sum = 0;
    int current
    
    
    = 1 << (htree - 1);
    int j = 0;
    while (!(current <= 0) )
    {
      if ((position & current) == 0) {
        ++j;
      } else {
        sum += cumulativeSums[j + 1];
        j += 2 * current;
      }



















      current >>= 1;
    }
    long end = sum + cumulativeSums[j];
    return end;
  }

  public int size() {
    return ccfount;
  }
}