package projet.OpenPitMining;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class Main {
	
	private static AdjacencyNetwork<Cell, Integer> construct(AdjacencyNetwork<Cell, Integer> res) throws IOException{
		
		
		Cell S = new Cell(-1,0);
		Cell T = new Cell(-2,0);
		res.addVertex(S);
		res.addVertex(T);
		
		int currentEdgeId = 0;
		
		for(Cell each : res.getVertices()) {
			
			if((each.getR()==-1)&&each.getR()==-2) {  // verif si each n'est ni source ni sink sinon ->NullPointerException
																	// il faut penser à implémenter un comparateur avec hascode pour utiliser equals
				int profit = res.getProfit().get(each);
				if(profit<0) {
					res.addEdge(currentEdgeId, each, T, profit);
					currentEdgeId++;
				}
				else if(profit >0) {
					res.addEdge(currentEdgeId, S, each, profit);
					currentEdgeId++;
				}
			}
			
		}
		
		return res;
		
	}

	private static List<AdjacencyNetwork<Cell, Integer>> read(String is) throws IOException {
		
		List<AdjacencyNetwork<Cell, Integer>> fin = new ArrayList<AdjacencyNetwork<Cell, Integer>>();
		
		AdjacencyNetwork<Cell, Integer> res = new AdjacencyNetwork<Cell, Integer>();
		AdjacencyNetwork<Cell, Integer> v2 = new AdjacencyNetwork<Cell, Integer>();
		
		BufferedReader ar = new BufferedReader(new InputStreamReader(new FileInputStream(new File("test.txt"))));
		int x = 0;
		int y = 0;

 		for (String actualLine = ar.readLine(); actualLine != null; actualLine = ar.readLine()) {
			String[] coords = actualLine.split(" ");
			x++;
			if(y<coords.length) {
				y=coords.length;         // si non rectangulaire
			}
		}
 		
 		System.out.println("x " + x);
 		System.out.println("y " + y);

 		
 		Cell network[][] = new Cell[x][y];

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("test.txt"))));

		// construction du graph pour l'affichage
		
		for (int line = 0; br.ready(); ++line) {
			String currentLine = br.readLine();
			String[] coords = currentLine.split(" ");
				for (int j = 0; j < coords.length; j++) {
					Cell currentCell = new Cell(line , j);
					res.addVertex(currentCell);
					res.addProfit(currentCell,Integer.parseInt(coords[j]));
					v2.addVertex(currentCell);
					v2.addProfit(currentCell,Integer.parseInt(coords[j]));
					network[line][j]=currentCell;
				}
		}
		System.out.println(network.length);
		
		int rows =GUI.maxRowMaxCol(res.getVertices())[0];
		int columns =GUI.maxRowMaxCol(res.getVertices())[1];
		int currentEdgeId = 0;
		// creation des connexions entre les vertices
		
		for(int i = rows;i>0;i--) { // pour toutes les lignes sauf la premiere
			for(int j = columns;j>=0;j--) { // pour chaque colonne
				//System.out.println(i);
				//System.out.println(j);
				if(j==0 ) {   //premiere
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j], Integer.MAX_VALUE);
					v2.addEdge(currentEdgeId, network[i][j], network[i-1][j], Integer.MAX_VALUE);
					currentEdgeId++;
					res.addEdge(currentEdgeId,  network[i-1][j], network[i][j], 0);
					v2.addEdge(currentEdgeId, network[i-1][j], network[i][j],  0);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j+1], Integer.MAX_VALUE);
					v2.addEdge(currentEdgeId, network[i][j], network[i-1][j+1], Integer.MAX_VALUE);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i-1][j+1], network[i][j], 0);
					v2.addEdge(currentEdgeId, network[i-1][j+1], network[i][j], 0);
					currentEdgeId++;
				}
				else if (j==columns) { //derniere
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j], Integer.MAX_VALUE);
					v2.addEdge(currentEdgeId, network[i][j], network[i-1][j], Integer.MAX_VALUE);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i-1][j], network[i][j], 0);
					v2.addEdge(currentEdgeId,  network[i-1][j], network[i][j], 0);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j-1], Integer.MAX_VALUE);
					v2.addEdge(currentEdgeId, network[i][j], network[i-1][j-1], Integer.MAX_VALUE);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i-1][j-1], network[i][j], 0);
					v2.addEdge(currentEdgeId, network[i-1][j-1], network[i][j], 0);
					currentEdgeId++;
				}
				else {// ni premiere ni derniere colonne
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j], Integer.MAX_VALUE);
					v2.addEdge(currentEdgeId, network[i][j], network[i-1][j], Integer.MAX_VALUE);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i-1][j], network[i][j], 0);
					v2.addEdge(currentEdgeId, network[i-1][j], network[i][j], 0);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j-1], Integer.MAX_VALUE);
					v2.addEdge(currentEdgeId, network[i][j], network[i-1][j-1], Integer.MAX_VALUE);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i-1][j-1], network[i][j], 0);
					v2.addEdge(currentEdgeId, network[i-1][j-1], network[i][j], 0);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j+1], Integer.MAX_VALUE);
					v2.addEdge(currentEdgeId, network[i][j], network[i-1][j+1], Integer.MAX_VALUE);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i-1][j+1], network[i][j], 0);
					v2.addEdge(currentEdgeId, network[i-1][j+1], network[i][j], 0);
					currentEdgeId++;
				}
			}
		}

		fin.add(res);
		fin.add(v2);
		return fin;
	}
	

	

	public static void main(String[] args) throws IOException {
		
		InputStream test = new FileInputStream(new File("test.txt"));

		List<AdjacencyNetwork<Cell, Integer>> graphs = new ArrayList<AdjacencyNetwork<Cell, Integer>>();
		
		graphs = read("test.txt");
		AdjacencyNetwork<Cell, Integer> vGraph = graphs.get(0);
		
		AdjacencyNetwork<Cell, Integer> rGraph = construct(graphs.get(1));
		
		GUI.setupCanvas(vGraph.getVertices(),900,900);
		GUI.draw(vGraph);
		GUI.drawNames(vGraph);

		//System.out.println(vGraph.getVertices().size());
		//System.out.println(rGraph.getVertices().size()); // renvoie 2 de + car il y a S et T
		
		

	}

}
