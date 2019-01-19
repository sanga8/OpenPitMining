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
		vGraph.addVertex(S);
		vGraph.addVertex(T);
		
		
		
		
		int currentEdgeId = rGraph.getEdges().size();
		
		for(Cell each : rGraph.getVertices()) {
			
			if((each.getR()!=-1)&&each.getR()!=-2) {  // verif si each n'est ni source ni sink sinon ->NullPointerException
																	// il faut penser à implémenter un comparateur avec hascode pour utiliser equals
				int profit = rGraph.getProfit().get(each);
				
				if(profit<0) {
					
					rGraph.addEdge(currentEdgeId, each, T, -profit);
					vGraph.addEdge(currentEdgeId, each, T, -profit);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, T, each, 0);
					vGraph.addEdge(currentEdgeId, T, each, 0);
					currentEdgeId++;
				}
				else if(profit >0) {
					rGraph.addEdge(currentEdgeId, S, each, profit);
					vGraph.addEdge(currentEdgeId, S, each, profit);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, each, S, 0);
					vGraph.addEdge(currentEdgeId, each, S, 0);
					currentEdgeId++;
				}
				else {
					rGraph.addEdge(currentEdgeId, S, each, 0);
					vGraph.addEdge(currentEdgeId, S, each, 0);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, each, S, 0);
					vGraph.addEdge(currentEdgeId, each, S, 0);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, each, T, 0);
					vGraph.addEdge(currentEdgeId, each, T, 0);
					currentEdgeId++;
					rGraph.addEdge(currentEdgeId, T, each, 0);
					vGraph.addEdge(currentEdgeId, T, each, 0);
					currentEdgeId++;
				}
			}
			
		}
		
		//System.out.println(rGraph.connexion(S, T));
	
		//System.out.println(path);
		
		
		System.out.println("debut");
		
		Map<Cell, Cell> parents = new HashMap<Cell, Cell>();
		
		while(rGraph.areConnected(rGraph, S, T, parents)==true) {  // tant qu'il y a un chemin
			
			System.out.println("jsuis dedans");
			
			int pathFlow = Integer.MAX_VALUE;          

	        Cell iter = T;
	 		while (parents.get(iter) != null) {
	 			//System.out.println(iter.getR()+";"+iter.getC());
	 			if(iter.equals(T)) {
	 				pathFlow = Math.min(pathFlow, Math.abs(rGraph.distParent(parents.get(iter), iter))); // selectionnne ledge de capacite minimale sur le chemin afin denvoyer ce flow
	 			}
	 			if(parents.get(iter).equals(S)) {
	 				pathFlow = Math.min(pathFlow, Math.abs(rGraph.distParent(parents.get(iter), iter))); // selectionnne ledge de capacite minimale sur le chemin afin denvoyer ce flow
	 			}
	 			
	 			iter = parents.get(iter);
	 		}
	 		
	 		
	 		//System.out.println("pathflow="+pathFlow);
	 		
	 		
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
			
			if(!each.equals(S)&&!each.equals(T)) {
				if(vGraph.getProfit().get(each)>0) {
					allProfit += vGraph.getProfit().get(each);
				}
			}
			
			
			
		}
		
		int mincut = 0;
		
		 // Print all edges that are from a reachable vertex to 
	     // non-reachable vertex in the original graph   
		Map<Cell,Boolean> reachable = new HashMap<Cell,Boolean>();
		
		
		for(Cell each : rGraph.getVertices()) {
			
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
	     
	     System.out.println("Le profit max est = "+maxprofit);
	}
} 
