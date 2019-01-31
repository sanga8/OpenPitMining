package projet.OpenPitMining;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue; 

public class MinCut { 

public static List<List<Cell>> mC(AdjacencyNetwork<Cell,Integer> vGraph, AdjacencyNetwork<Cell,Integer> rGraph) {
	
		List<List<Cell>> result = new ArrayList<List<Cell>>();
		List<Cell> toEscavate = new ArrayList<Cell>();
		List<Cell> notToEscavate = new ArrayList<Cell>();
		
		Map<Integer, Integer> flow = new HashMap<Integer, Integer>();
		Map<Integer, Integer> residual = new HashMap<Integer, Integer>();
		int  p, i1, i2, edge;
		int currentEdgeId = vGraph.getEdges().size();
		
		for (Cell v : vGraph.getVertices()) {
			
			
			for (Cell v2 : vGraph.getAdjacentVertices(v)) {
				
				
				edge = (int)(vGraph.getEdge(v, v2));
				i1=currentEdgeId++;
				rGraph.addEdgeMf(edge, v, v2);
				rGraph.addEdgeMf(i1, v2, v);
				flow.put(edge, Integer.MAX_VALUE);
				flow.put(i1, 0);
				residual.put(edge, i1);
				residual.put(i1, edge);
				
			}
		}
		
		Cell S = new Cell(-1,0);
		Cell T = new Cell(-2,0);
		rGraph.addVertex(S);
		rGraph.addVertex(T);	
		
		
		
		currentEdgeId = rGraph.getEdges().size();
		
		for (Cell v : vGraph.getVertices()) {
			p = vGraph.getProfit(v);
			if (p > 0) {
				i1=currentEdgeId++;
				i2=currentEdgeId++;
				rGraph.addEdgeMf(i1, S, v);
				rGraph.addEdgeMf(i2, v, S);
				flow.put(i1, p);
				flow.put(i2, 0);
				residual.put(i1, i2);
				residual.put(i2, i1);

			} else if (p < 0) {
				i1=currentEdgeId++;
				i2=currentEdgeId++;
				rGraph.addEdgeMf(i1, v, T);
				rGraph.addEdgeMf(i2, T, v);
				flow.put(i1, -p);
				flow.put(i2, 0);
				residual.put(i1, i2);
				residual.put(i2, i1);

			}
		}
	
		int pathFlow;
		List<Integer> path;
		int maxFlow = 0;
		
		
		while ((path = rGraph.bfs(flow, S, T)) != null) {  // tant qu'il y a un chemin
			
			
			pathFlow = Integer.MAX_VALUE;
			for (int e : path) {
				
				
				pathFlow = Math.min(pathFlow, flow.get(e));
			}

			maxFlow += pathFlow;
			
			
			
			for (int e : path) {
				flow.put(e, flow.get(e) - pathFlow);
				flow.put(residual.get(e), flow.get(residual.get(e)) + pathFlow);
			}
	         
			
		}
		
		System.out.println("max flow: " + maxFlow);
		
		int allProfit = 0;
		
		for(Cell each : vGraph.getVertices()) {
			
			if(!each.equals(S)&&!each.equals(T)) {
				if(vGraph.getProfit(each)>0) {
					allProfit += vGraph.getProfit(each);
				}
			}
			
		}
		
		int mincut = 0;
		
		for(Cell each : rGraph.getVertices()) {
			
			if(rGraph.connexion(flow, S, each)==true) {
				if(!each.equals(S)) {
					toEscavate.add(each);
				}
			}
			else {
				if(!each.equals(S)&&!each.equals(T)) {
					notToEscavate.add(each);
				}
			}
			
	
		}
		
		
	     int maxProfit =allProfit-maxFlow;
	     rGraph.setMaxProfit(maxProfit);
	     vGraph.setMaxProfit(maxProfit);
	     System.out.println("Le profit max est = "+maxProfit);
	     
	     result.add(toEscavate);
	     result.add(notToEscavate);
	     
	     return result;
	     
	     
	}
} 
