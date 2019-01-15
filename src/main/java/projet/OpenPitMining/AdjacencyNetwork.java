package projet.OpenPitMining;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdjacencyNetwork<Vertex,Edge> implements WeightedDiGraph<Vertex,Edge> {
	
	protected Set<Vertex> vertices = new HashSet<Vertex>();
	protected Map<Vertex, List<Edge>> vertexToEdges = new HashMap<Vertex, List<Edge>>();
	protected Map<Edge, Vertex> edgeToSrc = new HashMap<Edge, Vertex>();
	protected Map<Edge, Vertex> edgeToDest = new HashMap<Edge, Vertex>();
	protected Map<Edge,Integer> edges = new HashMap<Edge,Integer>();

	public void addVertex(Vertex v) {
		// TODO Auto-generated method stub
		
	}

	public List<Vertex> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEdge(Edge e, Vertex src, Vertex dest, Integer weight) {
		// TODO Auto-generated method stub
		//edges.put(e,weight);
		
	}

	public int getWeight(Edge e) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Vertex> getAdjacentVertices(Vertex v) {
		// TODO Auto-generated method stub
		return null;
	}

	public void nameVertex(String name, Vertex v) {
		// TODO Auto-generated method stub
		
	}

	public Vertex getVertexByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNameOrNullByVertex(Vertex v) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean areConnected(Vertex src, Vertex dest) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean areConnected(String src, String dest) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Vertex> shortestPath(Vertex src, Vertex dest) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Vertex> shortestPath(String src, String dest) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Collection<Vertex>> PathAndVisited(Vertex src, Vertex dest) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Collection<Vertex>> PathAndVisited(String src, String dest) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
