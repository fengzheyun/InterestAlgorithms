/*
Given an array of integers, find the kth element in the sorted order (not the kth distinct element). 

So, if the array is [3, 1, 2, 1, 4] and k is 3 then the result is 2, because it’s the 3rd element 
in sorted order (but the 3rd distinct element is 3).
*/

public class kthLargestElementInArray{
    kthLargestElementInArray(){}
    
    public void excute(){
        int[] arr = {1, 4, 2, 7, 3, 6, 4, 2, 3};
        int k = 3;
        System.out.println( kthLargestElementInArray1(arr, k) );
    }
    
    public int kthLargestElementInArray1( int[] arr, int k){//sorting, time O(nlogn)
        if( null==arr || 0==arr.length )
            return -1;
        if( k>arr.length )
            return -1;
        
        Arrays.sort( arr );
        return arr[k-1];
    }
    
    public int kthLargestElementInArray2( int[] arr, int k){//quick sort: best time O(n), worst O(n^2)
        if( null==arr || 0==arr.length )
            return -1;
        if( k>arr.length )
            return -1;
            
        int i= 0, j = arr.length-1, pivot = arr.length-1; 
        while( pivot!=k-1){
            if( k-1<pivot ){
                j = pivot-1;
            }else
                i = pivot+1;
            pivot = pivotSorting( arr, i, j);
        }
        return arr[pivot];   
    }
    
    private int pivotSorting(int[] arr, int i, int j){
        int pivot = j--;
        while( i<=j ){
            while( i<pivot && arr[i]<=arr[pivot] )
                i++;
            while( j>=0 && arr[j]>=arr[pivot])
                j--;
            
            if( i<j ){
                arr[i] ^= arr[j];
                arr[j] ^= arr[i];
                arr[i++] ^= arr[j--];
            }else
                break;
        }
        
        int temp = arr[pivot];
        arr[pivot] = arr[i];
        arr[i] = temp;
        return i;
    }

}
