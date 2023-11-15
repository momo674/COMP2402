package comp2402a4;

import java.util.Arrays;

public class roughwork {
    public static void main(String[] args) {
        MaxTree a = new MaxTree();
        a.push(1); System.out.println(Arrays.toString(a.getData()));
        a.push(2); System.out.println(Arrays.toString(a.getData()));
        a.pop(); System.out.println(Arrays.toString(a.getData()));
        a.set(0,100);  System.out.println(Arrays.toString(a.getData()));


    }
}
