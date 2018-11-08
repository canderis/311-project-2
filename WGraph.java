import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WGraph {
	private class Tuple<X, Y> { 
		public final X x; 
		public final Y y; 
		public Tuple(X x, Y y) { 
		    this.x = x; 
		    this.y = y; 
		} 
	} 
	
    private class Vertex{
    	private int x;
    	private int y;
    	private ArrayList<Edge> edges;
    	
        Vertex(int x, int y){
        	this.x = x;
        	this.y = y;
        	this.edges = new ArrayList<Edge>();
        }
        
        public ArrayList<Edge> getEdges(){
        	return this.edges;
        }
        
        public void addEdge(Edge e){
        	this.edges.add(e);
        }
        public void removeEdge(Edge e) {
        	this.edges.remove(e);
        }
        
    }
    private class Edge{
    	Vertex dst;
    	int weight;
    	Edge(Vertex dst, int weight){
    		this.dst = dst;
    		this.weight = weight;
    	}
    }

    HashMap<String, Vertex> lookup;
    int vertCount;
    Vertex[] list;

    WGraph(String FName) throws Exception{
    	Scanner scanner = new Scanner(new File(FName));
    	
    	//Verts
    	int vertCount = scanner.nextInt();
    	this.vertCount = vertCount;

    	//Edges
    	int edgeCount = scanner.nextInt();

    	lookup = new HashMap<String, Vertex>(vertCount);
    	
    	
    	
    	while(scanner.hasNextInt())
    	{
    		int sx = scanner.nextInt();
    		int sy = scanner.nextInt();
    		int dx = scanner.nextInt();
    		int dy = scanner.nextInt();
    		int weight = scanner.nextInt();
    		
    		
    		String srcKey = "" + sx + sy;
    		Vertex src = lookup.get(srcKey);
    		if(src == null) {
    			src = new Vertex(sx, sy);
    			lookup.put(srcKey, src);
    		}
    		
    		String dstKey = "" + dx + dy;
    		Vertex dst = lookup.get(dstKey);
    		
    		if(dst == null) {
    			dst = new Vertex(dx, dy);
    			lookup.put(dstKey, dst);
    		}
    		
    		src.addEdge(new Edge(dst, weight));
    		
    	}

    	scanner.close();
    }

    // The pre/post-conditions describes the structure of the
    // input/ouput. The semantics of these structures depend on
    // defintion of the corresponding method.
    // pre:  ux, uy, vx, vy are valid coordinates of vertices u and v
    //       in the graph
    // post: return arraylist contains even number of integers,
    //       for any even i,
    //       i-th and i+1-th integers in the array represent
    //       the x-coordinate and y-coordinate of the i/2-th vertex
    //       in the returned path (path is an ordered sequence of vertices)
    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
    	Vertex src = this.lookup.get(""+ux+uy);
    	Vertex dst = this.lookup.get(""+vx+vy);

    	return buildOutput(dijkstra(src, dst));
    }
    
    


    // pre:  ux, uy are valid coordinates of vertex u from the graph
    //       S represents a set of vertices.
    //       The S arraylist contains even number of intergers2
    //       for any even i,
    //       i-th and i+1-th integers in the array represent
    //       the x-coordinate and y-coordinate of the i/2-th vertex
    //       in the set S.
    // post: same structure as the last method’s post.
    public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
    	Vertex src = this.lookup.get(""+ux+uy);
    	
    	HashMap<Vertex, Edge> dummyEdges = new HashMap<Vertex, Edge>(S.size()/2);
    	Vertex s = new Vertex(-1, -1);
    	this.lookup.put("-1-1", s);
    	for(int i = 0; i < S.size(); i+=2) {    		
    		Edge e = new Edge(s, 0 );
    		Vertex v = this.lookup.get(""+S.get(i)+S.get(i+1));
    		dummyEdges.put(v, e);
    		v.addEdge(e);
    	}
    	
    	ArrayList<Vertex> path = dijkstra(src, s);
    	path.remove(path.size()-1);
    	
    	this.lookup.remove("-1-1");
    	
    	for(Vertex v : dummyEdges.keySet()) {
    		v.removeEdge(dummyEdges.get(v));
    	}

    	
    	return buildOutput(path);
    }




    // pre:  S1 and S2 represent sets of vertices (see above for
    //       the representation of a set of vertices as arrayList)
    // post: same structure as the last method’s post.
    public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
    	//Vertex src = this.lookup.get(""+ux+uy);
    	
    	Vertex s1 = new Vertex(-1, -1);
    	this.lookup.put("-1-1", s1);
    	for(int i = 0; i < S1.size(); i+=2) {
    		Edge e = new Edge(this.lookup.get(""+S1.get(i)+S1.get(i+1)), 0 );
    		s1.addEdge(e);
    	}
    	
    	Vertex s2 = new Vertex(-2, -2);
    	HashMap<Vertex, Edge> dummyEdges = new HashMap<Vertex, Edge>(S2.size()/2);
    	this.lookup.put("-2-2", s2);
    	for(int i = 0; i < S2.size(); i+=2) {
    		Edge e = new Edge(s2, 0 );
    		Vertex v = this.lookup.get(""+S2.get(i)+S2.get(i+1));
    		dummyEdges.put(v, e);
    		v.addEdge(e);
    	}
    	
    	ArrayList<Vertex> path = dijkstra(s1, s2);
    	
    	path.remove(path.size()-1);
    	path.remove(0);
    	
    	this.lookup.remove("-1-1");
    	this.lookup.remove("-2-2");
    	
    	for(Vertex v : dummyEdges.keySet()) {
    		v.removeEdge(dummyEdges.get(v));
    	}
    	
    	
    	return buildOutput(path);
    }
    
    public String toString() {
    	String s = "";
    	for(String key : this.lookup.keySet()) {
    		s += key;
    		for(Edge e: this.lookup.get(key).edges) {
    			s += "\t" + e.dst.x + e.dst.y;
    		}
    		s+= "\n";
    	}
    	
    	return s;
    }
    
    

    private ArrayList<Integer> buildOutput(ArrayList<Vertex> path){
    	ArrayList<Integer> output = new ArrayList<Integer>(path.size() * 2);
    	
    	for(Vertex v: path) {
    		output.add(v.x);
    		output.add(v.y);
    	}
    	
    	return output;
    }
    
    private ArrayList<Vertex> dijkstra(Vertex src, Vertex dst) {
    	HashMap<Vertex, Integer> dist = new HashMap<Vertex, Integer>(this.vertCount);
    	HashMap<Vertex, Vertex> parent = new HashMap<Vertex, Vertex>(this.vertCount);
    	
    	PriorityQueue<Tuple<Vertex, Integer>> q
    		= new PriorityQueue<Tuple<Vertex, Integer>>
    			(
    				this.vertCount,
    				(a,b) -> a.y.compareTo(b.y)
    			);
    	
    	//algo begin
    	dist.put(src, 0);
    	parent.put(null, src);
    	
    	q.add(new Tuple<Vertex, Integer>(src, 0));
    	
    	for (Vertex value : this.lookup.values()) {
    	    if(value != src) {
    	    	dist.put(value, Integer.MAX_VALUE);
    	    }
    	}
    	

    	
    	while(q.peek() != null) {
    		Tuple<Vertex, Integer> t = q.poll();
    		Vertex u = t.x;
    		ArrayList<Edge> edges = u.getEdges();
    		
    		for( Edge e: edges) {
    			Vertex v = e.dst;
    			if(dist.get(v) > dist.get(u) + e.weight) {
    				dist.replace(v, dist.get(u) + e.weight);
    				parent.put(u, v);
    				q.add(new Tuple<Vertex, Integer>(v, dist.get(v)));
    			}
    		}
    	}
    	

    	Vertex next = null;
    	ArrayList<Vertex> path = new ArrayList<Vertex>();
    	do {
    		next = parent.get(next);
    		path.add(next);
    		if(next == null) {
    			return new ArrayList<Vertex>();
    		}
    	}while(next != dst);
    	
    	return path;
    	
    }

}


