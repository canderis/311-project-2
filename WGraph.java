import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WGraph {
    private class Vertex{
    	private int x;
    	private int y;
    	private ArrayList<Edge> edges;
    	
        Vertex(int x, int y){
        	this.x = x;
        	this.y = y;
        	this.edges = new ArrayList<Edge>();
        }
        
        public void addEdge(Edge e){
        	this.edges.add(e);
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
    Vertex[] list;

    WGraph(String FName) throws Exception{
    	Scanner scanner = new Scanner(new File(FName));
    	
    	//Verts
    	int vertCount = scanner.nextInt();

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
    		
    		System.out.println(sx + " " + sy + " " + dx + " " + dy + " " + weight);
    		
    		String srcKey = "" + sx + sy;
    		Vertex src = lookup.get(srcKey);
    		if(src == null) {
    			src = new Vertex(sx, sy);
    			lookup.put(srcKey, src);
    		}
    		
    		String dstKey = "" + dx + dy;
    		Vertex dst = lookup.get(dstKey);
    		
    		if(dst == null) {
    			dst = new Vertex(sx, sy);
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
    ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
    	
    	return new ArrayList<Integer>();
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
    	return new ArrayList<Integer>();
    }




    // pre:  S1 and S2 represent sets of vertices (see above for
    //       the representation of a set of vertices as arrayList)
    // post: same structure as the last method’s post.
    ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
    	return new ArrayList<Integer>();
    }
}
