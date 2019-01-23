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
		
		
		int rows =GUI.maxRowMaxCol(res.getVertices())[0];
		int columns =GUI.maxRowMaxCol(res.getVertices())[1];
		int currentEdgeId = 0;
		// creation des connexions entre les vertices
		
		for(int i = rows;i>0;i--) { // pour toutes les lignes sauf la premiere
			for(int j = columns;j>=0;j--) { // pour chaque colonne
				//System.out.println(i);
				//System.out.println(j);
				if(j==0 ) {   //premiere
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j]);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j+1]);
					currentEdgeId++;
				}
				else if (j==columns) { //derniere
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j]);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j-1]);
					currentEdgeId++;
				}
				else {// ni premiere ni derniere colonne
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j]);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j-1]);
					currentEdgeId++;
					res.addEdge(currentEdgeId, network[i][j], network[i-1][j+1]);
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
		AdjacencyNetwork<Cell, Integer> rGraph = graphs.get(1);
		
		
		GUI.setupCanvas(vGraph.getVertices(),900,900);
		GUI.draw(vGraph);
		GUI.drawNames(vGraph);
				
		MinCut.mC(vGraph, rGraph);

		
		

	}

}
