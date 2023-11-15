package comp2402a4;
import java.util.Arrays;
public class MaxTree {
    
    private int[] array;
    private int first_zero;
    private int start_of_stack;

    public MaxTree() {
        this.array = new int[524288 + 1000000];
        this.first_zero = (this.array.length - 1) / 2;
        this.start_of_stack = (this.array.length - 1) / 2;

    }

    public void push(int x) {
        this.array[this.first_zero] = x;

        //go up algorithm

        int current_index = this.first_zero;
        while (current_index != 0) {
            int parent_index = this.getParent(current_index);
            int parent_value = this.array[parent_index];
            int max_value_of_children = Math.max(this.array[getLeftChild(parent_index)], this.array[getRightChild(parent_index)]);

            if (parent_value < max_value_of_children) {
                this.array[parent_index] = max_value_of_children;
            }
            current_index = parent_index;

        }


        this.first_zero++;

    }

    public void set(int index, int element) {
        index += this.start_of_stack;

        this.array[index] = element;

        int current_index = index;
        while (current_index != 0) {
            int parent_index = this.getParent(current_index);
            int parent_value = this.array[parent_index];
            int max_value_of_children = Math.max(this.array[getLeftChild(parent_index)], this.array[getRightChild(parent_index)]);

            this.array[parent_index] = max_value_of_children;
            
            current_index = parent_index;

        }


    }

    public void pop() {
        this.first_zero --;
        int index =  this.first_zero;

        this.array[index] = 0;

        int current_index = index;
        while (current_index != 0) {
            int parent_index = this.getParent(current_index);
            int parent_value = this.array[parent_index];
            int max_value_of_children = Math.max(this.array[getLeftChild(parent_index)], this.array[getRightChild(parent_index)]);

            this.array[parent_index] = max_value_of_children;
            
            current_index = parent_index;

        }



    }

    public int getMax() {

        return this.array[0];
    }

    public int[] getData(){
        return this.array;
    }

    private int getParent(int i) {
        return (int) Math.floor((i-1)/2);
    }

    private int getLeftChild(int i) {
        return ((2*i) + 1);
    }

    private int getRightChild(int i) {
        return ((2*i) + 2);

    }



   
    
}
