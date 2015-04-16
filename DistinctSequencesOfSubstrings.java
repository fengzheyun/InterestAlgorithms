/*
Given a string S and a string T, count the number of distinct sequences of substrings of T in S.

T is formed by a set of substrings, and each substring is constituted by repeating a single letter for 2 or 4 times. 
If a letter 'a' is repeated for twice, the substring is written as "a+"; if it is repeated for four times, its 
consequent substring is "a-"
(ie, "a+c+e-" is a subsequence of substrings of "aabbaacceeee" while "a+e-c+" and "a-c+e-" are not).

In T, if a letter is followed by '+' or '-', this letter will repeat for 2 or 4 times, respectively, and if it is
followed by neither '+' nor '-', then this letter won't repeat, e.g., "ab+c-" = "abbcccc".

Here is an example:
S = "waeginsapnaabangpisebbasepgnccccapisdnfngaabndlrjngeuiogbbegbuoecccc", T = "a+b+c-"

Return 4. 
*/
/*
0. 每个substring，例如a+用a2表示，a-用a4表示，单独a用a1表示，在S中如果a出现5次，那个串就用a5表示。
1. 重组T, 同时用hashtable记录每个字母出现的最小次数，比如T=aa+b+c+c-, 则hashtable中为(a,1)(b,2)(c,2)
2. 重组S，只保留在hashtable包含的字母且该字母重复次数大于等于它在hashtable中对应的值。
    比如S=akaasbbhccccdagaabbcccc, 那么当T=a+b+c-的时候，S重组后为a2b2c4a2b2c4；如果T=aa+b+c-，则S重组后为a1a2b2c4a1a2b2c4.
3. 之后就和distinct sequences差不多了，唯一的区别就是现在是以字母+数字为一个单位，并且要处理字母后面的那个值的关系，用xc代表x中的字母，xn代表x中的数字
4. 如果sc(i)==tc(j)且sn(i)>=tn(j), 则dp(i,j) = dp(i-1,j-1)*(sni-tnj+1)+dp(i-1,j)，否则dp(i,j) = dp(i-1,j)
*/

public class DistinctSequencesOfSubstrings{
	    DistinctSequencesOfSubstrings(){}
	    
	    public void excute(){
	        String S = "waeginsapnaabangpisebbasepgnccccapisdnfngaabndlrjngeuiogbbegbuoecccc";
	        String T = "aa+b+c-";
	        System.out.println( distinctSequencesOfSubstrings(S,T) );
	    }
	    
	    public class RepeatedChar{
	        char ch;
	        int t;//repeated times
	        
	        RepeatedChar( char _ch, int _t){
	            ch = _ch;
	            t = _t;
	        }
	    }
	    
	    //DP: Time O(nS+kS*kT), Space: O(kS). nS is the length of S, kX is the number of substrings in X, X is S or T.
	    private int distinctSequencesOfSubstrings( String s, String t){
	        if( null==t || 0==t.length() )
	            return -1;
	        if( null==s || 0==s.length() )
	            return 0;
	            
	        List<RepeatedChar> S = reformS(s,t);
	        List<RepeatedChar> T = reformT(t);
	        
	        int[] dp = new int[T.size()];
	        if( S.get(0).ch==T.get(0).ch && S.get(0).t>=T.get(0).t )
	        	dp[0] = 1+S.get(0).t-T.get(0).t;
	        
	        for(int i=1; i<S.size();i++){
	            int dp_pre = dp[0];
	            if( S.get(i).ch==T.get(0).ch && S.get(i).t>=T.get(0).t )
		        	dp[0] += 1+S.get(i).t-T.get(0).t;
	            for( int j=1; j<T.size();j++){
	                if( S.get(i).ch==T.get(j).ch && S.get(i).t>=S.get(j).t ){
	                    int temp = dp[j];
	                    dp[j] = dp_pre*(S.get(i).t+1-T.get(j).t)+dp[j];
	                    dp_pre = temp;
	                }else
	                    dp_pre = dp[j];
	            }
	        }
	        return dp[T.size()-1];
	    }
	    
	    private List<RepeatedChar> reformT( String t){
	        List<RepeatedChar> str = new ArrayList<RepeatedChar>();
	        for(int i=0;i<t.length();i++){
	            char tch = t.charAt(i);
	            int value = 1;
	            if( i+1<t.length() && (t.charAt(i+1)=='-'||t.charAt(i+1)=='+') ){
	                value = t.charAt(++i)=='-'?4:2;
	            }
	            str.add(new RepeatedChar(tch, value));
	        }
	        return str;
	    }
	    
	    private List<RepeatedChar> reformS( String s, String t){
	        Map<Character,Integer> map = new HashMap<Character,Integer>();
	        for(int i=0;i<t.length();i++){
	            char tch = t.charAt(i);
	            int value = 1;
	            if( i+1<t.length() && (t.charAt(i+1)=='-'||t.charAt(i+1)=='+') ){
	                value = t.charAt(++i)=='-'?4:2;
	            }
	            map.put( tch, map.containsKey(tch)?Math.min(value,map.get(tch)):value );
	        }
	        
	        List<RepeatedChar> str = new ArrayList<RepeatedChar>();
	        int k = 0;
	        while(k<s.length()){
	            char sch = s.charAt(k++);
	            if( map.containsKey( sch ) ){
	                int i = k;
	                while(i<s.length()&& sch==s.charAt(i))
	                    i++;
	                if( i-k+1 >= map.get(sch ) ){
	                    str.add(new RepeatedChar(sch,(i-k+1)));
	                } 
	                k = i;
	            }
	        }
	        return str;
	    }
	}
