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
	protected Map<Edge,Integer> edges = new HashMap<Edge,Integer>();
	protected Map<Edge,Integer> edgesFlow = new HashMap<Edge,Integer>();

	public void addVertex(Vertex v) {
		if (!vertices.contains(v)) {
			vertices.add(v);
			vertexToEdges.put(v, new ArrayList<Edge>());
		}
	}
	
	public void addProfit(Vertex v,Integer p) {	
			profit.put(v,p);

	}

	public Map<Vertex, Integer> getProfit() {
		return profit;
	}

	public void setProfit(Map<Vertex, Integer> profit) {
		this.profit = profit;
	}

	public List<Vertex> getVertices() {
		
		return new ArrayList<Vertex>(vertices);
	}

	public void addEdge(Edge e, Vertex src, Vertex dest, Integer weight) {
		
		//addVertex(src);
		//addVertex(dest);
		edgeToSrc.put(e, src);
		edgeToDest.put(e, dest);
		vertexToEdges.get(src).add(e);
		edges.put(e,weight);
		
	}

	public int getWeight(Edge e) {
		
		return edges.get(e);
	}
	
	public void setWeight(Edge e, int i) {
		// TODO Auto-generated method stub
		edges.put(e, edges.get(e)+i);
	}

	public List<Edge> getEdges() {

		return new ArrayList<Edge>(edgeToSrc.keySet());
	}

	public List<Vertex> getAdjacentVertices(Vertex src) {
		List<Vertex> res = new ArrayList<Vertex>();
		for (Edge e : vertexToEdges.get(src)) {
			res.add(edgeToDest.get(e));
		}
		return res;
	}

	public int distParent(Vertex parent,Vertex v) {
		for(Edge each: vertexToEdges.get(parent)) {
    		if(edgeToDest.get(each).equals(v)) {
    			return getWeight(each);
    		}
    	}
		return 0;
	}

	/*protected List<Integer> getPath(Vertex src, Vertex t, AdjacencyNetwork<Vertex,Edge> rGraph) {
		
        Queue<Vertex> toProcess = new LinkedList<Vertex>();
        Map<Vertex, Vertex> previousV = new HashMap<Vertex, Vertex>();
        Vertex visiting = src;
        toProcess.add(src);
        previousV.put(src, null);
        
        while (!visiting.equals(t) && !toProcess.isEmpty()) {
            visiting = toProcess.remove();
            for (Vertex v : rGraph.getAdjacentVertices(visiting)) {
                if (!previousV.containsKey(v) && !toProcess.contains(v) && flow.get(rGraph.getEdge(visiting, v)) > 0) {
                    toProcess.add(v);
                    previousV.put(v, visiting);
                }
            }
        }
        
        List<Integer> path = new ArrayList<Integer>();
        if (visiting.equals(t)) {
            Vertex v = t;
            while (previousV.get(v) != null) {
                path.add(rGraph.getEdge(previousV.get(v), v));
                v = previousV.get(v);
            } return path;
        } return null;
    }*/
	

	public Map<Vertex,Vertex> ShortestPath(Vertex src, Vertex dest) {
		
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
				
				System.out.println(getProfit().get(v));
				System.out.println(getProfit().get(each));
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
		
		
	}
	
	public boolean areConnected(AdjacencyNetwork rGraph, Vertex src, Vertex dest,Map<Vertex, Vertex> parents) {
   
        List<Vertex> visited = new ArrayList<Vertex>();
        Queue<Vertex> toVisit = new LinkedList<Vertex>();
        
        parents.put(src, null);
        toVisit.add(src);
        
        while (!toVisit.isEmpty()) {
            Vertex v = toVisit.poll();
            visited.add(v);
            for (Vertex each : getAdjacentVertices(v)) {
               
                if (!visited.contains(each) && distParent(v,each)>0) {
                	toVisit.add(each);
                	parents.put(each, v);
                }
            }
        }
        if(visited.contains(dest)) {
        	return true;
        }
        return false;
    }
	
	public boolean areConnected(Vertex src, Vertex dest) {
		   
        List<Vertex> visited = new ArrayList<Vertex>();
        Queue<Vertex> toVisit = new LinkedList<Vertex>();
        
        
        toVisit.add(src);
        
        while (!toVisit.isEmpty()) {
            Vertex v = toVisit.poll();
            visited.add(v);
            for (Vertex each : getAdjacentVertices(v)) {
               
                if (!visited.contains(each) && distParent(v,each)>0) {
                	toVisit.add(each);
                	
                }
            }
        }
        if(visited.contains(dest)) {
        	return true;
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
