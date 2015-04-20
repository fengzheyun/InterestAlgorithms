/*
Given an array:

[a_1, a_2, ..., a_N, b_1, b_2, ..., b_N, c_1, c_2, ..., c_N ]

convert it to:

[a_1, b_1, c_1, a_2, b_2, c_2, ..., a_N, b_N, c_N]

in-place using constant extra space.
*/

public class convertArray{
    convertArray( ){}
   
    public void excute(){
        int[] arr = {1, 2, 3, 4, 5, 11, 12, 13, 14, 15, 21, 22, 23, 24, 25};
        convertArrayO1( arr );
        for( int i=0;i<arr.length-1;i++ )
            System.out.print( arr[i] +"," );
        System.out.println(arr[arr.length-1]);
    }
  
    public void convertArrayO1( int[] arr ){//take O(1) space
        int N = arr.length/3;
        for(int i=1; i<arr.length-1; i++ ){
            int oddIdx = getIndexPrevStep(i, N);
            while( oddIdx<i)
                oddIdx = getIndexPrevStep( oddIdx, N );
            if( oddIdx>i){
                arr[i] ^= arr[oddIdx];
                arr[oddIdx] ^= arr[i];
                arr[i] ^= arr[oddIdx];
            }
        }
    }
    
    private int getIndexPrevStep(int i， int N){
        return (i%3)*N+i/3;
    }
  
}
