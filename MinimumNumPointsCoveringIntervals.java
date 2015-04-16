/*activities: 
{
    1,5
    2,4
    3,10
    5,6
}
where for each row, the first number represents the starting time and the second one represents the ending time.

Require to return one possible combination of select points, which guarantees all activities are visited, e.g.,
{
    4, 5
}
such that the first 3 activity can be check at time 4, and last one at time 5
*/

public class MinimumCheckPointsCoveringAllIntervals{
    MinimumCheckPointsCoveringAllIntervals(){}
    
    public void excute(){
        int[] A = {1,5,5,6,3,10,6,7,4,5,2,4};
        List<Interval> list = new ArrayList<Interval>();
        for( int i=0;i<A.length;i++)
            list.add( new Interval(A[i], A[++i]) );
        
        List<Integer> res = minCheckingPoints( list );
        for(int x:res )
            System.out.print(x+", ");
        System.out.println();
        
    }
    
    public class Interval {
        int start;
        int end;
        Interval( Interval i) { start = i.start; end = i.end; }
        Interval(int s, int e) { start = s; end = e; }
    }
    
    private List<Integer> minCheckingPoints( List<Interval> list ){
        List<Integer> res = new ArrayList<Integer>();
        if( null==list || 0==list.size() )
            return res;
        
        Comparator<Interval> comp = new Comparator<Interval>(){
            public int compare(Interval i1, Interval i2){
                if( i1.start==i2.start )
                    return i1.end-i2.end;
                else
                    return i1.start-i2.start;
            }
        };
        
        Collections.sort(list, comp);
        
        Interval keep = new Interval( list.get(0) );
        for( int i=1; i<=list.size();i++){
            if( i==list.size() || list.get(i).start > keep.end ){
                res.add(keep.start);
                if( i<list.size() ){
                    keep = list.get(i);
                }
            }else{
                keep.start = Math.max( keep.start, list.get(i).start );
                keep.end = Math.min( keep.end, list.get(i).end );
            }
        }
        return res;
    }
}
