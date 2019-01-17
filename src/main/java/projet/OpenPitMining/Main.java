package projet.OpenPitMining;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class Main {

	private static AdjacencyNetwork<Cell, Integer> read(InputStream is) throws IOException {
		AdjacencyNetwork<Cell, Integer> res = new AdjacencyNetwork<Cell, Integer>();

		// System.out.println("i " + i);

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		for (int line = 0; br.ready(); ++line) {
			String currentLine = br.readLine();
			String[] coords = currentLine.split(" ");
			
				for (int j = 0; j < coords.length; j++) {
					Cell currentCell = new Cell(line , j);
					res.addVertex(currentCell);
					res.addProfit(currentCell,Integer.parseInt(coords[j]));
				}
		}

		return res;
	}

	

	public static void main(String[] args) throws IOException {
		
		InputStream test = new FileInputStream(new File("test.txt"));

		AdjacencyNetwork<Cell, Integer> maze = read(test);
		
		
		GUI.setupCanvas(maze.getVertices(),512,512);
		GUI.draw(maze);
		GUI.drawNames(maze);
		

	}

}
