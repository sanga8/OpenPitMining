package projet.OpenPitMining;


import java.util.ArrayList;
import java.util.HashMap;
//Java program for finding min-cut in the given graph 
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue; 

public class MinCut { 

public static void mC(AdjacencyNetwork<Cell,Integer> vGraph, AdjacencyNetwork<Cell,Integer> rGraph) {
		
		Cell S = new Cell(-1,0);
		Cell T = new Cell(-2,0);
		rGraph.addVertex(S);
		rGraph.addVertex(T);
		
		
		int currentEdgeId = rGraph.getEdges().size();
		
		for(Cell each : rGraph.getVertices()) {
			
			if((each.getR()!=-1)&&each.getR()!=-2) {  // verif si each n'est ni source ni sink sinon ->NullPointerException
																	// il faut penser à implémenter un comparateur avec hascode pour utiliser equals
				int profit = rGraph.getProfit().get(each);
				
				if(profit<0) {
					
					rGraph.addEdge(currentEdgeId, each, T, profit);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, T, each, 0);
					currentEdgeId++;
				}
				else if(profit >0) {
					rGraph.addEdge(currentEdgeId, S, each, profit);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, each, S, 0);
					currentEdgeId++;
				}
				/*else {
					rGraph.addEdge(currentEdgeId, S, each, profit);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, each, S, 0);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, each, T, profit);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, T, each, 0);
					currentEdgeId++;
				}*/
			}
			
		}
	
		//System.out.println(path);
		
		
		System.out.println("debut");
		
		Map<Cell, Cell> parents = new HashMap<Cell, Cell>();
		
		while(rGraph.areConnected(rGraph, S, T, parents)==true) {  // tant qu'il y a un chemin
			
			System.out.println("jsuis dedans");
			
			int pathFlow = Integer.MAX_VALUE;          

	        Cell iter = T;
	 		while (parents.get(iter) != null) {
	 			System.out.println(iter.getR()+";"+iter.getC());
	 			if(iter.equals(T)) {
	 				pathFlow = Math.min(pathFlow, Math.abs(rGraph.distParent(parents.get(iter), iter))); // selectionnne ledge de capacite minimale sur le chemin afin denvoyer ce flow
	 			}
	 			if(parents.get(iter).equals(S)) {
	 				pathFlow = Math.min(pathFlow, Math.abs(rGraph.distParent(parents.get(iter), iter))); // selectionnne ledge de capacite minimale sur le chemin afin denvoyer ce flow
	 			}
	 			
	 			iter = parents.get(iter);
	 		}
	 		
	 		
	 		System.out.println("pathflow="+pathFlow);
	 		
	 		
	 		Cell ite = T;
	 		while (parents.get(ite) != null) {
	 			
	 			for(int each: rGraph.vertexToEdges.get(parents.get(ite))) {
	 	    		if(rGraph.edgeToDest.get(each).equals(ite)) {
	 	    			rGraph.setWeight(each, -pathFlow);  // enleve pathFlow
	 	    		}
	 	    	}
	 			
	 			for(int each: rGraph.vertexToEdges.get(ite)) {
	 	    		if(rGraph.edgeToSrc.get(each).equals(parents.get(ite))) {
	 	    			rGraph.setWeight(each, pathFlow);   // ajoute pathFlow
	 	    		}
	 	    	}
	 			
	 			ite = parents.get(ite);
	 		}
	         
			
		}
		
		System.out.println("fin");
		
		int allProfit = 0;
		
		for(Cell each : vGraph.getVertices()) {
			
			if(vGraph.getProfit().get(each)>0) {
				allProfit += vGraph.getProfit().get(each);
			}
			
			
		}
		
		int mincut = 0;
		
		 // Print all edges that are from a reachable vertex to 
	     // non-reachable vertex in the original graph   
		Map<Cell,Boolean> isVisited = new HashMap<Cell,Boolean>();
		
		
		for(Cell each : rGraph.getVertices()) {
			
			List<Cell> reachable = new ArrayList<Cell>();
			isVisited.put(each, rGraph.areConnected(S, each));
			
		}
		
		for(int each : rGraph.getEdges()) {
			//System.out.println("poids "+rGraph.getWeight(each));
			if(isVisited.get(rGraph.edgeToSrc.get(each))==true && isVisited.get(rGraph.edgeToDest.get(each))==false) {
				
				System.out.println(rGraph.edgeToSrc.get(each).getR()+";"+rGraph.edgeToSrc.get(each).getC());
				mincut += rGraph.getWeight(each);
			}
		}
		
	     int maxprofit =allProfit-Math.abs(mincut);
	     
	     System.out.println("La mincut"+mincut);
	     
	     System.out.println("Le profit max est = "+maxprofit);
	}
} 
