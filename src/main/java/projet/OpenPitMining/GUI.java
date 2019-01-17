package projet.OpenPitMining;

import java.util.Collection;

import edu.princeton.cs.introcs.StdDraw;

public class GUI {

	public static void setupCanvas(Collection<Cell> cells, int canvasWidth, int canvasHeight) {
		StdDraw.setCanvasSize(canvasWidth, canvasHeight);
		int[] nRowsNCols = maxRowMaxCol(cells);
		StdDraw.setXscale(-0.5, nRowsNCols[1] + 0.5);
		StdDraw.setYscale(-(nRowsNCols[0] + 0.5), 0.5);
		
	}
	
	public static void draw(AdjacencyNetwork<Cell, Integer> maze) {
		draw(maze, maze.getVertices());
	}
	
	public static void draw(AdjacencyNetwork<Cell, Integer> maze, Collection<Cell> cells) {
		// StdDraw.setPenRadius(0.01);
		//StdDraw.enableDoubleBuffering();
		for (Cell c : cells) {
			
			StdDraw.square(c.c, -c.r, 0.5);
			}
		
		StdDraw.show();
	}
	
	public static void drawNames(AdjacencyNetwork<Cell, Integer> maze) {
		StdDraw.setPenColor(StdDraw.RED);
		for (Cell c : maze.getVertices()) {
			int intName = maze.getProfit().get(c);
			StdDraw.text(c.c, -c.r, String.valueOf(intName));
		}
			
		StdDraw.show();
	}
	
	public static int[] maxRowMaxCol(Collection<Cell> cells) {
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
}
