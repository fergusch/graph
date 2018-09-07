import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Represents a three-column table which stores the shortest distance from the source to a given vertex
 * and the previous vertex in the shortest path to the given vertex.
 * @author Christian Ferguson
 * @see Graph
 * @see Vertex
 * @see Edge
 * @see Path
 */
class DijkstraTable {
	
	private HashMap<String, Integer> distTable;
	private HashMap<String, String> prevTable;
	
	public DijkstraTable(HashMap<String, Integer> distTable, HashMap<String, String> prevTable) {
		this.distTable = distTable;
		this.prevTable = prevTable;
	}
	
	public String getSource() {
		return distTable.keySet().iterator().next();
	}
	
	public int getDistanceTo(String v) {
		return distTable.get(v);
	}
	
	public String getPreviousVertex(String v) {
		return prevTable.get(v);
	}
	
}

/**
 * Represents a Graph structure.
 * @author Christian Ferguson
 * @see Vertex
 * @see Edge
 * @see Path
 */
public class Graph {
	
	/**
	 * Boolean value of whether or not this graph is directed.
	 */
	private boolean directed;
	
	/**
	 * Boolean value of whether or not this graph is weighted.
	 */
	private boolean weighted;
	
	/**
	 * A collection of vertices in a {@link Graph}.
	 */
	private ArrayList<Vertex> vertices = new ArrayList<>();
	
	/**
	 * Constructs a Graph object.
	 * @param directed - Boolean value of whether or not this graph is directed.
	 * @param weighted - Boolean value of whether or not this graph is weighted.
	 */
	public Graph(boolean directed, boolean weighted) {
		this.directed = directed;
		this.weighted = weighted;
	}
	
	/**
	 * @return Boolean value of whether or not this graph is directed.
	 */
	public boolean isDirected() {
		return directed;
	}
	
	/**
	 * @return Boolean value of whether or not this graph is weighted.
	 */
	public boolean isWeighted() {
		return weighted;
	}
	
	/**
	 * Adds a vertex to a Graph.
	 * @param v - Value of vertex to add.
	 */
	public void addVertex(String v) {
		if (!hasVertex(v)) {
			vertices.add(new Vertex(v));
		}
	}
	
	/**
	 * @return A collection of vertices in a Graph.
	 */
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	/**
	 * Creates a set of vertices based on an array of String values.
	 * @param vertices - String[] representing vertices.
	 */
	public void setVertices(String[] vertices) {
		this.vertices = new ArrayList<Vertex>();
		for (String v : vertices) {
			addVertex(v);
		}
	}
	
	/**
	 * Finds the given vertex.
	 * @param value - value of the requested Vertex
	 * @return Vertex with the given value
	 */
	public Vertex getVertex(String value) {
		
		for (Vertex v : vertices) {
			if (v.getValue().equals(value)) {
				return v;
			}
		}
		
		return null;
	}
	
	/**
	 * @param value - String value of requested Vertex
	 * @return Whether or not the given vertex exists within a Graph.
	 */
	public boolean hasVertex(String value) {
		
		Vertex v = getVertex(value);
		
		if (v != null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param v1 - Starting vertex value
	 * @param v2 - Ending vertex value
	 * @return Whether or not an edge exists between the given vertices.
	 */
	public boolean areNeighbors(String v1, String v2) {
		
		Vertex vert1 = getVertex(v1);
		Vertex vert2 = getVertex(v2);
		
		for (Edge e : vert1.getEdges()) {
			if (e.getEndVertex().equals(vert2.getValue())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Creates an edge between two vertices.
	 * @param v1 - value of first vertex.
	 * @param v2 - value of second vertex.
	 * @param weight - weight of the new edge.
	 */
	public void setEdge(String v1, String v2, int weight) {
		
		// If the graph is weighted, force the weight to be 1.
		if (!weighted) {
			weight = 1;
		}
		
		// If the two vertices are already connected, only change the edge weight.
		if (areNeighbors(v1, v2)) {
			
			getVertex(v1).getEdge(v2).setWeight(weight);
			
			// Only set the weight of the second edge if this graph is not directed.
			if (!directed) {
				getVertex(v2).getEdge(v1).setWeight(weight);
			}
			
		} else {
			
			// Create a new edge between the given vertices.
			
			getVertex(v1).addEdge(new Edge(v2, weight));
			
			// Only create the second edge if this graph is not directed.
			if (!directed) {
				getVertex(v2).addEdge(new Edge(v1, weight));
			}
			
		}
		
	}
	
	/**
	 * Creates an un-weighted edge between the two vertices.
	 * @param v1 - String value of first vertex
	 * @param v2 - String value of second vertex
	 */
	public void setEdge(String v1, String v2) {
		setEdge(v1, v2, 1);
	}
	
	/**
	 * Finds the weight of the edge between two vertices.
	 * @param v1 - String value of first vertex
	 * @param v2 - String value of second vertex
	 * @return Weight of the edge between the two vertices if it exists. If there is no edge, returns 0.
	 */
	public int getWeightOfEdge(String v1, String v2) {
		
		if (areNeighbors(v1, v2)) {
			return getVertex(v1).getEdge(v2).getWeight();
		}
		
		return 0;
	}
	
	/**
	 * Implements Dijkstra's algorithm to find the shortest path between the given source and all other vertices.
	 * @param v1 - String value of starting vertex
	 * @return DijkstraTable object containing resulting data
	 * @see DijkstraTable
	 */
	public DijkstraTable dijkstra(String v1) {
		
		// Table of each vertex's distance from v1
		HashMap<String, Integer> distTable = new HashMap<>();
		
		// Table of the previous vertex on the shortest path of each vertex
		HashMap<String, String> prevTable = new HashMap<>();
		
		// Queue of vertices to visit
		LinkedList<String> toVisit = new LinkedList<>();
		
		// Construct queue and tables
		for (Vertex v : vertices) {
			distTable.put(v.getValue(), Integer.MAX_VALUE); // Set each distance to infinity
			prevTable.put(v.getValue(), null);				// Set each prev to null
			toVisit.add(v.getValue());						// Add vertex to queue
		}
		
		// Set source vertex dist to 0 and prev to itself
		distTable.put(v1, 0);
		prevTable.put(v1, v1);
		
		while (!toVisit.isEmpty()) {
			
			// Find vertex with shortest distance
			String u1 = null;
		    int minDist = Integer.MAX_VALUE;
		    for(String v : distTable.keySet()) {
		    	if (toVisit.contains(v)) {
		    		int value = distTable.get(v);
			        if(value < minDist) {
			            minDist = value;
			            u1 = v;
			        }
		    	}
		    }
		    Vertex u = getVertex(u1);
		    
		    toVisit.remove(u1);	// Dequeue vertex
		    
		    // Iterate through neighbors
		    for (String v : u.getNeighbors()) {
		    	
		    	// Calculate distance to v from u
		    	int dist = distTable.get(u.getValue()) + getWeightOfEdge(u.getValue(), v);
		    	
		    	// Update distance and prev tables
		    	if (dist < distTable.get(v)) {
		    		distTable.put(v, dist);
		    		prevTable.put(v, u.getValue());
		    	}
		    }
			
		}
		
		return new DijkstraTable(distTable, prevTable);
	}
	
	/**
	 * Implements Dijkstra's algorithm to find the shortest path between two vertices.
	 * @param v1 - String value of starting vertex
	 * @param v2 - String value of ending vertex
	 * @return Possibly-null {@link Path}  object containing the shortest path between the given vertices. If no path exists, returns null.
	 */
	public Path getShortestPathBetween(String v1, String v2) {
		
		// Table containing paths calculated using Dijkstra's algorithm
		DijkstraTable dt = dijkstra(v1);
		
		// Collection of vertices in the shortest path
		// Will be used to build a Path object
		ArrayList<Vertex> path = new ArrayList<>();
		
		// Use the Dijkstra table to go backwards and track a path
		// from the requested vertex to the source
		String current = v2;
		while (!current.equals(v1)) {
			path.add(getVertex(current));
			current = dt.getPreviousVertex(current);
		}
		
		// Add the source to the path
		path.add(getVertex(v1));
		
		// Reverse the path so it starts at the source
		Collections.reverse(path);
		
		return new Path(path);
	}
	
}
