/** Given a list of equations, find the dependency list. 
	 * 
	 * For example
	 * a = b +4;
	 * b = 5;
	 * c = a + 2;
	 * ......
	 * x = a + c + 1;
	 * y = a + x;
	 *
	 * where the numbers (1, 2, ...) are considered as constants.
	 * When given a character, please output a list of its dependent characters.
	 * 
	 * If given x, output [ b, a, c, x]. 
	 * */
	 
public class EquationDependency{
	public EquationDependency(){}
	
	public void excute(){
//		String[] eqs = {"c=a+b","f=d+e","g=c+f"};
//		String[] eqs = {"a=5+b","c=a+4","g=a+c"};
		String[] eqs = {"g=c+d", "c=a+1","d=b+3"};
		List<String> L = dependency2( eqs, "g" );
		for( String s:L)
			System.out.print( s );
		System.out.println();		
	}
	
	public List<String> dependency0( String[] equations, String input ){//dynamic programming, space complexity O(n^2)
		//parse equations
		Map<String, List<String>> deps = new HashMap<String, List<String>>();
		for(String eq:equations ){
			int eqindex = eq.indexOf('=');
			List<String> values = new ArrayList<String>();
			int j = eqindex+1;
			while( j<eq.length() ){
				int plusindex = eq.indexOf('+',j);
				String s = eq.substring(j, -1==plusindex?eq.length():plusindex);
				j = (-1==plusindex?eq.length():plusindex)+1;
				int i = 0;//if string is numeric or not
				while(i<s.length() && (s.charAt(i)>='0' && s.charAt(i)<='9') )
					i++;
				if( i<s.length() ){//not numeric
					if( deps.containsKey( s ))
						values.addAll( deps.get(s) );
					values.add( s );
				}
			}
			Set<String> dups = new HashSet<String>();
			for(int i=0; i<values.size();){
				if( dups.add(values.get(i)) )
					i++;
				else
					values.remove(i);
			}			
			if( !values.isEmpty() ){
				String key = eq.substring(0, eqindex);
				values.add( key );
				deps.put( key, values);
			}
		}		
		return deps.get( input );
	}
	
	public List<String> dependency1( String[] equations, String input ){//dfs
		//parse equations
		Map<String, List<String>> deps = new HashMap<String, List<String>>();
		for(String eq:equations ){
			int eqindex = eq.indexOf('=');
			List<String> values = new ArrayList<String>();
			int j = eqindex+1;
			while( j<eq.length() ){
				int plusindex = eq.indexOf('+',j);
				String s = eq.substring(j, -1==plusindex?eq.length():plusindex);
				j = (-1==plusindex?eq.length():plusindex)+1;
				int i = 0;//if string is numeric or not
				while(i<s.length() && (s.charAt(i)>='0' && s.charAt(i)<='9') )
					i++;
				if( i<s.length() )//not numeric
					values.add( s );
			}
			if( !values.isEmpty() )
				deps.put( eq.substring(0, eqindex), values);
		}
		
		//retrieval
		List<String> res = new LinkedList<String>();
		Set<String> dups = new HashSet<String>();
		Stack<String> visiting = new Stack<String>();
		visiting.push( input );
		while(!visiting.isEmpty()){
			String key = visiting.peek();
			if( deps.containsKey(key) ){
				List<String> strs = deps.get( key );
				visiting.push( strs.get(0));
				if( 1==strs.size() )
					deps.remove(key);
				else{
					strs.remove(0);
					deps.put(key, strs);
				}				
			}else{
				if( dups.add( key) )
					res.add( key );
				visiting.pop();
			}			
		}		
		return res;
	}

	public List<String> dependency2( String[] equations, String input ){//topological sort
		//parse equations
		Map<String, List<String>> deps = new HashMap<String, List<String>>();//reverse of dependency
		Map<String, Integer> numChildren = new HashMap<String, Integer>();		
		for(String eq:equations ){
			int eqindex = eq.indexOf('=');
			String item = eq.substring(0, eqindex);
			int j = eqindex+1;
			while( j<eq.length() ){
				int plusindex = eq.indexOf('+',j);
				String s = eq.substring(j, -1==plusindex?eq.length():plusindex);
				j = (-1==plusindex?eq.length():plusindex)+1;
				int i = 0;//if string is numeric or not
				while(i<s.length() && (s.charAt(i)>='0' && s.charAt(i)<='9') )
					i++;
				if( i<s.length() ){//not numeric
					List<String> values = deps.containsKey(s)?deps.get( s ):(new ArrayList<String>());
					values.add( item );
					deps.put(s, values);
					numChildren.put(item, numChildren.containsKey(item)?numChildren.get(item)+1:1 );
				}
			}
		}
		
		Queue<String> sinkVetex = new LinkedList<String>();//find sink vetices
		for(String v:deps.keySet() ){
			if( !numChildren.containsKey( v ) )
				sinkVetex.offer( v );
		}
		
		//retrieval
		List<String> res = new LinkedList<String>();
		while( !sinkVetex.isEmpty() ){
			String item = sinkVetex.poll();
			res.add( item );
			if( deps.containsKey(item )){
				for(String v:deps.get(item) ){
					numChildren.put(v, numChildren.get( v )-1);
					if( 0==numChildren.get(v) )
						sinkVetex.offer( v );
				}
			}
			deps.remove( item );
		}				
		return res;
	}
}
