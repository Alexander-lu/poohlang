public class Solution {
    public static void main(String[] args) {
        TreeNode newLeft = new TreeNode(1);
        TreeNode newLeft2 = new TreeNode(2);
        newLeft.left = newLeft2;
        invertTree(null);
    }
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }else {
root.right = invertTree(root.left);
 root.left = invertTree(root.right);
            return root;
        }

    }
}
