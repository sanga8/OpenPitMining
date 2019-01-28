package projet.OpenPitMining;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class GUI {
	
	private AdjacencyNetwork<Cell, Integer> graph;
	private float X;
	private float Y;

	public GUI(AdjacencyNetwork<Cell,Integer> graph) {
		this.graph = graph;
		setupCanvas(graph.getVertices(),1200,900);
		menu();
		draw();
	}

	public void setupCanvas(Collection<Cell> cells, int canvasWidth, int canvasHeight) {
		StdDraw.setCanvasSize(canvasWidth, canvasHeight);
		int[] nRowsNCols = maxRowMaxCol(cells);
		this.X = (int) (nRowsNCols[1] + 0.5+2+0.5);
		this.Y = (int) (nRowsNCols[0] + 0.5 + 0.5);
		StdDraw.setXscale(-0.5, nRowsNCols[1] + 0.5+2);
		StdDraw.setYscale(-(nRowsNCols[0] + 0.5), 0.5);
		
	}
	
	public void menu() {
		
		StdDraw.picture( -0.5+X/2,0.5-Y/2, "mur.png");
		StdDraw.picture( -0.5+X/2,0.5-Y/2, "menu.png");
		boolean menu = true;
		
		while (menu) {
						
			if (StdDraw.mousePressed()) {
				Double x = StdDraw.mouseX(), y = StdDraw.mouseY();
				StdDraw.clear();
				menu=false;
			}
			
		}
	}
	
	public void draw() {
		// StdDraw.setPenRadius(0.01);
		//StdDraw.enableDoubleBuffering();
		List<Cell> cells = new ArrayList<Cell>();
		cells = this.graph.getVertices();
		int[] nRowsNCols = maxRowMaxCol(cells);
		
		StdDraw.setPenColor(Color.decode("#7CB342")); 
		StdDraw.filledRectangle(nRowsNCols[1]+2, 0, 0.3,0.1);
		StdDraw.setPenColor(); 
		StdDraw.rectangle(nRowsNCols[1]+2, 0, 0.3,0.1);
		StdDraw.text(nRowsNCols[1]+2, 0, "Solution");
	
		
		int maxValue = 0;
		int minValue = 0;
		
		for(Cell each : cells) {	
				if(graph.getProfit(each)>maxValue) {
					maxValue = graph.getProfit(each);
				}
				else if(graph.getProfit(each)<minValue) {
					minValue = graph.getProfit(each);
				}
		}
		System.out.println(maxValue);
		System.out.println(minValue);
		int positiveValues = maxValue/3;
		int negativeValues = minValue/4;
		
		for (Cell c : cells) {
			
			int intName = graph.getProfit(c);
			
			//blocks color	
			//positive
			
			if(intName>0 && intName<positiveValues) {
				StdDraw.setPenColor(Color.decode("#81D4FA")); // diamond
				StdDraw.filledSquare(c.c, -c.r, 0.5);
			}
			else if(intName>=positiveValues && intName<2*positiveValues) {
				StdDraw.setPenColor(Color.decode("#29B6F6")); 
				StdDraw.filledSquare(c.c, -c.r, 0.5);			
			}
			else if(intName>=2*positiveValues) {
				StdDraw.setPenColor(Color.decode("#0288D1")); 
				StdDraw.filledSquare(c.c, -c.r, 0.5);
			}
			
			//negative
			
			else if(intName<=0 && intName>negativeValues) {
				StdDraw.setPenColor(Color.decode("#A1887F")); // grey 
				StdDraw.filledSquare(c.c, -c.r, 0.5);
			}
			else if(intName<=negativeValues && intName>2*negativeValues) {
				StdDraw.setPenColor(Color.decode("#6D4C41")); 
				StdDraw.filledSquare(c.c, -c.r, 0.5);
			}
			else if(intName<=2*negativeValues && intName>3*negativeValues) {
				StdDraw.setPenColor(Color.decode("#455A64")); 
				StdDraw.filledSquare(c.c, -c.r, 0.5);
			}
			else if(intName<=3*negativeValues) {
				StdDraw.setPenColor(Color.decode("#263238")); 
				StdDraw.filledSquare(c.c, -c.r, 0.5);
			}
			

			//contouring
			StdDraw.setPenColor();
			StdDraw.square(c.c, -c.r, 0.5);
			
			// number
			StdDraw.setPenColor(StdDraw.WHITE);
			
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
	
	public void play(List<Cell> toEscavate) {
		
		int[] nRowsNCols = maxRowMaxCol(graph.getVertices());
		boolean play = true;
		
		while (play) {
			if (StdDraw.mousePressed()) {
				Double x = StdDraw.mouseX(), y = StdDraw.mouseY();
				
				if(x>(nRowsNCols[1]+2-0.3)&&x<(nRowsNCols[1]+2+0.3) && y>-0.1&&y<0.1) {
					System.out.println(x);
					System.out.println(y);
					solution(toEscavate);
					play=false;
				}
				
			}
			
		}
	}
	
	public void solution(List<Cell> toEscavate) {
		//int[] nRowsNCols = maxRowMaxCol(this.graph.getVertices());
		for(Cell each : toEscavate) {
			StdDraw.setPenColor(Color.WHITE); 
			StdDraw.filledSquare(each.c, -each.r, 0.5);
			StdDraw.setPenColor();
			StdDraw.square(each.c, -each.r, 0.5);
		}
	}
}
