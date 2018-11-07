import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WGraph {
	public class Tuple<X, Y> { 
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
    	private boolean dst = false;
    	
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
        
        public boolean isDst(){
        	return dst;
        }
        public void setDst() {
        	this.dst = true;
        }
        public void resetDst() {
        	this.dst = false;
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
    		
    		//System.out.println(sx + " " + sy + " " + dx + " " + dy + " " + weight);
    		
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
//    	for (String key : lookup.keySet()) {
//    	    System.out.println(""+key + " - " + lookup.get(key).x + " " + lookup.get(key).y);
//    	}
    	
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
    ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
    	Vertex src = this.lookup.get(""+ux+uy);
    	Vertex dst = this.lookup.get(""+vx+vy);
    	dst.setDst();
    	
    	//do dijkstra's
    	dijkstra(src);
    	
    	dst.resetDst();
    	return new ArrayList<Integer>();
    }
    
    private void dijkstra(Vertex src) {
//    	1) Initialize distances of all vertices as infinite.
//
//    2) Create an empty priority_queue pq.  Every item
//       of pq is a pair (weight, vertex). Weight (or 
//       distance) is used used as first item  of pair
//       as first item is by default used to compare
//       two pairs
//
//    3) Insert source vertex into pq and make its
//       distance as 0.
//
//    4) While either pq doesn't become empty
//        a) Extract minimum distance vertex from pq. 
//           Let the extracted vertex be u.
//        b) Loop through all adjacent of u and do 
//           following for every vertex v.
//
//               // If there is a shorter path to v
//               // through u. 
//               If dist[v] > dist[u] + weight(u, v)
//
//                   (i) Update distance of v, i.e., do
//                         dist[v] = dist[u] + weight(u, v)
//                   (ii) Insert v into the pq (Even if v is
//                        already there)
//                   
//    5) Print distance array dist[] to print all shortest
//       paths. 
    	
    	
    	HashMap<Vertex, Integer> dist = new HashMap<Vertex, Integer>(this.vertCount);
    	PriorityQueue<Tuple<Vertex, Integer>> q
    		= new PriorityQueue<Tuple<Vertex, Integer>>
    			(
    				this.vertCount,
    				(a,b) -> a.y.compareTo(b.y)
    			);
    	
    	//algo begin
    	dist.put(src, 0);
    	q.add(new Tuple<Vertex, Integer>(src, 0));
    	
    	for (Vertex value : this.lookup.values()) {
    	    if(value != src) {
    	    	dist.put(value, Integer.MAX_VALUE);
    	    }
//    	    System.out.println("" + value.x + value.y);
    	}
    	
//    	for (Vertex key : dist.keySet()) {
//    	    System.out.println(""+key.x + key.y + " - " + dist.get(key));
//    	}
    	
    	while(q.peek() != null) {
    		Tuple<Vertex, Integer> t = q.poll();
    		Vertex u = t.x;
    		ArrayList<Edge> edges = u.getEdges();
    		
    		for( Edge e: edges) {
    			Vertex v = e.dst;
    			if(dist.get(v) > dist.get(u) + e.weight) {
    				dist.replace(v, dist.get(u) + e.weight);
    				q.add(new Tuple<Vertex, Integer>(v, dist.get(v)));
    			}
    		}
    	}
    	
    	for (Vertex key : dist.keySet()) {
    	    System.out.println(""+key.x + key.y + " - " + dist.get(key));
    	}
    	
    }


    // pre:  ux, uy are valid coordinates of vertex u from the graph
    //       S represents a set of vertices.
    //       The S arraylist contains even number of intergers2
    //       for any even i,
    //       i-th and i+1-th integers in the array represent
    //       the x-coordinate and y-coordinate of the i/2-th vertex
    //       in the set S.
    // post: same structure as the last method’s post.
    ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
    	Vertex src = this.lookup.get(""+ux+uy);
    	
    	for(int i = 0; i < S.size(); i++) {
    		this.lookup.get(""+S.get(i)+S.get(i+1)).setDst();
    	}
    	
    	//do dijkstra's
    	
    	
    	
    	for(int i = 0; i < S.size(); i++) {
    		this.lookup.get(""+S.get(i)+S.get(i+1)).resetDst();
    	}
    	return new ArrayList<Integer>();
    }




    // pre:  S1 and S2 represent sets of vertices (see above for
    //       the representation of a set of vertices as arrayList)
    // post: same structure as the last method’s post.
    ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
    	return new ArrayList<Integer>();
    }
    
    
//    
//    
//    
//    /**
//     * A priority queue.
//     *
//     * @author Scott Huffman
//     * @author John Jago
//     */
//    public class PriorityQ<T> {
//    	public class PriorityVal<T> {
//
//    	    private String str;
//    	    private int priority;
//
//    	    public PriorityVal(String str, int priority) {
//    	        this.str = str;
//    	        this.priority = priority;
//    	    }
//
//    	    @Override
//    	    public boolean equals(Object o) {
//    	        if (this == o)
//    	            return true;
//
//    	        if (o == null)
//    	            return false;
//
//    	        if (getClass() != o.getClass())
//    	            return false;
//
//    	        PriorityVal<T> other = (PriorityVal<T>) o;
//    	        return this.str.equals(other.str) && this.priority == other.priority;
//    	    }
//
//    	    public String getStr() {
//    	        return str;
//    	    }
//
//    	    public void setStr(String str) {
//    	        this.str = str;
//    	    }
//
//    	    public int getPriority() {
//    	        return priority;
//    	    }
//
//    	    public void setPriority(int priority) {
//    	        this.priority = priority;
//    	    }
//    	}
//
//        private PriorityVal[] data;
//        private int size;
//
//        public PriorityQ() {
//            data = new PriorityVal[100];
//            size = 0;
//        }
//
//        public void add(String s, int p) {
//        	PriorityVal newString = new PriorityVal(s, p);
//            data[size] = newString;
//            size++;
//
//            if (size > data.length - 1) {
//            	PriorityVal[] newData = new PriorityVal[data.length * 2];
//                System.arraycopy(data, 0, newData, 0, data.length);
//                data = newData;
//            }
//
//            int i = size - 1;
//            while (i != 0 && newString.getPriority() > data[(i - 1) / 2].getPriority()) {
//                data[i] = data[(i - 1) / 2];
//                i = (i - 1) / 2;
//            }
//
//            data[i] = newString;
//        }
//
//        public PriorityVal returnMax() {
//            return data[0];
//        }
//
//        public PriorityVal extractMax() {
//        	PriorityVal max = returnMax();
//            data[0] = data[size - 1];
//            size--;
//            percolateDown(data, size, 0);
//
//            return max;
//        }
//
//        public void remove(int i) {
//            swap(i, size - 1);
//            size--;
//            percolateDown(data, size, i);
//        }
//
//        public void decrementPriority(int i, int k) {
//            data[i].setPriority(data[i].getPriority() - k);
//            percolateDown(data, size, i);
//        }
//
//        public int[] priorityArray() {
//            int[] temp = new int[size];
//
//            for (int i = 0; i < size; i++) {
//                temp[i] = data[i].getPriority();
//            }
//
//            return temp;
//        }
//
//        public int getKey(int i) {
//            return data[i].getPriority();
//        }
//
//        public String getValue(int i) {
//            return data[i].getStr();
//        }
//
//        public boolean isEmpty() {
//            return size == 0;
//        }
//
//        public PriorityVal[] getData() {
//            return data;
//        }
//
//        public int getSize() {
//            return size;
//        }
//
//        private void percolateDown(PriorityVal[] a, int size, int i) {
//            int left = (2 * i) + 1;
//            int right = (2 * i) + 2;
//
//            int largest = i;
//
//            if (left < size && a[left].getPriority() > a[largest].getPriority()) {
//                largest = left;
//            }
//
//            if (right < size && a[right].getPriority() > a[largest].getPriority()) {
//                largest = right;
//            }
//
//            if (largest != i) {
//                swap(largest, i);
//                percolateDown(a, size, largest);
//            }
//        }
//
//        private void swap(int a, int b) {
//        	PriorityVal temp = data[a];
//            data[a] = data[b];
//            data[b] = temp;
//        }
//    }

}


