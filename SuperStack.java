package Test_EA;

import java.io.PrintWriter;
import java.util.*;


public class test {
	
	/**	Super Stack
	 * Given a stack, empty at the start, and a series of operations on that stack, you need to take a "peek" at the value
	 * on top of the stack and print it to output after every operation. If stack is empty then print "EMPTY".
	 * 
	 * Allowable Operations:
	 * push a 	// push an element with value a to the top of the stack
	 * pop		// pop the top element from the stack
	 * inc x d	// add the value d to each of the bottom x elements on the stack
	 * 
	 * Input format:
	 * n - Integer, number of operations
	 * n lines with exactly one operation per line
	 * 
	 * Output format:
	 * n lines of text, with the element at the top of the stack each line
	 * If the stack is empty, then print "EMPTY"
	 * 
	 * Sample Input:		Output:
	 * 12
	 * push 4				4
	 * pop					EMPTY
	 * push 3				3
	 * push 5				5
	 * push 2				2
	 * inc 3 1				3
	 * pop					6
	 * push 1				1
	 * inc 2 2				1
	 * push 4				4
	 * pop					1
	 * pop					8
	 * 
	 * Constraints:
	 * -1,000,000,000 <= a <= 1,000,000,000
	 * -1,000,000,000 <= d <= 1,000,000,000
	 * 0 <= n <= 500,000
	 * 1<= x <= size of the stack at the time of operation
	 * */
	
	@SuppressWarnings("resource")
	public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        Scanner sc = new Scanner( System.in );
        PrintWriter writer = new PrintWriter(System.out);
		int N = sc.nextInt();
        Stack<Integer> stk = new Stack<Integer>();
        List<Long> list = new ArrayList<Long>();
        
        for(int i=0;i<N;i++){
            String op = sc.next();
            if( op.equals("pop") ){
                if( !stk.isEmpty() ){
                    stk.pop();
                    long temp = list.get(list.size()-1);
                    list.remove( list.size()-1);
                    if( !list.isEmpty() ){
                    	temp += list.get( list.size()-1);
                    	list.set(list.size()-1, temp);
                    }
                }
            }else if( op.equals("push") ){
                stk.push( sc.nextInt() );
                list.add( (long)0 );
            }else{
            	int x = sc.nextInt();
            	long d = ((long)sc.nextInt())+list.get(x-1);
            	list.set(x-1, d);
            }
            if( stk.isEmpty() )
            	writer.println( "EMPTY" );
            else{
            	writer.println( stk.peek()+list.get(list.size()-1) );
            }
        }
        writer.flush();
        writer.close();
    }
}
