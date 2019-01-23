package projet.OpenPitMining;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue; 

public class MinCut { 

public static void mC(AdjacencyNetwork<Cell,Integer> vGraph, AdjacencyNetwork<Cell,Integer> rGraph) {
		
		Map<Integer, Integer> flow = new HashMap<Integer, Integer>();
		Map<Integer, Integer> residual = new HashMap<Integer, Integer>();
		int  p, i1, i2, edge;
		int currentEdgeId = vGraph.getEdges().size();
		
		for (Cell v : vGraph.getVertices()) {
			
			System.out.println("main "+v.getR()+";"+v.getC());
			System.out.println("voisins");
			
			for (Cell v2 : vGraph.getAdjacentVertices(v)) {
				
				System.out.println(v2.getR()+";"+v2.getC());
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
		//vGraph.addVertex(S);
		//vGraph.addVertex(T);
		
		
		
		
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
		
		//System.out.println(rGraph.connexion(S, T));
	
		
		
		System.out.println("debut");
		int pathFlow;
		List<Integer> path;
		int maxFlow = 0;
		
		System.out.println("size "+rGraph.getVertices().size());
		System.out.println("size "+rGraph.getEdges().size());
		System.out.println("size "+flow.size());
		System.out.println("size "+residual.size());
		
		while ((path = rGraph.bfs(flow, S, T)) != null) {  // tant qu'il y a un chemin
			
			System.out.println("jsuis dedans");
			System.out.println(path.size());
			pathFlow = Integer.MAX_VALUE;
			for (int e : path) {
				
				
				pathFlow = Math.min(pathFlow, flow.get(e));
			}

			maxFlow += pathFlow;
			System.out.println("max flow: " + maxFlow);
			
			
			for (int e : path) {
				System.out.print((rGraph.edgeToDest.get(rGraph.getEdges().get(e))).getR());
				System.out.print(";");
				System.out.print((rGraph.edgeToDest.get(rGraph.getEdges().get(e))).getC());
				System.out.println("");
				System.out.println("avant"+flow.get(e));
				flow.put(e, flow.get(e) - pathFlow);
				System.out.println("apres"+flow.get(e));
				flow.put(residual.get(e), flow.get(residual.get(e)) + pathFlow);
			}
	         
			
		}
		
		
		System.out.println("fin");
		
		int allProfit = 0;
		
		for(Cell each : vGraph.getVertices()) {
			
			if(!each.equals(S)&&!each.equals(T)) {
				if(vGraph.getProfit(each)>0) {
					allProfit += vGraph.getProfit(each);
				}
			}
			
			
			
		}
		
		int mincut = 0;
		
		Map<Cell,Boolean> reachable = new HashMap<Cell,Boolean>();
		
		
		/*for(Cell each : rGraph.getVertices()) {
			
			//if(!each.equals(S)) {
				reachable.put(each, rGraph.connexion(S, each));
			//}
			
			
		}
		
		for(int each : vGraph.getEdges()) {
			
			//System.out.println(vGraph.edgeToDest.get(each).getR()+";"+vGraph.edgeToDest.get(each).getC());
			
			if(vGraph.getWeight(each)>0 && reachable.get(rGraph.edgeToSrc.get(each))==true && reachable.get(rGraph.edgeToDest.get(each))==false) {
				
				System.out.println(vGraph.edgeToSrc.get(each).getR()+";"+vGraph.edgeToSrc.get(each).getC());
				//System.out.println("poids "+vGraph.getWeight(each));
				mincut += vGraph.getWeight(each);
				
				//System.out.println(mincut);
				
			}
		}
		
	     int maxprofit =allProfit-mincut;
	     
	     System.out.println("La mincut"+mincut);
	     
	     System.out.println("Le profit max est = "+maxprofit);*/
	}
} 
