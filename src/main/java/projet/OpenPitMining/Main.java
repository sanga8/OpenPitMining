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

	private static int[] maxRowMaxCol(Collection<Cell> cells) {
		int[] res = { 0, 0 };
		for (Cell c : cells) {
			if (c.r > res[0]) {
				res[0] = c.r;
			}
			if (c.c > res[1]) {
				res[1] = c.c;
			}
		}
		return res;
	}

	private static void setupCanvas(Collection<Cell> cells, int canvasWidth, int canvasHeight) {
		StdDraw.setCanvasSize(canvasWidth, canvasHeight);
		int[] nRowsNCols = maxRowMaxCol(cells);
		StdDraw.setXscale(-0.5, nRowsNCols[1] + 0.5);
		StdDraw.setYscale(-(nRowsNCols[0] + 0.5), 0.5);
		
	}
	
	private static void draw(AdjacencyNetwork<Cell, Integer> maze) {
		draw(maze, maze.getVertices());
	}
	
	private static void draw(AdjacencyNetwork<Cell, Integer> maze, Collection<Cell> cells) {
		// StdDraw.setPenRadius(0.01);
		//StdDraw.enableDoubleBuffering();
		for (Cell c : cells) {
			
			StdDraw.square(c.c, -c.r, 0.5);
			}
		
		StdDraw.show();
	}
	
	private static void drawNames(AdjacencyNetwork<Cell, Integer> maze) {
		StdDraw.setPenColor(StdDraw.RED);
		for (Cell c : maze.getVertices()) {
			int intName = maze.getProfit().get(c);
			StdDraw.text(c.c, -c.r, String.valueOf(intName));
		}
			
		StdDraw.show();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InputStream test = new FileInputStream(new File("test.txt"));

		AdjacencyNetwork<Cell, Integer> maze = read(test);
		
		for(Cell each : maze.getVertices()) {
			System.out.print(each.getR()+","+each.getC());
			System.out.println("= "+maze.getProfit().get(each));
		}
		System.out.println(maze.getVertices().size());
		System.out.println(maxRowMaxCol(maze.getVertices())[0]+" , "+maxRowMaxCol(maze.getVertices())[1]);
		
		setupCanvas(maze.getVertices(),512,512);
		draw(maze);
		drawNames(maze);
		

	}

}
