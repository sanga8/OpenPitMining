package projet.OpenPitMining;
import java.util.Collection;
import java.util.List;

public interface DiGraph<Vertex, Edge> {
	public void addVertex(Vertex v);

	public List<Vertex> getVertices();

	public void addEdge(Edge e, Vertex src, Vertex dest);

	public List<Edge> getEdges();

	public List<Vertex> getAdjacentVertices(Vertex v);

	public void nameVertex(String name, Vertex v);

	public Vertex getVertexByName(String name);

	public String getNameOrNullByVertex(Vertex v);

	public List<String> getNames();

	public boolean areConnected(Vertex src, Vertex dest);

	public boolean areConnected(String src, String dest);

	public List<Vertex> shortestPath(Vertex src, Vertex dest);

	public List<Vertex> shortestPath(String src, String dest);
	
	public List<Collection<Vertex>> PathAndVisited(Vertex src, Vertex dest);

	public List<Collection<Vertex>> PathAndVisited(String src, String dest);
}
