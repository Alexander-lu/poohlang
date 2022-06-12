class Solution {
    public static void main(String[] args) {
//        System.out.println(mySqrt(2147395600));
    }
    public boolean isPerfectSquare(int num) {
        if (num == 0) {
            return false;
        }
        float size = 0;
        while (true) {
            if(size*size>num){
                size++;
                break;
            }
            size++;
        }
        int[] nums = new int[(int)size];
        for (int i = 1; i <= nums.length; i++) {
            nums[i-1]=i*i;
        }
        float left = 0;
        float right = nums.length-1;
        float middle =0;
        while (left <= right){
            middle = (left + right )/2;
            if(nums[(int)middle]> num){
                right = middle-1;
            }else if (nums[(int)middle]<num){
                left = middle +1;
            }else if(nums[(int)middle] == num){
                return true;
            }
        }
    return  false;
    }
}
