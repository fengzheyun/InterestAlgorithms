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
    
    private int kthLargestElementInArray3(int[] arr, int left, int right, int k){
        if( left==right )
            return k==left?arr[left]:-1;
	        
        int[] medians = getMedianOf5Medians( arr, left, right );
        int pVal = kthLargestElementInArray3(medians, 0, medians.length-1, medians.length/2);
        int[] pivots = partition3( arr, left, right, pVal );
        if( pivots[0]<=k && pivots[1]>=k )
            return pVal;
        else if(pivots[0]>k){
            return kthLargestElementInArray3(arr, left, pivots[0]-1, k);
        }else
            return kthLargestElementInArray3(arr, pivots[1]+1, right, k);
    }
	    
    private int[] getMedianOf5Medians(int[] arr, int left, int right){
        int[] medians = new int[(int)Math.ceil(1.0*(right-left+1)/5)];
        for( int i=0;i<medians.length;i++){
            int len = Math.min(5, right-left+1-5*i);
            int[] temp = new int[len];
            for(int j=0; j<len;j++)
                temp[j] = arr[left+5*i+j];
            Arrays.sort(temp);
            medians[i] = temp[len/2];
        }
        return medians;
    }
	    
    private int[] partition3(int[] arr, int left, int right, int pVal){
        int pivot = left;
        for(int i=left; i<=right; i++){
            if( arr[i]<pVal){
                int temp = arr[i];
                arr[i] = arr[pivot];
                arr[pivot++] = temp;
            }
        }
        int k = pivot;
        for(int i=pivot; i<=right; i++){
            if( arr[i]==pVal){
                int temp = arr[i];
                arr[i] = arr[k];
                arr[k++] = temp;
            }
        }
        return (new int[]{pivot,k-1});
    }

}
