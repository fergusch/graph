
/**
 * Represents an edge between two vertices in a Graph.
 * @author Christian Ferguson
 * @see Graph
 * @see Vertex
 * @see Path
 */
public class Edge {
	
	/**
	 * The String value of the Vertex the edge points to.
	 */
	private String endVertex;
	
	/**
	 * The weight of the edge.
	 */
	private int weight;
	
	/**
	 * Constructs an Edge object.
	 * @param endVertex - The String value of the Vertex the edge points to.
	 * @param weight - The weight of the edge.
	 */
	public Edge(String endVertex, int weight) {
		this.endVertex = endVertex;
		this.weight = weight;
	}
	
	/**
	 * @return String value of the Vertex the edge points to.
	 */
	public String getEndVertex() {
		return this.endVertex;
	}
	
	/**
	 * @return The weight of the edge.
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Sets the weight of an edge.
	 * @param w - The new weight to set.
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
