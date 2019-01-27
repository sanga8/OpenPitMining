package projet.OpenPitMining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AdjacencyNetwork<Vertex,Edge> {
	
	protected Set<Vertex> vertices = new HashSet<Vertex>();
	protected Map<Vertex,Integer> profit = new HashMap<Vertex,Integer>();
	protected Map<Vertex, List<Edge>> vertexToEdges = new HashMap<Vertex, List<Edge>>();
	protected Map<Edge, Vertex> edgeToSrc = new HashMap<Edge, Vertex>();
	protected Map<Edge, Vertex> edgeToDest = new HashMap<Edge, Vertex>();
	protected Set<Edge> edges = new HashSet<Edge>();

	public void addVertex(Vertex v) {
		if (!vertices.contains(v)) {
			vertices.add(v);
			vertexToEdges.put(v, new ArrayList<Edge>());
		}
	}
	
	public void addProfit(Vertex v,Integer p) {	
			profit.put(v,p);

	}

	
	public Integer getProfit(Vertex v) {
		return profit.get(v);
	}

	public void setProfit(Map<Vertex, Integer> profit) {
		this.profit = profit;
	}

	public List<Vertex> getVertices() {
		
		return new ArrayList<Vertex>(vertices);
	}

	public void addEdge(Edge e, Vertex src, Vertex dest) {
		addVertex(src);
		if (dest != null) {
			addVertex(dest);
			edges.add(e);
			edgeToSrc.put(e, src);
			edgeToDest.put(e, dest);
			vertexToEdges.get(src).add(e);
		}
	}
	
	public void addEdgeMf(Edge e, Vertex src, Vertex dest) {
		addVertex(src);
		addVertex(dest);
		if(getEdge(src,dest) == null) {
			edges.add(e);
			edgeToSrc.put(e, src);
			edgeToDest.put(e, dest);
			vertexToEdges.get(src).add(e);
		}
		
	}
	

	public List<Edge> getEdges() {
		return new ArrayList<Edge>(edges);
	}
	
	public Edge getEdge(Vertex v1, Vertex v2) {
		for(Edge e: vertexToEdges.get(v1)) {
			if(edgeToDest.get(e).equals(v2))
				return e;
		}return null;
	}

	public List<Vertex> getAdjacentVertices(Vertex src) {
		List<Vertex> res = new ArrayList<Vertex>();
		for (Edge e : vertexToEdges.get(src)) {
			res.add(edgeToDest.get(e));
		}
		return res;
	}


	/*public Map<Vertex,Vertex> ShortestPath(Vertex src, Vertex dest) {
		
		List<Vertex> visited = new ArrayList<Vertex>();
		Map<Vertex, Vertex> parents = new HashMap<Vertex, Vertex>();
		Map<Vertex, Vertex> pathParents = new HashMap<Vertex, Vertex>();
		Map<Vertex,Integer> vertexWeight = new HashMap<Vertex, Integer>();
		//Map<Vertex,Integer> distances = new HashMap<>();
		//Map<Vertex,Integer> heuristic = new HashMap<>();
    	PriorityQueue<Vertex> toVisit = new PriorityQueue<Vertex>(new Comparateur<Object, Map<Vertex, Integer>>(vertexWeight));
    	
    	for (Vertex each:vertices) {
    		vertexWeight.put(each,Integer.MAX_VALUE);
        }
    	
    	vertexWeight.put(src, 0);
		toVisit.add(src);
		parents.put(src, null);
		Vertex v = src;
		
		List<Vertex> path = new ArrayList<Vertex>();
		int dist =0;
		
		while (!toVisit.isEmpty() ) {
			v = toVisit.poll();
			visited.add(v);
			
			for (Vertex each : getAdjacentVertices(v)) {
				
				System.out.println(getProfit(v));
				System.out.println(getProfit(each));
				System.out.println("distance "+distParent(v,each));
				
				if (!visited.contains(each) && distParent(v,each)>0 ) {
					
					System.out.println("jsuis la");
					dist=vertexWeight.get(v)+distParent(v,each);
					if(vertexWeight.get(each)>dist) {
						vertexWeight.put(each, dist);
						parents.put(each, v);
					}
					toVisit.add(each);
				}
			}
			
		}
		
		if(parents.containsKey(dest)) {
			Vertex iter = dest;
			while (iter != null) {
				path.add(iter);
				pathParents.put(iter, parents.get(iter));
				iter = parents.get(iter);
			}
			return pathParents;
		}
		return null;
		
		
	}*/
	
	public List<Integer> bfs(Map<Integer, Integer> f, Vertex src, Vertex t) {
		Queue<Vertex> toProcess = new LinkedList<Vertex>();
		Map<Vertex, Vertex> previousV = new HashMap<Vertex, Vertex>();
		Vertex visiting = src;
		toProcess.add(src);
		previousV.put(src, null);

		while (!visiting.equals(t) && !toProcess.isEmpty()) {
			visiting = toProcess.remove();
			for (Vertex v : getAdjacentVertices(visiting)) {
				if (!previousV.containsKey(v) && !toProcess.contains(v) && f.get(getEdge(visiting, v)) > 0) {
					toProcess.add(v);
					previousV.put(v, visiting);
				}

			}
		}

		List<Integer> path = new ArrayList<Integer>();
		if (visiting.equals(t)) {
			Vertex v = t;
			while (previousV.get(v) != null) {
				path.add((Integer) getEdge(previousV.get(v), v));
				v = previousV.get(v);
			}

			return path;

		}
		return null;
	}
	
	public boolean connexion(Map<Integer, Integer> f, Vertex src, Vertex dest) {
		   
        List<Vertex> visited = new ArrayList<Vertex>();
        List<Vertex> toVisit = new ArrayList<Vertex>();

        toVisit.add(src);
        
        while (!toVisit.isEmpty()) {
            Vertex v = toVisit.remove(toVisit.size()-1);
            visited.add(v);
            for (Vertex each : getAdjacentVertices(v)) {
            	
                if (!visited.contains(each) && f.get(getEdge(v, each)) > 0) {  
                	if(each.equals(dest)) {
                		return true;
                	}
                	toVisit.add(each);
                }
            }
        }
        return false;
    }
	
	public boolean vContains(Vertex v) {
		for (Vertex a : vertices) {
			if (v.equals(a)) {
				return true;
			}
		}
		return false;
	}
	
	

	
}
