package projet.OpenPitMining;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import edu.princeton.cs.introcs.StdDraw;

public class GUI{
	
	private AdjacencyNetwork<Cell, Integer> graph;
	private float X;
	private float Y;
	Cell network[][];
	int positiveValues;
	int negativeValues;
	int currentProfit = 0;
	protected Map<Cell,Boolean> profit = new HashMap<Cell,Boolean>();
	
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
		for(Cell each : graph.getVertices()) {
			this.profit.put(each, false);
		}
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
	
		this.currentProfit =0;
		
		StdDraw.setPenColor(Color.GRAY); 
		StdDraw.filledRectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/15, 0.45,0.2);
		StdDraw.setPenColor(); 
		StdDraw.rectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/15, 0.45,0.2);
		StdDraw.picture(nRowsNCols[1]+1.7, -(nRowsNCols[0] + 0.5)/15, "coin.png",0.3,0.3);
		
		StdDraw.text(nRowsNCols[1]+2.15, -(nRowsNCols[0] + 0.5)/15,  String.valueOf(currentProfit));
		
		
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

		while(play) {
			
				if (StdDraw.mousePressed()) {
					double x = StdDraw.mouseX(), y = StdDraw.mouseY();
	
					if(x>(nRowsNCols[1]+2-0.45) && x<(nRowsNCols[1]+2+0.45) && y>-(nRowsNCols[0] + 0.5)/4-0.45&&y<-(nRowsNCols[0] + 0.5)/4+0.45) {
						
						if(this.currentProfit==graph.getMaxProfit()) {
							StdDraw.clear();
							StdDraw.picture( -0.5+X/2,0.5-Y/2, "background.jpg");
							StdDraw.picture( -0.5+X/2,0.5-Y/2, "win.png");
							StdDraw.setFont(fontMinecraftPetit);
							
							StdDraw.text(-0.5+X/2 , 0.5-2*Y/3, "You collected "+this.currentProfit+" $");
							
						}
						else {
							solution(soluce);
							try{
								Thread.sleep(1500);
								}catch(InterruptedException e){}
							StdDraw.clear();
							StdDraw.picture( -0.5+X/2,0.5-Y/2, "gameover.png");
							StdDraw.setFont(fontMinecraftPetit);
							
							if(this.currentProfit<0) {
								StdDraw.text(-0.5+X/2 , 0.5-2*Y/3, "You lost "+this.currentProfit+" $");
							}
							else {
								StdDraw.text(-0.5+X/2 , 0.5-2*Y/3, "You collected "+this.currentProfit+" $");
							}
							
						}
						
						
						play = false;
					}
	
					if((x>=-0.5 && x<= nRowsNCols[1]+0.5) && (y<=0.5 && y>=-nRowsNCols[0]-0.5)) {  // to know if x and y are in the mine
						if(Math.abs(x-(int) x)<0.5) {
							x = (int) x;
							if(Math.abs(y-(int) y)<0.5) {
								y = Math.abs((int) y);
							}
							else {
								y = 1+Math.abs((int) y);
							}
						}
						else {
							x = (int) x+1;
							if(Math.abs(y-(int) y)<0.5) {
								y = Math.abs((int) y);
							}
							else {
								y = 1+Math.abs((int) y);
							}
						}
						
						Cell click = this.network[(int) y][(int) x];

						StdDraw.setPenColor(Color.WHITE); 
						StdDraw.filledSquare(x, -y, 0.5);
						StdDraw.setPenColor();
						StdDraw.square(x, -y, 0.5);
						
						updateProfit(click);
						updateNeighbors(click);
						
					}
				}	
				
				
			
		}
			
			
	}
	
	public void updateNeighbors(Cell cell) {
		for(Cell each: graph.getAdjacentVertices(cell)) {
			if(this.profit.get(each)==false) {
				StdDraw.setPenColor(Color.WHITE); 
				StdDraw.filledSquare(each.c, -each.r, 0.5);
				StdDraw.setPenColor();
				StdDraw.square(each.c, -each.r, 0.5);
				updateProfit(each);
				this.profit.put(each, true);
				updateNeighbors(each);
			}
		}
	}
	
	public void updateProfit(Cell cell) {
		if(this.profit.get(cell)==false) {
			this.profit.put(cell, true);
			this.currentProfit += graph.getProfit(cell);
			int[] nRowsNCols = maxRowMaxCol(graph.getVertices());
			
			StdDraw.setPenColor(Color.GRAY); 
			StdDraw.filledRectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/15, 0.45,0.2);
			StdDraw.setPenColor(); 
			StdDraw.rectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/15, 0.45,0.2);
			StdDraw.picture(nRowsNCols[1]+1.7, -(nRowsNCols[0] + 0.5)/15, "coin.png",0.3,0.3);
			
			StdDraw.text(nRowsNCols[1]+2.15, -(nRowsNCols[0] + 0.5)/15,  String.valueOf(currentProfit));
		}
		
	}

	
	public void solution(List<List<Cell>> soluce) {
		for(Cell each : soluce.get(0)) {
			StdDraw.setPenColor(Color.WHITE); 
			StdDraw.filledSquare(each.c, -each.r, 0.5);
			StdDraw.setPenColor();
			StdDraw.square(each.c, -each.r, 0.5);
			try{
				Thread.sleep(50);
				}catch(InterruptedException e){}
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
		
		int[] nRowsNCols = maxRowMaxCol(graph.getVertices());
		
		StdDraw.setPenColor(Color.GRAY); 
		StdDraw.filledRectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/15, 0.45,0.2);
		StdDraw.setPenColor(); 
		StdDraw.rectangle(nRowsNCols[1]+2, -(nRowsNCols[0] + 0.5)/15, 0.45,0.2);
		StdDraw.picture(nRowsNCols[1]+1.7, -(nRowsNCols[0] + 0.5)/15, "coin.png",0.3,0.3);
		
		StdDraw.text(nRowsNCols[1]+2.15, -(nRowsNCols[0] + 0.5)/15,  String.valueOf(graph.getMaxProfit()));
	}

}
