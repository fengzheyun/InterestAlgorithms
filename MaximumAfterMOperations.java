/*Given n integers, find out the maximum value after M operations. Each operation can be represented by (a,b,K), which
means add value K to every element from a to b. 

For example, N , M = 3, and three operations (a,b,K) are
2, 3, 100;
1, 2, 200;
4, 5, 100;

And the final maximum value is 300.
*/


/*
For each operation (a, b, k), there are two key/value data to describe the
operation:(a, k) and (b+1, -k), which means all the location starting from
point a will increase the weight by k and all the locations starting from b+
1 will decrease the weight by k. Maintain a BST of all key/value data sorted
by the location (the key of the data). When insert a new key/value data, if
a node with the key exists, update the value of the node by summing up the
new value and the old value in the node.

Finally, traverse the BST in order and sum up the value in the nodes
cumulatively, the cumulative sum at each node is the final weight for that
location.   
*/

public class TreeNode{
  int key;
  int val;
  TreeNode left;
  TreeNode right;
  
  TreeNode(int k, int v){
    key = k;
    val = v;
  }
  
  public insert(int k, int v){
    if( k==key ){
      val += v;
    }else if(k<key){
      if(left == null)
        left = new TreeNode(k,v);
      else
        left.insert(k,v);
    }else{
      if(right==null)
        right = new TreeNode(k,v);
      else
        right.insert(k,v);
    }
  }
}

public int maxAfterMOperations(int N, int M, List<int[]> operations ){
  TreeNode bst = null;
  for(int[] temp: operations ){
    bst.insert( temp[0], temp[2]);
    bst.insert( temp[1]+1, -temp[2]);
  }
  
  //Traversal the tree in order and compute the cumulative value
  Stack<TreeNode> stk = new tack<TreeNode>();
  TreeNode cur = root;
  int MaxVal = 0;
  int CumVal = 0;
  
  while( !stk.isEmpty() || cur!=null ){
    if( cur==null ){
      cur = stk.pop();
      CumVal += cur.val;
      MaxVal = CumVal>MaxVal?CumVal:MaxVal;
      cur = cur.right;
    }else{
      stk.push( cur );
      cur = cur.left;
    }
  }
  return MaxVal;
}

