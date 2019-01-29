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
	Cell network[][];
	int positiveValues;
	int negativeValues;
	
	Font fontMinecraft = new Font("Minecraft Evenings",Font.BOLD,90);
	Font fontMinecraftPetit = new Font("Minecraft Evenings",Font.ITALIC,20);
	Font fontClassic = new Font("Libre Franklin",Font.BOLD,35);
	
	public GUI() {
		
	}

	public Cell[][] getNetwork() {
		return network;
	}

	public void setNetwork(Cell[][] network) {
		this.network = network;
	}

	public AdjacencyNetwork<Cell, Integer> getGraph() {
		return graph;
	}

	public void setGraph(AdjacencyNetwork<Cell, Integer> graph) {
		this.graph = graph;
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
		setupCanvas(graph.getVertices(),1200,900);
		StdDraw.setFont(fontMinecraft);	
		StdDraw.picture( -0.5+X/2,0.5-Y/2, "background.jpg");
		StdDraw.text(-0.5+X/2 , 0.5-Y/3, "Open Pit Mining");
		StdDraw.setFont(fontMinecraftPetit);	
		StdDraw.text(-0.5+X/2 , 0.5-Y, "Made by Guillaume Dupont, Vincent Pauwels, Merlin Rousseau");
		boolean menu = true;

		while (menu) {

			if (StdDraw.mousePressed()) {
				Double x = StdDraw.mouseX(), y = StdDraw.mouseY();
				StdDraw.clear();
				menu=false;
			}

		}
		draw();
	}
	
	public void draw() {
		StdDraw.setFont(fontClassic);	
		StdDraw.picture( -0.5+X/2,0.5-Y/2, "background.jpg");
		// StdDraw.setPenRadius(0.01);
		//StdDraw.enableDoubleBuffering();
		List<Cell> cells = new ArrayList<Cell>();
		cells = this.graph.getVertices();
		int[] nRowsNCols = maxRowMaxCol(cells);
		

		StdDraw.setPenColor(); 
		StdDraw.rectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/4, 0.45,0.45);
		StdDraw.picture(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/4, "pickaxe.png", 1,0.9);
	
		StdDraw.rectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/15, 0.45,0.2);
		StdDraw.picture(nRowsNCols[1]+1.7, -(nRowsNCols[0] + 0.5)/15, "coin.png",0.3,0.3);
		int a = 0;
		StdDraw.text(nRowsNCols[1]+2.15, -(nRowsNCols[0] + 0.5)/15,  String.valueOf(a));
		
		
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
		this.positiveValues = maxValue/2;
		this.negativeValues = minValue/3;
		
		if(positiveValues>0) {
			for (Cell c : cells) {
				
				int intName = graph.getProfit(c);
				
				//blocks color	
				//positive
				if(intName>0 && intName<positiveValues) {
					StdDraw.picture(c.c, -c.r, "gold.jpg",1,1);// diamond
				}
				
				else if(intName>=positiveValues) {
					StdDraw.picture(c.c, -c.r, "diamond.jpg",1,1);
				}
				//negative
				else if(intName<=0 && intName>negativeValues) {
					StdDraw.picture(c.c, -c.r, "dirt.jpeg",1,1);
				}
				else if(intName<=negativeValues && intName>2*negativeValues) {
					StdDraw.picture(c.c, -c.r, "andesite.png",1,1);
				}
				else if(intName<=2*negativeValues) {
					StdDraw.picture(c.c, -c.r, "stone.png",1,1);
				}	
				
				
				//contouring
				StdDraw.setPenColor();
				StdDraw.square(c.c, -c.r, 0.5);
				//number
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.text(c.c, -c.r, String.valueOf(intName));
				
				StdDraw.text(c.c, -c.r, String.valueOf(intName));
				}
		}
		else {
			for (Cell c : cells) {  // si il n'y a pas de profit positif
				
				int intName = graph.getProfit(c);
				StdDraw.picture(c.c, -c.r, "stone.png",1,1);

				//contouring
				StdDraw.setPenColor();
				StdDraw.square(c.c, -c.r, 0.5);
				//number
				StdDraw.setPenColor(StdDraw.WHITE);
				
				StdDraw.text(c.c, -c.r, String.valueOf(intName));
				
				StdDraw.text(c.c, -c.r, String.valueOf(intName));
				}
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
	
	public void play(List<List<Cell>> soluce) {
		
		int[] nRowsNCols = maxRowMaxCol(graph.getVertices());
		boolean play = true;
		
		while (play) {
			if (StdDraw.mousePressed()) {
				Double x = StdDraw.mouseX(), y = StdDraw.mouseY();

				if(x>(nRowsNCols[1]+2-0.3) && x<(nRowsNCols[1]+2+0.3) && y>-(nRowsNCols[0] + 0.5)/4-0.4&&y<-(nRowsNCols[0] + 0.5)/4+0.4) {
					System.out.println(x);
					System.out.println(y);
					solution(soluce);
					play=false;
				}
			}	
		}
	}
	
	public void solution(List<List<Cell>> soluce) {
		for(Cell each : soluce.get(0)) {
			StdDraw.setPenColor(Color.WHITE); 
			StdDraw.filledSquare(each.c, -each.r, 0.5);
			StdDraw.setPenColor();
			StdDraw.square(each.c, -each.r, 0.5);
		}
		for (Cell c : soluce.get(1)) {
			
			int intName = graph.getProfit(c);
			
			//blocks color	
			//positive
			if(intName>0 && intName<positiveValues) {
				StdDraw.picture(c.c, -c.r, "gold.jpg",1,1);// diamond
			}
			
			else if(intName>=positiveValues) {
				StdDraw.picture(c.c, -c.r, "diamond.jpg",1,1);
			}
			//negative
			else if(intName<=0 && intName>negativeValues) {
				StdDraw.picture(c.c, -c.r, "dirt.jpeg",1,1);
			}
			else if(intName<=negativeValues && intName>2*negativeValues) {
				StdDraw.picture(c.c, -c.r, "andesite.png",1,1);
			}
			else if(intName<=2*negativeValues) {
				StdDraw.picture(c.c, -c.r, "stone.png",1,1);
			}	
			
			//contouring
			StdDraw.setPenColor();
			StdDraw.square(c.c, -c.r, 0.5);
			//number
			StdDraw.setPenColor(StdDraw.WHITE);
			
			StdDraw.text(c.c, -c.r, String.valueOf(intName));
			
			StdDraw.text(c.c, -c.r, String.valueOf(intName));
			}
	}
}
