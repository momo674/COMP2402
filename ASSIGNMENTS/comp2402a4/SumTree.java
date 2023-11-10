class TreeNode {
    int data;
    TreeNode left, right;

    public TreeNode(int data) {
        this.data = data;
        this.left = this.right = null;
    }
}

public class SumTree {
    TreeNode root;

    // Function to convert a binary tree to a sum tree
    int convertToSumTree(TreeNode node) {
        // Base case
        if (node == null) {
            return 0;
        }

        // Store the old value
        int oldData = node.data;

        // Recursively convert left and right subtrees
        node.data = convertToSumTree(node.left) + convertToSumTree(node.right);

        // Return the sum of values of nodes in the subtree rooted with this node
        return node.data + oldData;
    }

    // Utility function to print inorder traversal of the tree
    void printInorder(TreeNode node) {
        if (node == null) {
            return;
        }
        printInorder(node.left);
        System.out.print(node.data + " ");
        printInorder(node.right);
    }

    public static void main(String[] args) {
        SumTree tree = new SumTree();

        /* Constructed binary tree is
                   10
                 /    \
                -2      6
               /   \   /  \
             8    -4  7    5
        */

        tree.root = new TreeNode(10);
        tree.root.left = new TreeNode(-2);
        tree.root.right = new TreeNode(6);
        tree.root.left.left = new TreeNode(8);
        tree.root.left.right = new TreeNode(-4);
        tree.root.right.left = new TreeNode(7);
        tree.root.right.right = new TreeNode(5);

        tree.convertToSumTree(tree.root);

        // Print inorder traversal of the converted tree to verify the result
        System.out.println("Inorder Traversal of the sum tree:");
        tree.printInorder(tree.root);
    }
}
