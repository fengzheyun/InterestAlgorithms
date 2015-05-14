public class numberOfIslands_200{//connected components
		numberOfIslands_200(){}
		
		public void excute(){
//			int[][] A = {{0,0,0,1,0},{0,1,1,1,1},{0,0,0,0,1},{0,1,1,0,1},{0,0,1,0,1}};
			int[][] A = new int[5][];
	        A[0] = new int[]{0, 0, 1, 1, 1};
	        A[1] = new int[]{0, 1, 1, 1, 1};
	        A[2] = new int[]{0, 1, 0, 0, 1};
	        A[3] = new int[]{0, 0, 0, 0, 1};
	        A[4] = new int[]{0, 0, 0, 0, 1};
	        
			if ( isValidCrosswordO1( A ) )
				System.out.println( "true");
			else
				System.out.println("false");
		}
		// if only one island: true-yes, false-no
		// dfs: time:O(mn) space O(1)
		private boolean isValidCrosswordO1(int[][] puzzle){//4-connectivity
			boolean firstScan = true;
			for(int r=0;r<puzzle.length;r++){
				for(int c=0;c<puzzle[0].length;c++){
					if( 1==puzzle[r][c] )
						continue;				
					if( !firstScan && 0==puzzle[r][c])
						return false;
					
					int x = r, y = c;
					while(true){
						while( 0==puzzle[x][y] ){
							puzzle[x][y] = 2;
							if( x+1<puzzle.length && 0==puzzle[x+1][y] ){
								x += 1;
								continue;
							}
							if( y+1<puzzle[0].length && 0==puzzle[x][y+1] ){
								y += 1;
								continue;
							}
							if( x-1>=0 && 0==puzzle[x-1][y] ){
								x -= 1;
								continue;
							}
							if( y-1>=0 && 0==puzzle[x][y-1] ){
								y -= 1;
							}
						}
						
						while( 2==puzzle[x][y] && !((x+1<puzzle.length && 0==puzzle[x+1][y])|| (y+1<puzzle[0].length && 0==puzzle[x][y+1]) 
								|| (x-1>=0 && 0==puzzle[x-1][y]) || (y-1>=0 && 0==puzzle[x][y-1])) ){
							puzzle[x][y] = 3;
							if( x-1>=0 && 2==puzzle[x-1][y] ){
								x -= 1;
								continue;
							}
							if( y+1<puzzle[0].length && 2==puzzle[x][y+1] ){
								y += 1;
								continue;
							}
							if( x+1<puzzle.length && 2==puzzle[x+1][y] ){
								x += 1;
								continue;
							}
							if( y-1>=0 && 2==puzzle[x][y-1] ){
								y -= 1;
							}
						}
						
						if(x+1<puzzle.length && 0==puzzle[x+1][y])
							x += 1;
						else if(y+1<puzzle[0].length && 0==puzzle[x][y+1]) 
							y += 1;
						else if(x-1>=0 && 0==puzzle[x-1][y])
							x -= 1;
						else if (y-1>=0 && 0==puzzle[x][y-1])
							y -= 1;
						else
							break;
					}
					firstScan = false;
				}				
			}		
			return true;
		}
		
	}
